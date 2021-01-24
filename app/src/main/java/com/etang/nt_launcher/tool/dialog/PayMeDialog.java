package com.etang.nt_launcher.tool.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.etang.nt_launcher.R;

/**
 * 用于展示赞赏支付方式的弹出框
 */
public class PayMeDialog {
    public static void show_dialog(final Activity activity, String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (number.equals("no")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_payme, null, false);
            builder.setView(view);
        } else if (number.equals("ali")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_payme_ali, null, false);
            builder.setView(view);
        } else if (number.equals("aifadian")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_payme_aifadian, null, false);
            builder.setView(view);
        } else if (number.equals("wechat")) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_payme_wechat, null, false);
            builder.setView(view);
        }
        builder.setTitle("请选择平台");
        builder.setPositiveButton("支付宝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show_dialog(activity, "ali");
            }
        });
        builder.setNegativeButton("微信", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show_dialog(activity, "wechat");
            }
        });
        builder.setNeutralButton("关闭", null);
        builder.show();
    }
}
