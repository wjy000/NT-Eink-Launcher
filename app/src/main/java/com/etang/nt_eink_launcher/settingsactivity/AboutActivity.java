package com.etang.nt_eink_launcher.settingsactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.R;

public class AboutActivity extends AppCompatActivity {
    TextView tv_system_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_about);
        setTitle("关于");
        initView();
        tv_system_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, SystemStringInfo.class));
            }
        });
        //返回按钮（整个顶栏LinearLayout）
        ((ImageView) findViewById(R.id.iv_title_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //标题
        ((TextView) findViewById(R.id.tv_title_text)).setText("关于");
    }

    private void initView() {
        tv_system_info = (TextView) findViewById(R.id.tv_system_info);
    }
}
