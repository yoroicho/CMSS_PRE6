/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author tokyo
 */
public class TimestampUtil {

    private static final String CMSS_DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

    public static Timestamp parseToTimestamp(String dateTime) throws ParseException {
        return new Timestamp(new SimpleDateFormat(CMSS_DATE_FORMAT_PATTERN).parse(dateTime).getTime());
    }

    public static String formattedTimestamp(Timestamp timestamp, String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(timestamp);
    }

    public static String formattedTimestamp(Timestamp timestamp) {
        return new SimpleDateFormat(CMSS_DATE_FORMAT_PATTERN).format(timestamp);
    }

    public static Timestamp current() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String convertTimestampToISO860(Date dt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date());
    }

}
