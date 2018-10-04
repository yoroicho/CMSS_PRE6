/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import common.StaticSystemPropertiesAcc;
import common.SystemPropertiesItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AnyUser
 */
public interface IDAO {

    static final String URL = StaticSystemPropertiesAcc.DB_URL;
    static final String USERNAME = StaticSystemPropertiesAcc.DB_USER;
    static final String PASSWORD = StaticSystemPropertiesAcc.DB_PASS;

    public static ResultSet getResultSetByKey(String tableName, String keyName, String id) throws SQLException {
        String sql = "SELECT * from " + tableName + " WHERE " + keyName + " = (?);";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            statement.setString(1, id);
            statement.addBatch();
            System.out.println("getResultSetByKey statement is " + statement.toString());
            ResultSet result = statement.executeQuery();
            System.out.println("getResultSetByKey ???" + result.getFetchSize() + "?");
            connection.commit();
            System.out.println("getResultSetByKey ????");
            return result;
        } catch (SQLException e) {
            System.out.println("getResultSetByKey ????");
            e.printStackTrace();
        }
        return null;
    }
}
/*
検索
find(get|select) + カラム名（省略は全部) + By + 検索条件
findNameById(int id) : String
findById(int id) : TakuanBean
更新
update(modify) + カラム名(省略は全部) + By + 検索条件
updateNameById(int id, String name) : void
update(int id, TakuanBean takuan) : void
update(TakuanBean takuan) : void
作成
create(regist|insert)
create(TakuanBean takuan) : void
create(TakuanBean takuan) : void
削除
delete(remove) + By + 条件
delete(TakuanBean takuan)
 */
