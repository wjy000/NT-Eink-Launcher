package com.etang.nt_eink_launcher.settingsactivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.R;

public class AboutActivity extends AppCompatActivity {
    TextView tv_system_coolapk, tv_system_fucher;

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
        /**
         * 增加下划线
         */
        tv_system_coolapk.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_system_coolapk.getPaint().setAntiAlias(true);//抗锯齿
        tv_system_fucher.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_system_fucher.getPaint().setAntiAlias(true);//抗锯齿

        tv_system_coolapk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, CoolApkActivity.class));
            }
        });
        tv_system_fucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, FucherActivity.class));
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
        tv_system_coolapk = (TextView) findViewById(R.id.tv_system_qcode);
        tv_system_fucher = (TextView) findViewById(R.id.tv_system_fucher);
    }
}
