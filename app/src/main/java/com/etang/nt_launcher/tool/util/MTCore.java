package com.etang.nt_launcher.tool.util;

import android.os.Build;

import com.etang.nt_launcher.BuildConfig;

/**
 * 存放整个APP能用到的变量
 */
public class MTCore {
    //APP名称
    public static String my_app_name = "梅糖桌面";

    //APP ID
    public static String get_my_appAPPLICATION_ID() {
        return String.valueOf(BuildConfig.APPLICATION_ID);
    }

    //APP构造...看不懂
    public static String get_my_appBUILD_TYPE() {
        return String.valueOf(BuildConfig.BUILD_TYPE);
    }

    //APP Debug
    public static String get_my_appDEBUG() {
        return String.valueOf(BuildConfig.DEBUG);
    }

    //APP版本代号
    public static String get_my_appVERSIONNAME() {
        return String.valueOf(BuildConfig.VERSION_NAME);
    }

    //APP版本代码
    public static String get_my_appVERSIONCODE() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }
}
