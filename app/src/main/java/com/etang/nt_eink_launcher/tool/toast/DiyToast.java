package com.etang.nt_eink_launcher.tool.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示框
 */
public class DiyToast {
    private static Toast toast;

    public static void showToast(Context context, String string) {
        if (toast == null) {
            toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        } else {
            toast.setText(string);
        }
        toast.show();
    }
}