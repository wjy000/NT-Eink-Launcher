package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.etang.nt_launcher.launcher.MainActivity;

public class DeBugDialog {
    public static void debug_show_dialog(Context context, String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("出现错误");
        builder.setMessage("错误信息：" + "\n" + e.toString());
        builder.setNeutralButton("关闭", null);
        builder.show();
    }
}
