package com.etang.nt_launcher.launcher.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etang.nt_launcher.launcher.settings.about.AboutActivity;
import com.etang.nt_launcher.launcher.settings.desktopsetting.DeskTopSettingActivity;
import com.etang.nt_launcher.launcher.settings.hindapp.HindAppSetting;
import com.etang.nt_launcher.launcher.settings.launcherimage.ChoseImagesActivity;
import com.etang.nt_launcher.launcher.settings.textsizesetting.TextSizeSetting;
import com.etang.nt_launcher.launcher.settings.wather.WeatherSettingActivity;
import com.etang.nt_launcher.R;

public class SettingActivity extends Activity {

    LinearLayout lv_weather_gone_setting, lv_textsize_setting, lv_applist_setting, lv_about_activity, lv_desktop_setting, lv_hindapp_setting;
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
        tv_title_text.setText("桌面设置（v3.0）");
        iv_title_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //隐藏暂时无用的选项
        if (!Build.BRAND.toString().equals("Allwinner")) {
        }
        //打开关于界面
        lv_about_activity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });
//        //打开系统设置
//        lv_open_dksetting.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //启动其他APP的Activity示例
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                ComponentName cn = new ComponentName("com.duokan.mireader", "com.duokan.home.SystemSettingActivity");
//                intent.setComponent(cn);
//                startActivity(intent);
//                finish();
//            }
//        });
        //壁纸设置
        lv_desktop_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChoseImagesActivity.class));
                finish();
            }
        });
        //桌面应用列表设置
        lv_applist_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, DeskTopSettingActivity.class));
            }
        });
        //天气设置
        lv_weather_gone_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, WeatherSettingActivity.class));
            }
        });
        //文本大小设置
        lv_textsize_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, TextSizeSetting.class));
            }
        });
        //应用管理设置
        lv_hindapp_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, HindAppSetting.class));
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        lv_hindapp_setting = (LinearLayout) findViewById(R.id.lv_hindapp_setting);
        lv_textsize_setting = (LinearLayout) findViewById(R.id.lv_textsize_setting);
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        lv_desktop_setting = (LinearLayout) findViewById(R.id.lv_desktop_setting);
        lv_about_activity = (LinearLayout) findViewById(R.id.lv_about_activity);
        lv_applist_setting = (LinearLayout) findViewById(R.id.lv_applist_setting);
        lv_weather_gone_setting = (LinearLayout) findViewById(R.id.lv_weather_gone_setting);
    }
}