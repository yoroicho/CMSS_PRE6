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
public class ProcessDAO implements IDAO {

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

    public static List<ProcessDTO> findAll() {
        String sql = "SELECT * from process;";
        // DTOクラスのインスタンス格納用
        List<ProcessDTO> processDTO = new ArrayList<>();
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
                    ProcessDTO dto = new ProcessDTO();
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
                    // インスタンスをListに格納
                    processDTO.add(dto);
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
        return processDTO;
    }

    public static List<ProcessDTO> findById(long id) {
        //Timestamp id = Timestamp.valueOf(idText);
        String sql = "SELECT * from process WHERE id = (?) ; ";
        //String sql = "SELECT * from process ;";
// DTO?????????????
        List<ProcessDTO> processDTO = new ArrayList<>();
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
                    ProcessDTO dto = new ProcessDTO();
                    // ???????????????
                    dto.setId(result.getLong("id"));
                    //dto.setDivtime(ZonedDateTime.ofInstant(result.getTimestamp("divtime").toInstant(),ZoneId.systemDefault()));
                    dto.setDivtime(result.getLong("divtime"));
                    dto.setDivname(result.getString("divname"));
                    dto.setComment(result.getString("comment"));
                    dto.setPredivtime(result.getTimestamp("predivtime"));
                    dto.setArtifactsId(result.getString("artifactsid"));
                    // ???????List???
                    processDTO.add(dto);
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
        return processDTO;
    }

    public static List<ProcessDTO> findById(Timestamp id) {
        //Timestamp id = Timestamp.valueOf(idText);
        String sql = "SELECT * from process WHERE id = (?);";
        // DTOクラスのインスタンス格納用
        List<ProcessDTO> processDTO = new ArrayList<>();
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
                    ProcessDTO dto = new ProcessDTO();
                    // カラムの値をフィールドにセット
                    dto.setId(result.getLong("id"));
                    dto.setDivtime(result.getLong("divtime"));
                    dto.setDivname(result.getString("divname"));
                    dto.setComment(result.getString("comment"));
                    dto.setPredivtime(result.getTimestamp("predivtime"));
                    dto.setArtifactsId(result.getString("artifactsid"));
                    // インスタンスをListに格納
                    processDTO.add(dto);
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
        return processDTO;
    }

    public static boolean create(ProcessDTO processDTO) {

        // 時計の誤差や海外時刻などで不用意に上書きしないようupdateは使わない。
        //String sql = "INSERT INTO process values (?,?,?,?,?,?,?,?);";
        //仮に海外に行っても時差時間内に作業を再開するとは考えにくいので方針変更
        String sql = "REPLACE INTO process values (?,?,?,?,?,?,?,?);";

        // データベースへの接続
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setLong(1, processDTO.getId());
            statement.setLong(2, processDTO.getDivtime());
            statement.setString(3, processDTO.getDivname());
            statement.setTimestamp(4, processDTO.getCutdatetime());
            statement.setString(5, processDTO.getComment());
            statement.setTimestamp(6, processDTO.getPredivtime());
            statement.setString(7, processDTO.getArtifactsId());
            statement.setTimestamp(8, processDTO.getClosedatetime());
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
