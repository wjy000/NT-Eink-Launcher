package com.etang.nt_eink_launcher.launcher.settings.wather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.R;

public class WeatherSettingActivity extends AppCompatActivity {
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
        setContentView(R.layout.setting_weather_setting);
        initView();
        tv_title_text.setText("桌面设置");
        iv_title_back.setOnClickListener(new View.OnClickListener() {
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

    }

    private void initView() {
        // TODO Auto-generated method stub
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
    }
}