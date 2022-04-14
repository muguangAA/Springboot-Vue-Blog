package com.muguang.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getTime(){
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sft.format(new Date());
    }

}
