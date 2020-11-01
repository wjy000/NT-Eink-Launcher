package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.etang.nt_launcher.launcher.MainActivity;

public class DeBugDialog {
    public static void debug_show_dialog(final Context context, final String e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("出现错误");
        builder.setMessage("错误信息：" + "\n" + e.toString());
        builder.setNeutralButton("关闭", null);
        builder.setPositiveButton("推送错误信息到服务器", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewUserDialog.dialog_show(context, e);
            }
        });
        builder.show();
    }
}
