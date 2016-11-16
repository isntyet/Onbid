package com.ks.onbid.utill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by jo on 2016-10-27.
 */
public class SysUtill {

    public static String boolToStr(boolean value) {
        try {
            return Boolean.toString(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean strToBool(String value) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    public static String intToStr(int value) {
        try {
            return Integer.toString(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static int strToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String longToStr(long value) {
        try {
            return Long.toString(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static long strToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String floatToStr(float value) {
        try {
            return Float.toString(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static float strToFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String doubleToStr(double value) {
        try {
            return Double.toString(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static double strToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Date getCurrentTime() {
        Calendar cd = Calendar.getInstance();
        Date date = cd.getTime();

        return date;
    }

    public static String getCurrentToday() {
        String year = intToStr(new GregorianCalendar().get(GregorianCalendar.YEAR));
        String month = intToStr(new GregorianCalendar().get(GregorianCalendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        String day = intToStr(new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH));
        if (day.length() < 2) {
            day = "0" + day;
        }

        return year + month + day;
    }

    public static Date strToDttm(String dttm) {
        return strToDttm(dttm, "yyyyMMddHHmmss");
    }

    public static Date strToDttm(String dttm, String pattern) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern, Locale.KOREA);
        try {
            return sdf1.parse(dttm);
        } catch (Exception e1) {
            try {
                return sdf1.parse("19000101000000");
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
