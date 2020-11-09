package com.etang.nt_launcher.launcher.settings.weather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;


public class WeatherActivity extends Activity {
    private Button btn_wather_con, btn_wather_cls;
    private EditText et_city_get;
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView tv_back, tv_button, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_weather);
        initView();
        btn_wather_con.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_city_get.getText().toString().isEmpty()) {
                    DiyToast.showToast(getApplicationContext(), "请输入城市", true);
                } else {
                    db.execSQL("update wather_city set city = ? ",
                            new String[]{et_city_get.getText().toString()});
                    finish();
                }
            }
        });
        btn_wather_cls.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((RadioButton) findViewById(R.id.ra_weather_view_vis)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putBoolean("isHind_weather", false);
                editor.apply();
            }
        });
        ((RadioButton) findViewById(R.id.ra_weather_view_gone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putBoolean("isHind_weather", true);
                editor.apply();
            }
        });
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_button.setVisibility(View.INVISIBLE);
        tv_title.setText("天气设置");
    }

    private void initView() {
        // TODO Auto-generated method stub
        tv_back = (TextView) findViewById(R.id.tv_title_back);
        tv_button = (TextView) findViewById(R.id.tv_title_button);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        btn_wather_cls = (Button) findViewById(R.id.btn_wather_cls);
        btn_wather_con = (Button) findViewById(R.id.btn_wather_con);
        et_city_get = (EditText) findViewById(R.id.et_city_get);
        dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
                null, 2);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
