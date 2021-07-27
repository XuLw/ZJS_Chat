package com.example.zjschat.utils;

public class StringUtils {
    public static boolean isNull(String s) {
        if ("".equals(s) || s == null) {
            return true;
        }
        return false;
    }
}
