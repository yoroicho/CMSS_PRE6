/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tokyo
 */
public class Utility {

    public static Boolean isUUID(String uuid) {
        String reg = "[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}";

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(uuid);

        return m.find();
    }
    

}
