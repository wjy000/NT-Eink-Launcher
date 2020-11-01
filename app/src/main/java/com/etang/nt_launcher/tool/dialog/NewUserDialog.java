package com.etang.nt_launcher.tool.dialog;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class NewUserDialog {
    private static String SKEY = "SCU66788Tac2bf7385575174e067c917d471e25365dd3983cec5ee";
    private static String web_index = "sc.ftqq.com";


    public static void dialog_show(Context context, String info) {
        test(context, info);
    }

    private static void test(Context context, String info) {
        Build bd = new Build();
        String model = bd.MODEL;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newuser, null, false);
        WebView wv = (WebView) view.findViewById(R.id.webview_newuser);
        wv.loadUrl("https://" + web_index + "/" + SKEY
                + ".send?text=【奶糖桌面】" + "-------" + info + model);
        DiyToast.showToast(context, "激活成功", true);
    }
}
