package com.etang.nt_eink_launcher.launcher.settings.uninstallapk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.etang.nt_eink_launcher.launcher.MainActivity;
import com.etang.nt_eink_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_launcher.R;

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
    private Button btn_uninstall_con, btn_uninstall_cls, btn_hind_con;
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
        // 设置显示包名
        tv_pack_name.setText(MainActivity.string_app_info);
    }

    private void initView() {
        // TODO Auto-generated method stub
        btn_uninstall_con = (Button) findViewById(R.id.btn_uninstall_con);
        btn_uninstall_cls = (Button) findViewById(R.id.btn_uninstall_cls);
        btn_hind_con = (Button) findViewById(R.id.btn_hind_con);
        tv_pack_name = (TextView) findViewById(R.id.tv_pack_name);
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