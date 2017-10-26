package com.pm3.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by student on 2017/10/13.
 */

public class time {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH時mm分");//定義好時間字串的格式

    // ====== 截止時間換算 ======
    public static Calendar string2calendar(String t) {
        Calendar cal = Calendar.getInstance(); // 取得目前時間
        try {
            Date dt = sdf.parse(t);                              //將字串轉成Date型
            cal.setTime(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar nowtime() {
        return Calendar.getInstance(); // 取得目前時間
    }

    public static Calendar settime(int hh,int mm) {
        Calendar cal = nowtime(); // 取得目前時間

        cal.set(Calendar.HOUR_OF_DAY,hh);
        cal.set(Calendar.MINUTE,mm);
//        cal.add(Calendar.HOUR, hh);        //小時+hh
//        cal.add(Calendar.MINUTE, mm);      //分+mm
        return cal;
    }

    // ========時間換字串 ======
    public static String calendar2string(Calendar cal) {
        Date d = cal.getTime();
        String dateStr = sdf.format(d);
        return dateStr;
    }

}
