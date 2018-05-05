/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import common.StaticSystemPropertiesAcc;
import common.SystemPropertiesItem;

/**
 *
 * @author AnyUser
 */
public interface IDAO {


    static final String URL = StaticSystemPropertiesAcc.DB_URL;
    static final String USERNAME = StaticSystemPropertiesAcc.DB_USER;
    static final String PASSWORD = StaticSystemPropertiesAcc.DB_PASS;

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
}
