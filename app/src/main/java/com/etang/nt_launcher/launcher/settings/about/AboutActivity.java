package com.etang.nt_launcher.launcher.settings.about;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.dialog.CheckUpdateDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;

/**
 * “关于”界面，用于显示APP相关信息和软件更新
 */
public class AboutActivity extends AppCompatActivity {
    private ImageView iv_about_logo;
    private TextView tv_back, tv_title, tv_button;
    private LinearLayout lv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_about);
        lv_back = (LinearLayout) findViewById(R.id.lv_back);
        iv_about_logo = (ImageView) findViewById(R.id.iv_about_logo);
        tv_back = (TextView) findViewById(R.id.tv_title_back);
        tv_button = (TextView) findViewById(R.id.tv_title_button);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        //标题
        tv_title.setText(getString(R.string.string_about));
        tv_button.setText(getString(R.string.string_version));
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AboutActivity.this).setTitle("部分图片来自：iconfont.cn").setMessage("图标（launcher icon）：小白熊_猫草君 | \"糖果\"icon").setNegativeButton("关闭", null).show();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_about_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePermission.check_save_permission(AboutActivity.this);
                CheckUpdateDialog.check_update(AboutActivity.this, AboutActivity.this);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
