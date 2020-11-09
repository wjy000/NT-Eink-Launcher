package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;

public class DeBugDialog {
    public static void debug_show_dialog(final Context context, final String e) {
        final AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setTitle("出现错误");
        builder.setMessage("错误信息：" + "\n" + e.toString());
        builder.setButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.setButton2("推送错误信息到服务器", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewUserDialog.dialog_show(context, e);
                builder.dismiss();
            }
        });
        builder.show();
        Window window = builder.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        window.setAttributes(lp);
    }
}
