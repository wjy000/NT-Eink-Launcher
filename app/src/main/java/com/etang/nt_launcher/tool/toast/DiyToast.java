package com.etang.nt_launcher.tool.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etang.nt_launcher.R;


public class DiyToast {
    private static Toast toast;

    public static void showToast(Context context, String s, boolean long_or_short) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_back, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_toast_show);
        toast = null;
        if (long_or_short) {
            toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        }
        toast.setView(view);
        tv.setText(s);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}
