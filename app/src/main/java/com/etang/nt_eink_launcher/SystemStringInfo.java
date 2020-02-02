package com.etang.nt_eink_launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etang.nt_launcher.R;

public class SystemStringInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_system_string_info);
        /**
         * 设置 title bar 信息
         */
        //返回按钮（整个顶栏LinearLayout）
        ((LinearLayout) findViewById(R.id.lv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //标题
        ((TextView) findViewById(R.id.tv_back_title)).setText("设备信息");
        //设备SDK版本
        ((TextView) findViewById(R.id.tv_system_sdkinfo)).setText(String.valueOf(SystemInFo.getDeviceSDK()));
        //设备厂商
        ((TextView) findViewById(R.id.tv_system_devicemanufacturer)).setText(String.valueOf(SystemInFo.getDeviceManufacturer()));
        //设备产品名
        ((TextView) findViewById(R.id.tv_system_deviceproduct)).setText(String.valueOf(SystemInFo.getDeviceManufacturer()));
        //设备品牌
        ((TextView) findViewById(R.id.tv_system_devicebrand)).setText(String.valueOf(SystemInFo.getDeviceManufacturer()));
        //设备主板名
        ((TextView) findViewById(R.id.tv_system_deviceboard)).setText(String.valueOf(SystemInFo.getDeviceBoard()));
    }
}
