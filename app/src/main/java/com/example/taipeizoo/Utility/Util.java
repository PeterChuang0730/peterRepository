package com.example.taipeizoo.Utility;

public class Util {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static boolean isHttpOrHttpsUrl(String url) {
        String patter = "^(http|https)://.*$";
        return url.matches(patter);
    }
}
