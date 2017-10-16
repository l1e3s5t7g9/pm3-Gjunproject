package com.pm3.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by student on 2017/10/13.
 */

public class time {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//定義好時間字串的格式
    // ====== 截止時間換算 ======
    public static Calendar settime(String t) {
        Calendar cal = Calendar.getInstance(); // 取得目前時間
        try {
            Date dt = sdf.parse(t);                              //將字串轉成Date型
            cal.setTime(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar settime(int mm) {
        Calendar cal = Calendar.getInstance(); // 取得目前時間
//        cal.add(Calendar.HOUR, hh);        //小時+hh
        cal.add(Calendar.MINUTE, mm);      //分+mm
        return cal;
    }

    // ========時間換字串 ======
    public static String calendar2string(Calendar cal) {
        Date d = cal.getTime();
        String dateStr = sdf.format(d);
        return dateStr;
    }
}
