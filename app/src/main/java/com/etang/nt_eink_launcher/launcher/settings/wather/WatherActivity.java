package com.etang.nt_eink_launcher.launcher.settings.wather;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.etang.nt_eink_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_eink_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;


public class WatherActivity extends Activity {
    private Button btn_wather_con;
    private EditText et_city_get;
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting_wather);
        initView();
        btn_wather_con.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_city_get.getText().toString().isEmpty()) {
                    DiyToast.showToast(getApplicationContext(), "请输入城市");
                } else {
                    db.execSQL("update wather_city set city = ? ",
                            new String[]{et_city_get.getText().toString()});
                    finish();
                }
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        btn_wather_con = (Button) findViewById(R.id.btn_wather_con);
        et_city_get = (EditText) findViewById(R.id.et_city_get);
        dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
                null, 2);
        db = dbHelper.getWritableDatabase();
    }
}
