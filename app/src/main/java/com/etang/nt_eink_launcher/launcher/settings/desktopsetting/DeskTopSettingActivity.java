package com.etang.nt_eink_launcher.launcher.settings.desktopsetting;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import com.etang.nt_eink_launcher.tool.toast.DiyToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.etang.nt_launcher.R;

public class DeskTopSettingActivity extends AppCompatActivity {
    private TextView tv_title_text, tv_gridlist_setting;
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
        setContentView(R.layout.setting_activity_desk_top_setting);
        initView();//绑定控件
        tv_gridlist_setting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_gridlist_setting.getPaint().setAntiAlias(true);//抗锯齿
        /**
         * 设置列数文本点击事件
         */
        tv_gridlist_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_gridlist_setting();
            }
        });
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
        tv_gridlist_setting = (TextView) findViewById(R.id.tv_gridlist_setting);
    }

    public void show_gridlist_setting() {
        final AlertDialog builder = new AlertDialog.Builder(
                DeskTopSettingActivity.this).create();
        View view = LayoutInflater.from(DeskTopSettingActivity.this).inflate(
                R.layout.dialog_desktop_setting, null, false);
        builder.setView(view);
//        Window window = builder.getWindow();
//        builder.getWindow();
//        window.setGravity(Gravity.CENTER); // 底部位置
//        window.setContentView(view);
        final Button btn_dialog_listnumber_con = (Button) view
                .findViewById(R.id.btn_dialog_listnumber_con);
        final Button btn_dialog_listnumber_cls = (Button) view
                .findViewById(R.id.btn_dialog_listnumber_cls);
        final Button btn_dialog_listnumber_auto = (Button) view
                .findViewById(R.id.btn_dialog_listnumber_auto);
        final SeekBar seekBar_listnumber = (SeekBar) view
                .findViewById(R.id.seekBar_listnumber);
        final TextView tv_listnumber_number = (TextView) view
                .findViewById(R.id.tv_listnumber_number);
        final EditText et_dialog_applist_number = (EditText) view
                .findViewById(R.id.et_dialog_applist_number);
        seekBar_listnumber.setProgress(1);
        seekBar_listnumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_listnumber_number.setText(String.valueOf(progress) + "列");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn_dialog_listnumber_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("applist_number", "auto");
                editor.apply();
                builder.dismiss();
            }
        });
        btn_dialog_listnumber_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_dialog_applist_number.getText().toString().isEmpty()) {
                    mode_seekbar(seekBar_listnumber.getProgress());
                } else {
                    mode_edittext(Integer.valueOf(et_dialog_applist_number.getText().toString()));
                }
                builder.dismiss();
            }
        });
        btn_dialog_listnumber_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.show();
    }


    private void mode_edittext(int number) {
        if (number == 0) {
            DiyToast.showToast(getApplicationContext(), "不能为“0”");
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("applist_number", String.valueOf(number));
            editor.apply();
        }
    }

    private void mode_seekbar(int number) {
        if (number == 0) {
            DiyToast.showToast(getApplicationContext(), "不能为“0”");
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("applist_number", String.valueOf(number));
            editor.apply();
        }
    }
}
