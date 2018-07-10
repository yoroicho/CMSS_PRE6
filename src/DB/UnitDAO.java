/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import static DB.DatabaseUty.URL;
import common.SystemPropertiesItem;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
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
            System.out.println(statement.toString());

            ResultSet result = statement.executeQuery();
            System.out.println("検索：" + result.getFetchSize() + "件");

            try {
                connection.commit();
                System.out.println("検索成功");
                return result;
            } catch (SQLException e) {

                System.out.println("検索失敗");
                e.printStackTrace();
            }
        } catch (BatchUpdateException e) {
            System.out.println("登録失敗:すでに登録されています。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("outer");
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
                    //Instant instant = date.toInstant();
                    //ZoneId zone = ZoneId.systemDefault();
                    //ZonedDateTime converted = ZonedDateTime.ofInstant(instant, zone);
                    //dto.setDivtime(ZonedDateTime.ofInstant((result.getDate("divtime")toInstant()),ZoneId.systemDefault()));
                    dto.setDivtime(result.getLong("divtime"));
                    dto.setDivname(result.getString("divname"));
                    dto.setComment(result.getString("comment"));
                    dto.setPredivtime(result.getTimestamp("predivtime"));
                    dto.setArtifactsId(result.getString("artifactsid"));
                    dto.setEtd(result.getDate("etd"));
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
        //Timestamp id = Timestamp.valueOf(idText);
        String sql = "SELECT * from unit WHERE id = (?) ; ";
        //String sql = "SELECT * from unit ;";
// DTO?????????????
        List<UnitDTO> unitDTO = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.addBatch();
            System.out.println(statement.toString());

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
                    //dto.setDivtime(ZonedDateTime.ofInstant(result.getTimestamp("divtime").toInstant(),ZoneId.systemDefault()));
                    dto.setDivtime(result.getLong("divtime"));
                    dto.setDivname(result.getString("divname"));
                    dto.setComment(result.getString("comment"));
                    dto.setPredivtime(result.getTimestamp("predivtime"));
                    dto.setArtifactsId(result.getString("artifactsid"));
                    dto.setEtd(result.getDate("etd"));
                    // ???????List???
                    unitDTO.add(dto);
                    // while????????????
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
                    dto.setDivtime(result.getLong("divtime"));
                    dto.setDivname(result.getString("divname"));
                    dto.setComment(result.getString("comment"));
                    dto.setPredivtime(result.getTimestamp("predivtime"));
                    dto.setArtifactsId(result.getString("artifactsid"));
                    dto.setEtd(result.getDate("etd"));
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

    public static boolean create(UnitDTO unitDTO) {

        // 時計の誤差や海外時刻などで不用意に上書きしないようupdateは使わない。
        //String sql = "INSERT INTO unit values (?,?,?,?,?,?,?,?);";
        //仮に海外に行っても時差時間内に作業を再開するとは考えにくいので方針変更
        String sql = "REPLACE INTO unit values (?,?,?,?,?,?,?,?,?);";

        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setLong(1, unitDTO.getId());
            statement.setLong(2, unitDTO.getDivtime());
            statement.setString(3, unitDTO.getDivname());
            statement.setTimestamp(4, unitDTO.getCutdatetime());
            statement.setString(5, unitDTO.getComment());
            statement.setTimestamp(6, unitDTO.getPredivtime());
            statement.setString(7, unitDTO.getArtifactsId());
            statement.setTimestamp(8, unitDTO.getClosedatetime());
            statement.setDate(9, unitDTO.getEtd());
            statement.addBatch();
            ResultSet result = statement.executeQuery();
            try {
                connection.commit();
                System.out.println("追加成功");
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                System.out.println("追加失敗");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("データべース障害");
        }
        return false;
    }
}
