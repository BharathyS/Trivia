package com.appscrip.trivia.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionHelper {
    public static String getformatedDateString(Date date, String formattpattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattpattern);
        String timeString = simpleDateFormat.format(date);
        return timeString;
    }
}
