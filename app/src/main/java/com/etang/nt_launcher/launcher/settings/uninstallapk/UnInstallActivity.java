package com.etang.nt_launcher.launcher.settings.uninstallapk;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayImageUtil;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.toast.DiyToast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 卸载APP
 * @package_name com.example.dklauncherdemo.activitys
 * @project_name DKLauncherDemo
 * @file_name UnInstallActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class UnInstallActivity extends Activity {

    private TextView tv_pack_name;
    private Button btn_uninstall_con, btn_uninstall_cls, btn_hind_con, btn_upload_ico;
    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_uninstall);
        initView();
        //开启卸载界面的时候获取存储在本地的包名
        arrayList = SaveArrayListUtil.getSearchArrayList(UnInstallActivity.this);
        // 卸载按钮
        btn_uninstall_con.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UninstallApk();
            }
        });
        // 隐藏按钮
        btn_hind_con.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                arrayList = SaveArrayListUtil.getSearchArrayList(UnInstallActivity.this);
                arrayList.add(MainActivity.string_app_info);
                Log.e("UnInstall_arrayList", arrayList.toString());
                SaveArrayListUtil.saveArrayList(UnInstallActivity.this, arrayList, "start");//存储在本地
                finish();
            }
        });
        // 关闭按钮
        btn_uninstall_cls.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity.initAppList(getApplicationContext());// 刷新应用列表
                finish();// 结束当前活动
//                try {
//                    //虚拟返回按钮
//                    Runtime.getRuntime().exec("input keyevent 4");
//                } catch (Exception e) {
//                    Log.e("runtime", "error");
//                }
            }
        });
        //设置图标
        btn_upload_ico.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show_ico_dialog();
            }
        });
        // 设置显示包名
        tv_pack_name.setText(MainActivity.string_app_info);
    }

    private void show_ico_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UnInstallActivity.this);
        View view = LayoutInflater.from(UnInstallActivity.this).inflate(R.layout.dialog_load_ico, null, false);
        builder.setView(view);
        final EditText et_load_ico_uri = (EditText) view.findViewById(R.id.et_load_ico_uri);
        builder.setTitle("请设置图标名称");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et_load_ico_uri.getText().toString().isEmpty()) {
                    show_ico_dialog();
                    DiyToast.showToast(UnInstallActivity.this, "请输入文件名");
                } else {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList = SaveArrayImageUtil.getSearchArrayList(UnInstallActivity.this);
                    arrayList.add(MainActivity.string_app_info + "-" + et_load_ico_uri.getText().toString());
                    SaveArrayImageUtil.saveArrayList(UnInstallActivity.this, arrayList, "1");
                    finish();
                }
            }
        });
        builder.setNeutralButton("重置图标", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList = SaveArrayImageUtil.getSearchArrayList(UnInstallActivity.this);
                for (int i = 1; i < arrayList.size(); i++) {
                    String str = arrayList.get(i);
                    String[] all = str.split("-");
                    Log.e("TAG", all[0]);
                    if (MainActivity.string_app_info.equals(all[0])) {
                        arrayList.remove(i);
                        continue;
                    }
                }
                SaveArrayImageUtil.saveArrayList(UnInstallActivity.this, arrayList, "1");
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void initView() {
        // TODO Auto-generated method stub
        btn_uninstall_con = (Button) findViewById(R.id.btn_uninstall_con);
        btn_uninstall_cls = (Button) findViewById(R.id.btn_uninstall_cls);
        btn_hind_con = (Button) findViewById(R.id.btn_hind_con);
        tv_pack_name = (TextView) findViewById(R.id.tv_pack_name);
        btn_upload_ico = (Button) findViewById(R.id.btn_load_ico);
    }

    /**
     * 唤起系统的卸载apk功能
     */
    private void UninstallApk() {
        String packageName = MainActivity.string_app_info;
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivityForResult(uninstallIntent, 1);
    }

    /**
     * 这里有个问题，android 4.2.2模拟器环境下，卸载软件resultCode返回的并不是RESULT_OK，保险起见两处都加上刷新
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {// 检查返回Code
                    MainActivity.initAppList(getApplicationContext());// 刷新应用列表
                    finish();
                } else {
                    MainActivity.initAppList(getApplicationContext());// 刷新应用列表
                    finish();// 结束当前活动
                }
                break;
            default:
                break;
        }
    }
}