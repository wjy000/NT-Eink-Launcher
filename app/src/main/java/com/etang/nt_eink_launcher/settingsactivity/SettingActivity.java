package com.etang.nt_eink_launcher.settingsactivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etang.nt_eink_launcher.util.SystemInFo;
import com.etang.nt_launcher.R;

public class SettingActivity extends Activity {

    LinearLayout lv_open_dk_window;
    LinearLayout lv_open_dksetting;
    LinearLayout lv_desktop_setting;
    LinearLayout lv_about_activity;
    LinearLayout lv_shuoming_activity;
    LinearLayout lv_applist_setting;
    private TextView tv_title_text;
    private ImageView iv_title_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_setting);
        initView();
        tv_title_text.setText("桌面设置");
        iv_title_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //隐藏暂时无用的选项
        lv_shuoming_activity.setVisibility(View.GONE);//说明
        if (SystemInFo.getDeviceManufacturer().toString().equals("Allwinner")) {
            lv_open_dksetting.setVisibility(View.VISIBLE);
            lv_open_dk_window.setVisibility(View.VISIBLE);
        } else {
            lv_open_dksetting.setVisibility(View.GONE);
            lv_open_dk_window.setVisibility(View.GONE);
        }
        //打开关于界面
        lv_about_activity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });
        //打开多看悬浮球设置
        lv_open_dk_window.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动其他APP的Activity示例
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.moan.moanwm", "com.moan.moanwm.MainActivity");
                intent.setComponent(cn);
                startActivity(intent);
                finish();
            }
        });
        //打开系统设置
        lv_open_dksetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动其他APP的Activity示例
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.duokan.mireader", "com.duokan.home.SystemSettingActivity");
                intent.setComponent(cn);
                startActivity(intent);
                finish();
            }
        });
        //壁纸设置
        lv_desktop_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChoseImagesActivity.class));
                finish();
            }
        });
        //壁纸设置
        lv_desktop_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChoseImagesActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        lv_open_dk_window = (LinearLayout) findViewById(R.id.lv_open_dk_window);
        lv_open_dksetting = (LinearLayout) findViewById(R.id.lv_open_dksetting);
        lv_desktop_setting = (LinearLayout) findViewById(R.id.lv_desktop_setting);
        lv_shuoming_activity = (LinearLayout) findViewById(R.id.lv_shuoming_activity);
        lv_about_activity = (LinearLayout) findViewById(R.id.lv_about_activity);
        lv_applist_setting = (LinearLayout) findViewById(R.id.lv_applist_setting);
    }
}