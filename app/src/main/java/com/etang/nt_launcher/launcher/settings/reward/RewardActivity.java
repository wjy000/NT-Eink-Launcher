package com.etang.nt_launcher.launcher.settings.reward;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.etang.nt_launcher.launcher.settings.about.AboutActivity;
import com.etang.nt_launcher.tool.dialog.CheckUpdateDialog;

public class RewardActivity extends AppCompatActivity {
    private ImageView iv_about_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_reward);
        setTitle("关于");
        //返回按钮（整个顶栏LinearLayout）
        ((ImageView) findViewById(R.id.iv_title_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //标题
        ((TextView) findViewById(R.id.tv_title_text)).setText("打赏");
        ((TextView) findViewById(R.id.tv_title_imagetext)).setText("如何打赏");
        ((TextView) findViewById(R.id.tv_title_imagetext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
        ((ImageView) findViewById(R.id.iv_title_imagebutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
    }

    private void show_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RewardActivity.this);
        builder.setMessage("您可以扫描图片上的二维码或者登陆网址：afdian.net/@naiyouhuameitang");
        builder.setPositiveButton("我知道了", null);
        builder.show();
    }
}
