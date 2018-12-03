/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tokyo 2018/10/04 21:08
 */
public class MainTitleDAO implements IDAO {

    public static ResultSet getResultSetByKey(
            String tableName, String keyName, String id) throws SQLException {
        return IDAO.getResultSetByKey(tableName, keyName, id);
    }

    public static List<MainTitleDTO> findAll() {
        String sql = "SELECT * from maintitle;";
        // DTOクラスのインスタンス格納用
        List<MainTitleDTO> mainTitleDTO = new ArrayList<>();
        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.addBatch();
            ResultSet result = statement.executeQuery();
            try {
                connection.commit();
                System.out.println("検索成功");
                // データベースから取得した値がある間、
                while (result.next()) {
                    // OrdersDTOクラスのインスタンスを生成
                    MainTitleDTO dto = new MainTitleDTO();
                    // カラムの値をフィールドにセット
                    dto.setId(result.getString("id"));
                    dto.setClose(result.getDate("close"));
                    dto.setSeriesid(result.getString("seriesid"));
                    dto.setMaintitle(result.getString("maintitle"));
                    dto.setMainkana(result.getString("mainkana"));
                    dto.setLang(result.getString("lang"));
                    dto.setCreator(result.getString("creator"));
                    dto.setMtg(result.getDate("mtg"));
                    dto.setCut(result.getDate("cut"));
                    dto.setEtd(result.getDate("etd"));
                    dto.setRemark(result.getString("remark"));
                    dto.setTimestamp(result.getTimestamp("timestamp"));
                    // インスタンスをListに格納
                    mainTitleDTO.add(dto);
                    // while文で次のレコードの処理へ
                }
            } catch (SQLException e) {
                // connection.rollback(); 
                e.printStackTrace();
                System.out.println("検索失敗");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("データべース障害");
        }
        // DTOクラスのインスタンスのListを返す
        return mainTitleDTO;
    }

    public static List<MainTitleDTO> findById(String id) {
        String sql = "SELECT * from maintitle WHERE id = (?) ; ";
        List<MainTitleDTO> mainTitleDTO = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setString(1, id);
            statement.addBatch();
            System.out.println("findById " + statement.toString());

            ResultSet result = statement.executeQuery();
            System.out.println("Total" + result.getFetchDirection() + "items");

            try {
                connection.commit();
                while (result.next()) {
                    MainTitleDTO dto = new MainTitleDTO();
                    dto.setId(result.getString("id"));
                    dto.setClose(result.getDate("close"));
                    dto.setSeriesid(result.getString("seriesid"));
                    dto.setMaintitle(result.getString("maintitle"));
                    dto.setMainkana(result.getString("mainkana"));
                    dto.setLang(result.getString("lang"));
                    dto.setCreator(result.getString("creator"));
                    dto.setMtg(result.getDate("mtg"));
                    dto.setCut(result.getDate("cut"));
                    dto.setEtd(result.getDate("etd"));
                    dto.setRemark(result.getString("remark"));
                    dto.setTimestamp(result.getTimestamp("timestamp"));
                    mainTitleDTO.add(dto);
                }
            } catch (SQLException e) {
                // connection.rollback(); 
                e.printStackTrace();
                System.out.println("E-2");
            }
        } catch (SQLException e) {
            System.out.println("E-3");
            e.printStackTrace();
        }
        return mainTitleDTO;
    }

    public static boolean deleteById(String id) {

        // 時計の誤差や海外時刻などで不用意に上書きしないようupdateは使わない。
        //String sql = "INSERT INTO unit values (?,?,?,?,?,?,?,?);";
        //仮に海外に行っても時差時間内に作業を再開するとは考えにくいので方針変更
        String sql = "DELETE FROM maintitle WHERE id = ?;";

        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setString(1, id);
            statement.addBatch();
            ResultSet result = statement.executeQuery();
            try {
                connection.commit();
                System.out.println("deleteById 成功");
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                System.out.println("deleteById 失敗");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("deleteByID データべース障害");
        }
        return false;
    }

    public static boolean register(MainTitleDTO mainTitleDTO) {

        String sql = "REPLACE INTO unit values (?,?,?,?,?,?,?,?,?,?,?,?);";

        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setString(1, mainTitleDTO.getId());
            statement.setDate(2, mainTitleDTO.getClose());
            statement.setString(3, mainTitleDTO.getSeriesid());
            statement.setString(4, mainTitleDTO.getMaintitle());
            statement.setString(5, mainTitleDTO.getMainkana());
            statement.setString(6, mainTitleDTO.getLang());
            statement.setString(7, mainTitleDTO.getCreator());
            statement.setDate(8, mainTitleDTO.getMtg());
            statement.setDate(9, mainTitleDTO.getCut());
            statement.setDate(10, mainTitleDTO.getEtd());
            statement.setString(11, mainTitleDTO.getRemark());
            statement.setTimestamp(12, mainTitleDTO.getTimestamp());

            statement.addBatch();
            ResultSet result = statement.executeQuery();
            try {
                connection.commit();
                System.out.println("register 追加成功");
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                System.out.println("register 追加失敗");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("register データべース障害");
        }
        return false;
    }
}
