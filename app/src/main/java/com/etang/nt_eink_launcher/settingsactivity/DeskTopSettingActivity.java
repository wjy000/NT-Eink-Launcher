package com.etang.nt_eink_launcher.settingsactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.R;

public class DeskTopSettingActivity extends AppCompatActivity {
    private TextView tv_title_text;
    private ImageView iv_title_back;
    private RadioButton ra_appname_hind;
    private RadioButton ra_appname_one;
    private RadioButton ra_appname_nope;
    private RadioButton ra_app_show_blok;
    private RadioButton ra_app_hind_blok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_desk_top_setting);

        initView();//绑定控件

        /**
         * 设置title
         */
        tv_title_text.setText("应用列表设置");
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ra_appname_hind.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
                    editor.putString("appname_state", "hind");
                    editor.apply();
                    finish();
                    Log.e("APP_SETTING", "存储hind");
                }
                if (ra_appname_nope.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
                    editor.putString("appname_state", "nope");
                    editor.apply();
                    finish();
                    Log.e("APP_SETTING", "存储nope");
                }
                if (ra_appname_one.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
                    editor.putString("appname_state", "one");
                    editor.apply();
                    finish();
                    Log.e("APP_SETTING", "存储one");
                }
                if (ra_app_hind_blok.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
                    editor.putString("appblok_state", "hind_blok");
                    editor.apply();
                    finish();
                    Log.e("APP_SETTING", "存储隐藏");
                }
                if (ra_app_show_blok.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
                    editor.putString("appblok_state", "show_blok");
                    editor.apply();
                    finish();
                    Log.e("APP_SETTING", "存储显示");
                }
            }
        });
    }

    private void initView() {
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        ra_appname_hind = (RadioButton) findViewById(R.id.ra_appname_hind);
        ra_appname_one = (RadioButton) findViewById(R.id.ra_appname_one);
        ra_appname_nope = (RadioButton) findViewById(R.id.ra_appname_nope);
        ra_app_show_blok = (RadioButton) findViewById(R.id.ra_app_show_blok);
        ra_app_hind_blok = (RadioButton) findViewById(R.id.ra_app_hind_blok);
    }

}
