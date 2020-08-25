package com.etang.nt_launcher.launcher.settings.about;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.BuildConfig;
import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.dialog.CheckUpdateDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;

/**
 * “关于”界面，用于显示APP相关信息和软件更新
 */
public class AboutActivity extends AppCompatActivity {
    private ImageView iv_about_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_about);
        setTitle("关于");
        //返回按钮（整个顶栏LinearLayout）
        ((ImageView) findViewById(R.id.iv_title_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //标题
        String version = BuildConfig.VERSION_NAME;
        ((TextView) findViewById(R.id.tv_title_text)).setText("关于  " + version);
        iv_about_logo = (ImageView) findViewById(R.id.iv_about_logo);
        iv_about_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePermission.check_save_permission(AboutActivity.this, AboutActivity.this);
                CheckUpdateDialog.check_update(AboutActivity.this, AboutActivity.this);
            }
        });
        ((TextView) findViewById(R.id.tv_title_imagetext)).setText("当前版本");
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
        String version = BuildConfig.VERSION_NAME;
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
        builder.setMessage("当前版本\n" + version);
        builder.setPositiveButton("我知道了", null);
        builder.show();
    }
}
