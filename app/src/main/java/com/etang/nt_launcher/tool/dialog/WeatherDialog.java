package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class WeatherDialog {
    private static MyDataBaseHelper dbHelper;
    private static SQLiteDatabase db;

    public static void weather_setting(final Context context) {
        try {
            final AlertDialog builder = new AlertDialog.Builder(context).create();
            builder.setTitle("请设置城市");
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_wather, null);
            final Button btn_wather_cls = (Button) view.findViewById(R.id.btn_wather_cls);
            final Button btn_wather_con = (Button) view.findViewById(R.id.btn_wather_con);
            final EditText et_city_get = (EditText) view.findViewById(R.id.et_city_get);
            dbHelper = new MyDataBaseHelper(context, "info.db",
                    null, 2);
            db = dbHelper.getWritableDatabase();
            btn_wather_cls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });
            btn_wather_con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_city_get.getText().toString().isEmpty()) {
                        DiyToast.showToast(context, "请输入城市", true);
                    } else {
                        db.execSQL("update wather_city set city = ? ",
                                new String[]{et_city_get.getText().toString()});
                        DiyToast.showToast(context, "设置成功，请点击“天气”区域进行刷新", true);
                        builder.dismiss();
                    }
                }
            });
            builder.setView(view);
            builder.show();
        } catch (Exception e) {
            DeBugDialog.debug_show_dialog(context, e.toString());
        }
    }
}