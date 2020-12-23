package com.etang.nt_launcher.tool.util;

import android.os.Build;

import com.etang.nt_launcher.BuildConfig;

public class MTCore {

    public static String my_app_name = "梅糖桌面";

    public static String get_my_appVERSIONCODE() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    public static String get_my_appAPPLICATION_ID() {
        return String.valueOf(BuildConfig.APPLICATION_ID);
    }

    public static String get_my_appBUILD_TYPE() {
        return String.valueOf(BuildConfig.BUILD_TYPE);
    }

    public static String get_my_appDEBUG() {
        return String.valueOf(BuildConfig.DEBUG);
    }

    public static String get_my_appVERSIONNAME() {
        return String.valueOf(BuildConfig.VERSION_NAME);
    }
}
