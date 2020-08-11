package com.etang.nt_launcher.launcher.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.launcher.settings.about.AboutActivity;
import com.etang.nt_launcher.launcher.settings.desktopsetting.DeskTopSettingActivity;
import com.etang.nt_launcher.launcher.settings.hindapp.HindAppSetting;
import com.etang.nt_launcher.launcher.settings.instructions.InstructionsActivity;
import com.etang.nt_launcher.launcher.settings.launcherimage.ChoseImagesActivity;
import com.etang.nt_launcher.launcher.settings.reward.RewardActivity;
import com.etang.nt_launcher.launcher.settings.textsizesetting.TextSizeSetting;
import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.dialog.UnInstallDialog;
import com.etang.nt_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class SettingActivity extends Activity {

    LinearLayout lv_window_setting, lv_reward_activity, lv_textsize_setting, lv_applist_setting, lv_about_activity, lv_desktop_setting, lv_exit_setting, lv_hindapp_setting, lv_name_setting, lv_uninstall_setting;
    private TextView tv_title_text;
    private CheckBox cb_hind_setting_ico, cb_setting_offlinemode;
    private ImageView iv_title_back;
    private MyDataBaseHelper dbHelper_name_sql;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
//        if (!Build.BRAND.toString().equals("Allwinner")) {
//        }
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
            }
        });
        //桌面应用列表设置
        lv_applist_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, DeskTopSettingActivity.class));
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
        //退出“奶糖桌面”
        lv_exit_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        //卸载“奶糖桌面”
        lv_uninstall_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UnInstallDialog.UninstallApk(SettingActivity.this, SettingActivity.this, getPackageName());
            }
        });
        lv_reward_activity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, RewardActivity.class));
            }
        });
        //昵称设置
        lv_name_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show_name_dialog();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String ico_info = sharedPreferences.getString("setting_ico_hind", null);
        String offline_info = sharedPreferences.getString("offline", null);
        try {
            if (ico_info.equals("true")) {
                cb_hind_setting_ico.setChecked(true);
//                MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
            } else {
                cb_hind_setting_ico.setChecked(false);
//                MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
            }
            if (offline_info.equals("true")) {
                cb_setting_offlinemode.setChecked(true);
            } else {
                cb_setting_offlinemode.setChecked(false);
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("setting_ico_hind", "false");//日期文本大小
            editor.apply();
        }
        cb_hind_setting_ico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("setting_ico_hind", "true");//日期文本大小
                    editor.apply();
                    DiyToast.showToast(SettingActivity.this, "有时需要重启设备以应用更改，长按桌面中的“小时”可以打开设置", true);
                    SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("setting_ico_hind", "false");//日期文本大小
                    editor.apply();
                    DiyToast.showToast(SettingActivity.this, "有时需要重启设备以应用更改，点击桌面中的“小时”可以打开设置", true);
                    SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
                }
            }
        });
        cb_setting_offlinemode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("offline", "true");//日期文本大小
                    editor.apply();
                    DiyToast.showToast(SettingActivity.this, "有时需要重启设备以应用更改", true);
                    SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("offline", "false");//日期文本大小
                    editor.apply();
                    DiyToast.showToast(SettingActivity.this, "有时需要重启设备以应用更改", true);
                    SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    MainActivity.check_view_hind(SettingActivity.this, sharedPreferences);
                }
            }
        });
        ((TextView) findViewById(R.id.tv_title_imagetext)).setText("说明书");
        ((TextView) findViewById(R.id.tv_title_imagetext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, InstructionsActivity.class));
            }
        });
        ((ImageView) findViewById(R.id.iv_title_imagebutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, InstructionsActivity.class));
            }
        });
    }
//
//    private void show_dialog() {
//        String version = BuildConfig.VERSION_NAME;
//        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//        builder.setMessage("当前版本\n" + version);
//        builder.setPositiveButton("我知道了", null);
//        builder.show();
//    }

    /**
     * 设置主界面的顶部文案
     */
    private void show_name_dialog() {
        final AlertDialog builder = new AlertDialog.Builder(
                SettingActivity.this).create();
        View view = LayoutInflater.from(SettingActivity.this).inflate(
                R.layout.dialog_name_show, null, false);
        builder.setView(view);
        final EditText et_name_get = (EditText) view
                .findViewById(R.id.et_title_name);
        final RadioButton ra_0 = (RadioButton) view
                .findViewById(R.id.radio0);
        final RadioButton ra_1 = (RadioButton) view
                .findViewById(R.id.radio1);
        final RadioButton ra_2 = (RadioButton) view
                .findViewById(R.id.radio2);
        final RadioButton ra_3 = (RadioButton) view
                .findViewById(R.id.radio3);
        final Button btn_con = (Button) view.findViewById(R.id.btn_dialog_rename_con);
        final Button btn_cls = (Button) view.findViewById(R.id.btn_dialog_rename_cls);
        builder.setTitle("请输入你的要显示的内容");
        btn_cls.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btn_con.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name_get.getText().toString().isEmpty()
                        && !ra_0.isChecked() && !ra_2.isChecked()
                        && !ra_3.isChecked() && !ra_1.isChecked()) {
                    db.execSQL("update name set username = ?",
                            new String[]{""});
                } else {
                    if (ra_0.isChecked() || ra_1.isChecked()
                            || ra_2.isChecked() || ra_3.isChecked()) {
                        if (ra_0.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_0.getText()
                                            .toString() + "电纸书"});
                        }
                        if (ra_1.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_1.getText()
                                            .toString() + "电纸书"});
                        }
                        if (ra_2.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_2.getText()
                                            .toString() + "电纸书"});
                        }
                        if (ra_3.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_3.getText()
                                            .toString() + "电纸书"});
                        }
                    } else {
                        db.execSQL("update name set username = ?",
                                new String[]{et_name_get
                                        .getText().toString()});
                    }
                }
                builder.dismiss();
                MainActivity.rember_name(SettingActivity.this);
            }
        });
        builder.show();
    }

    private void initView() {
        // TODO Auto-generated method stub
        lv_reward_activity = (LinearLayout) findViewById(R.id.lv_reward_activity);
        cb_setting_offlinemode = (CheckBox) findViewById(R.id.cb_setting_offlinemode);
        cb_hind_setting_ico = (CheckBox) findViewById(R.id.cb_hind_setting_ico);
        lv_name_setting = (LinearLayout) findViewById(R.id.lv_name_setting);
        lv_uninstall_setting = (LinearLayout) findViewById(R.id.lv_uninstall_setting);
        lv_hindapp_setting = (LinearLayout) findViewById(R.id.lv_hindapp_setting);
        lv_exit_setting = (LinearLayout) findViewById(R.id.lv_exit_setting);
        lv_textsize_setting = (LinearLayout) findViewById(R.id.lv_textsize_setting);
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        lv_desktop_setting = (LinearLayout) findViewById(R.id.lv_desktop_setting);
        lv_about_activity = (LinearLayout) findViewById(R.id.lv_about_activity);
        lv_applist_setting = (LinearLayout) findViewById(R.id.lv_applist_setting);
        lv_window_setting = (LinearLayout) findViewById(R.id.lv_window_setting);
        dbHelper_name_sql = new MyDataBaseHelper(getApplicationContext(), "info.db",
                null, 2);
        db = dbHelper_name_sql.getWritableDatabase();
    }
}