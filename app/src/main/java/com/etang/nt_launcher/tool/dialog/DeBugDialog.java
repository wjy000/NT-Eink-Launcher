package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;

/**
 * 用于展示报错信息的弹出框
 */
public class DeBugDialog {
    /**
     * 显示报错Dialog，用于错误信息判断
     *
     * @param context Context继承
     * @param e       报错信息
     * @param TAG     当前报错页面的TAG
     */
    public static void debug_show_dialog(final Context context, final String e, final String TAG) {
        //新建一个AlertDialog
        final AlertDialog builder = new AlertDialog.Builder(context).create();
        //设置标题
        builder.setTitle(TAG + "出现错误");
        //设置信息
        builder.setMessage("错误信息：" + "\n" + e.toString());
        //关闭按钮
        builder.setButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        //推送信息按钮
        builder.setButton2("发送错误（需要网络）", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewUserDialog.dialog_show(context, e, false);
                builder.dismiss();
            }
        });
        //显示Dialog
        builder.show();
        Window window = builder.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        window.setAttributes(lp);
    }
}
