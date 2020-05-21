package com.etang.nt_launcher.launcher.settings.about;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.etang.nt_launcher.BuildConfig;
import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.dialog.CheckUpdateDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.toast.DiyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AboutActivity extends AppCompatActivity {
    private ImageView iv_about_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setTitle("你好");
                builder.setMessage("需要赞助以解锁这个功能。（其实是假的，桌面不会有任何收费功能，主要原因是开学了，服务器访问人数比较多，要节省资源。但是如果可以的话，还请赞助支持我一下QAQ）");
                builder.setPositiveButton("我知道了", null);
                builder.setNegativeButton("我知道了", null);
                builder.setNeutralButton("我知道了", null);
                builder.show();
//                SavePermission.check_save_permission(AboutActivity.this, AboutActivity.this);
//                CheckUpdateDialog.check_update(AboutActivity.this, AboutActivity.this);
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
