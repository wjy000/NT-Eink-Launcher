package com.etang.nt_launcher.launcher.settings.desktopsetting;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.toast.DiyToast;
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

public class DeskTopSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_title_text, tv_gridlist_setting;
    private ImageView iv_title_back;
    private RadioButton ra_appname_hind;
    private RadioButton ra_appname_one;
    private RadioButton ra_appname_nope;
    private RadioButton ra_app_show_blok;
    private RadioButton ra_app_hind_blok;
    private RadioButton ra_app_bottommode_bottom;
    private RadioButton ra_app_bottommode_top;
    private RadioButton ra_app_show_nocolor_blok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_activity_desk_top_setting);
        initView();//绑定控件
        check_app_list();
        check_app_bottom();
        tv_gridlist_setting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_gridlist_setting.getPaint().setAntiAlias(true);//抗锯齿
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
    }

    private void check_app_bottom() {
        SharedPreferences sharedPreferences_applist_bottom = getSharedPreferences("info", MODE_PRIVATE);
        if (sharedPreferences_applist_bottom.getBoolean("app_setStackFromBottomMode", true) == false) {
            ra_app_bottommode_top.setChecked(true);
            ra_app_bottommode_bottom.setChecked(false);
        } else {
            ra_app_bottommode_top.setChecked(false);
            ra_app_bottommode_bottom.setChecked(true);
        }
    }

    private void check_app_list() {
        SharedPreferences sharedPreferences = getSharedPreferences("info_app_list_state", MODE_PRIVATE);
        String appname_state = sharedPreferences.getString("appname_state", null);
        String appblok_state = sharedPreferences.getString("appblok_state", null);
        switch (appname_state) {
            case "one":
                ra_appname_one.setChecked(true);
                break;
            case "hind":
                ra_appname_hind.setChecked(true);
                break;
            case "nope":
                ra_appname_nope.setChecked(true);
                break;
        }
        switch (appblok_state) {
            case "hind_blok":
                ra_app_hind_blok.setChecked(true);
                break;
            case "show_blok":
                ra_app_show_blok.setChecked(true);
                break;
            case "show_nocolor_blok":
                ra_app_show_nocolor_blok.setChecked(true);
                break;
        }
    }

    private void initView() {
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        ra_appname_hind = (RadioButton) findViewById(R.id.ra_appname_hind);
        ra_appname_hind.setOnClickListener(this);
        ra_appname_one = (RadioButton) findViewById(R.id.ra_appname_one);
        ra_appname_one.setOnClickListener(this);
        ra_appname_nope = (RadioButton) findViewById(R.id.ra_appname_nope);
        ra_appname_nope.setOnClickListener(this);
        ra_app_show_blok = (RadioButton) findViewById(R.id.ra_app_show_blok);
        ra_app_show_blok.setOnClickListener(this);
        ra_app_hind_blok = (RadioButton) findViewById(R.id.ra_app_hind_blok);
        ra_app_hind_blok.setOnClickListener(this);
        tv_gridlist_setting = (TextView) findViewById(R.id.tv_gridlist_setting);
        tv_gridlist_setting.setOnClickListener(this);
        ra_app_bottommode_bottom = (RadioButton) findViewById(R.id.ra_app_bottommode_bottom);
        ra_app_bottommode_bottom.setOnClickListener(this);
        ra_app_bottommode_top = (RadioButton) findViewById(R.id.ra_app_bottommode_top);
        ra_app_bottommode_top.setOnClickListener(this);
        ra_app_show_nocolor_blok = (RadioButton) findViewById(R.id.ra_app_show_nocolor_blok);
        ra_app_show_nocolor_blok.setOnClickListener(this);
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
        seekBar_listnumber.setMax(20);
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
            DiyToast.showToast(getApplicationContext(), "不能为“0”", true);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("applist_number", String.valueOf(number));
            editor.apply();
        }
    }

    private void mode_seekbar(int number) {
        if (number == 0) {
            DiyToast.showToast(getApplicationContext(), "不能为“0”", true);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("applist_number", String.valueOf(number));
            editor.apply();
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("info_app_list_state", MODE_PRIVATE).edit();
        SharedPreferences.Editor editor_bottommode = getSharedPreferences("info", MODE_PRIVATE).edit();
        switch (v.getId()) {
            case R.id.tv_gridlist_setting:
                show_gridlist_setting();
                break;
            case R.id.ra_app_hind_blok:
                editor.putString("appblok_state", "hind_blok");
                editor.apply();
                DiyToast.showToast(getApplicationContext(), "已设置为：隐藏边框", true);
                MainActivity.initAppList(DeskTopSettingActivity.this);
                break;
            case R.id.ra_app_show_blok:
                editor.putString("appblok_state", "show_blok");
                editor.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：显示边框", true);
                break;
            case R.id.ra_app_show_nocolor_blok:
                editor.putString("appblok_state", "show_nocolor_blok");
                editor.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：显示边框，背景透明", true);
                break;
            case R.id.ra_appname_one:
                editor.putString("appname_state", "one");
                editor.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：限制为一行", true);
                break;
            case R.id.ra_appname_nope:
                editor.putString("appname_state", "nope");
                editor.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：显示", true);
                break;
            case R.id.ra_appname_hind:
                editor.putString("appname_state", "hind");
                editor.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：隐藏", true);
                break;
            case R.id.ra_app_bottommode_bottom:
                editor_bottommode.putBoolean("app_setStackFromBottomMode", true);
                editor_bottommode.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：从底部开始排列", true);
                break;
            case R.id.ra_app_bottommode_top:
                editor_bottommode.putBoolean("app_setStackFromBottomMode", false);
                editor_bottommode.apply();
                MainActivity.initAppList(DeskTopSettingActivity.this);
                DiyToast.showToast(getApplicationContext(), "已设置为：从顶部开始排列", true);
                break;
        }
    }
}
