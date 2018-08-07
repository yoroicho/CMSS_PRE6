/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.BatchUpdateException;
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
 * @author tokyo
 */
public class UnitDAO implements IDAO {

    public static ResultSet getResultSetByKey(String tableName, String keyName, String id) {
        String sql = "SELECT * from " + tableName + " WHERE " + keyName + " = (?);";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setString(1, id);
            statement.addBatch();
            System.out.println("getResultSetByKey statement is " + statement.toString());
            ResultSet result = statement.executeQuery();
            System.out.println("getResultSetByKey 検索：" + result.getFetchSize() + "件"); // 下の行と逆っぽい
            connection.commit();
            System.out.println("getResultSetByKey 検索成功");
            return result;
        } catch (SQLException e) {
            System.out.println("getResultSetByKey 検索失敗");
            e.printStackTrace();
        }
        return null;
    }

    public static List<UnitDTO> findAll() {
        String sql = "SELECT * from unit;";
        // DTOクラスのインスタンス格納用
        List<UnitDTO> unitDTO = new ArrayList<>();
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
                    UnitDTO dto = new UnitDTO();
                    // カラムの値をフィールドにセット
                    dto.setId(result.getLong("id"));
                    dto.setClose(result.getDate("close"));
                    dto.setMaintitleId(result.getString("maintitleid"));
                    dto.setTitle(result.getString("title"));
                    dto.setMtg(result.getDate("mtg"));
                    dto.setCut(result.getDate("cut"));
                    dto.setEtd(result.getDate("etd"));
                    dto.setRemark(result.getString("remark"));
                    dto.setTemplateId(result.getLong("templateid"));
                    dto.setVersionId(result.getLong("versionid"));
                    // インスタンスをListに格納
                    unitDTO.add(dto);
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
        return unitDTO;
    }

    public static List<UnitDTO> findById(long id) {
System.out.println("findById Called."+id);
        String sql = "SELECT * from unit WHERE id = (?) ; ";
        //String sql = "SELECT * from unit ;";
// DTO?????????????
        List<UnitDTO> unitDTO = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.addBatch();
            System.out.println("findById "+statement.toString());

            ResultSet result = statement.executeQuery();
            System.out.println("Total" + result.getFetchDirection() + "items");

            try {
                connection.commit();
                System.out.println("E-1");
                // ??????????????????
                while (result.next()) {
                    // OrdersDTO?????????????
                    UnitDTO dto = new UnitDTO();
                    // ???????????????
                    dto.setId(result.getLong("id"));
                    dto.setClose(result.getDate("close"));
                    dto.setMaintitleId(result.getString("maintitleid"));
                    dto.setTitle(result.getString("title"));
                    dto.setMtg(result.getDate("mtg"));
                    dto.setCut(result.getDate("cut"));
                    dto.setEtd(result.getDate("etd"));
                    dto.setRemark(result.getString("remark"));
                    dto.setTemplateId(result.getLong("templateid"));
                    dto.setVersionId(result.getLong("versionid"));
                    unitDTO.add(dto);

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
        return unitDTO;
    }

    public static List<UnitDTO> findById(Timestamp id) {
        //Timestamp id = Timestamp.valueOf(idText);
        String sql = "SELECT * from unit WHERE id = (?);";
        // DTOクラスのインスタンス格納用
        List<UnitDTO> unitDTO = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setTimestamp(1, id);
            statement.addBatch();
            System.out.println(statement.toString());

            ResultSet result = statement.executeQuery();
            System.out.println("検索：" + result.getFetchSize() + "件");

            try {
                connection.commit();
                System.out.println("検索成功");
                // データベースから取得した値がある間、
                while (result.next()) {
                    // OrdersDTOクラスのインスタンスを生成
                    UnitDTO dto = new UnitDTO();
                    // カラムの値をフィールドにセット
                    dto.setId(result.getLong("id"));
                    dto.setClose(result.getDate("close"));
                    dto.setMaintitleId(result.getString("maintitleid"));
                    dto.setTitle(result.getString("title"));
                    dto.setMtg(result.getDate("mtg"));
                    dto.setCut(result.getDate("cut"));
                    dto.setEtd(result.getDate("etd"));
                    dto.setRemark(result.getString("remark"));
                    dto.setTemplateId(result.getLong("templateid"));
                    dto.setVersionId(result.getLong("versionid"));
                    // インスタンスをListに格納
                    unitDTO.add(dto);
                    // while文で次のレコードの処理へ
                }
            } catch (SQLException e) {
                // connection.rollback(); 
                e.printStackTrace();
                System.out.println("検索失敗");
            }
        } catch (SQLException e) {
            System.out.println("データベース障害");
            e.printStackTrace();
        }
        return unitDTO;
    }

    public static boolean register(UnitDTO unitDTO) {

        // 時計の誤差や海外時刻などで不用意に上書きしないようupdateは使わない。
        //String sql = "INSERT INTO unit values (?,?,?,?,?,?,?,?);";
        //仮に海外に行っても時差時間内に作業を再開するとは考えにくいので方針変更
        String sql = "REPLACE INTO unit values (?,?,?,?,?,?,?,?,?,?);";

        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setLong(1, unitDTO.getId());
            statement.setDate(2, unitDTO.getClose());
            statement.setString(3, unitDTO.getMaintitleId());
            statement.setString(4, unitDTO.getTitle());
            statement.setDate(5, unitDTO.getMtg());
            statement.setDate(6, unitDTO.getCut());
            statement.setDate(7, unitDTO.getEtd());
            statement.setString(8, unitDTO.getRemark());
            statement.setLong(9, unitDTO.getTemplateId());
            statement.setLong(10, unitDTO.getVersionId());
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
