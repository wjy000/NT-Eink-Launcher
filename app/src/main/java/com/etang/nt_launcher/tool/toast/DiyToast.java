package com.etang.nt_launcher.tool.toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etang.nt_launcher.R;


public class DiyToast {
    private static Toast toast;

    public static void showToast(Context context, String s) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_back, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_toast_show);
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.setView(view);
            tv.setText(s);
        } else {
            toast.setView(view);
            tv.setText(s);
        }
        toast.show();
    }
}
