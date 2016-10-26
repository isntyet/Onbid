package com.ks.onbid.utill;

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
}
