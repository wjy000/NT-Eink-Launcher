package com.etang.nt_launcher.launcher.settings.hindapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.tool.util.AppInfo;
import com.etang.nt_launcher.tool.util.DeskTopGridViewBaseAdapter;
import com.etang.nt_launcher.tool.util.GetApps;

import java.util.ArrayList;
import java.util.List;

public class HindAppSetting extends AppCompatActivity {
    private static List<AppInfo> appInfos = new ArrayList<AppInfo>();
    private static List<AppInfo> appHindInfos = new ArrayList<AppInfo>();
    private static GridView mHindGridView;
    private TextView tv_back, tv_button, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_hind_app);
        initView();
        initAppList(HindAppSetting.this);
        tv_title.setText("应用管理");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHindGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show_dialog_helper(appHindInfos.get(position).getPackageName());
            }
        });
        tv_button.setText("刷新");
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyToast.showToast(getApplicationContext(), "刷新成功", false);
                initAppList(getApplicationContext());
            }
        });
    }

    private void show_dialog_helper(final String packname) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HindAppSetting.this);
        builder.setTitle("要取消隐藏应用吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> hind_apparrayList = new ArrayList<String>();
                hind_apparrayList.clear();
                hind_apparrayList = SaveArrayListUtil.getSearchArrayList(HindAppSetting.this);
                for (int i = 0; i < hind_apparrayList.size(); i++) {
                    if (hind_apparrayList.get(i).equals(packname)) {
                        hind_apparrayList.remove(i);
                        continue;
                    }
                }
                SaveArrayListUtil.saveArrayList(HindAppSetting.this, hind_apparrayList, "1");
                initAppList(HindAppSetting.this);
            }
        });
        builder.setNeutralButton("取消", null);
        builder.show();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_title_back);
        tv_button = (TextView) findViewById(R.id.tv_title_button);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        mHindGridView = (GridView) findViewById(R.id.mHindGridView);
    }

    /**
     * +获取应用列表、隐藏应用
     *
     * @param context
     */
    public static void initAppList(Context context) {
        appInfos.clear();
        appHindInfos.clear();
        appInfos = GetApps.GetAppList1(context);
        ArrayList<String> hind_apparrayList = new ArrayList<String>();
        hind_apparrayList.clear();
        hind_apparrayList = SaveArrayListUtil.getSearchArrayList(context);
        for (int j = 0; j < hind_apparrayList.size(); j++) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (hind_apparrayList.get(j).equals(appInfos.get(i).getPackageName())) {
                    appHindInfos.add(appInfos.remove(i));
                    continue;
                }
            }
        }
        DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(appHindInfos,
                context);
        mHindGridView.setAdapter(deskTopGridViewBaseAdapter);
    }
}
