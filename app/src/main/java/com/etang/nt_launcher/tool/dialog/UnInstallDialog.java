package com.etang.nt_launcher.tool.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.launcher.settings.SettingActivity;
import com.etang.nt_launcher.launcher.settings.uirefresh.UireFreshActivity;
import com.etang.nt_launcher.launcher.settings.weather.WeatherActivity;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayImageUtil;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.tool.util.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class UnInstallDialog {
    public static void uninstall_app(final int position, final List<AppInfo> appInfos, final Context context, final Activity activity, final String pakename, final String app_name) {
        try {
            final AlertDialog builder = new AlertDialog.Builder(context).create();
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_uninstall, null);
            builder.setView(view);
            TextView tv_uninstall_appinfo = (TextView) view.findViewById(R.id.tv_uninstall_appinfo);
            Button btn_uninstall = (Button) view.findViewById(R.id.btn_uninstall_con);
            Button btn_cls = (Button) view.findViewById(R.id.btn_uninstall_cls);
            Button btn_hind = (Button) view.findViewById(R.id.btn_hind_con);
            Button btn_ico = (Button) view.findViewById(R.id.btn_load_ico);
            Button btn_openapp = (Button) view
                    .findViewById(R.id.btn_uninstall_openapp);
            Button btn_appname = (Button) view
                    .findViewById(R.id.btn_uninstall_appname);
            btn_appname.setText(app_name);
            builder.setTitle("包名：" + pakename);
            btn_openapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        builder.dismiss();
                        // Intent intent=appInfos.get(position).getIntent();
                        // startActivity(intent);
                        Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                                appInfos.get(position).getPackageName());
                        if (intent != null) {//点击的APP无异常
                            intent.putExtra("type", "110");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else if (appInfos.get(position).getPackageName().equals(context.getPackageName() + ".weather")) {//点击了“天气”
                            intent = new Intent(context, WeatherActivity.class);
                            context.startActivity(intent);
                        } else if (appInfos.get(position).getPackageName().equals(context.getPackageName() + ".systemupdate")) {//点击了“检查更新”
                            CheckUpdateDialog.check_update(context, activity);
                        } else if (appInfos.get(position).getPackageName().equals(context.getPackageName() + ".launchersetting")) {//点击了“桌面设置”
                            intent = new Intent(context, SettingActivity.class);
                            context.startActivity(intent);
                        } else if (appInfos.get(position).getPackageName().equals(context.getPackageName() + ".uirefresh")) {//点击了“刷新屏幕”
                            String s = Build.BRAND;
                            if (s.equals("Allwinner")) {
                                Intent intent_refresh = new Intent("android.eink.force.refresh");
                                context.sendBroadcast(intent_refresh);
                            } else {
                                context.startActivity(new Intent(context, UireFreshActivity.class));
                            }
                        } else if (appInfos.get(position).getPackageName().equals(context.getPackageName() + ".systemclean")) {//点击了“清理”
                            String s_clean = Build.BRAND;
                            if (s_clean.equals("Allwinner")) {
                                //唤醒广播
                                Intent intent_clear = new Intent("com.mogu.clear_mem");
                                context.sendBroadcast(intent_clear);
                            }
                        } else {//出现异常
                            DeBugDialog.debug_show_dialog(context, "启动APP时出现“Intent”相关的异常");
                        }
                    } catch (Exception e) {
                        DeBugDialog.debug_show_dialog(context, e.toString());
                    }
                }
            });
            btn_hind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.clear();
                        arrayList = SaveArrayListUtil.getSearchArrayList(context);
                        arrayList.add(MainActivity.string_app_info);
                        SaveArrayListUtil.saveArrayList(context, arrayList, "start");//存储在本地
                        builder.dismiss();
                        MainActivity.initAppList(context);
                    } catch (Exception e) {
                        DeBugDialog.debug_show_dialog(context, e.toString());
                    }
                }
            });
            btn_ico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_ico_dialog(context, activity);
                    builder.dismiss();
                }
            });
            btn_uninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UninstallApk(context, activity, pakename);
                    builder.dismiss();
                }
            });
            btn_cls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });
            builder.show();
            Window window = builder.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = 0f;
            window.setAttributes(lp);
        } catch (Exception e) {
            DeBugDialog.debug_show_dialog(context, e.toString());
        }
    }

    /**
     * 唤起系统的卸载apk功能
     */
    public static void UninstallApk(Context context, Activity activity, String pakename) {
        try {
            Uri packageURI = Uri.parse("package:" + pakename);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            activity.startActivityForResult(uninstallIntent, 1);
        } catch (Exception e) {
            DeBugDialog.debug_show_dialog(context, e.toString());
        }
    }


    private static void show_ico_dialog(final Context context, final Activity activity) {
        try {
            SavePermission.check_save_permission(activity);//检查存取权限
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_load_ico, null, false);
            builder.setView(view);
            final EditText et_load_ico_uri = (EditText) view.findViewById(R.id.et_load_ico_uri);
            builder.setTitle("请设置图标名称");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (et_load_ico_uri.getText().toString().isEmpty()) {
                        show_ico_dialog(context, activity);
                        DiyToast.showToast(context, "请输入文件名", true);
                    } else {
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList = SaveArrayImageUtil.getSearchArrayList(context);
                        arrayList.add(MainActivity.string_app_info + "-" + et_load_ico_uri.getText().toString());
                        SaveArrayImageUtil.saveArrayList(context, arrayList, "1");
                    }
                }
            });
            builder.setNeutralButton("重置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList = SaveArrayImageUtil.getSearchArrayList(context);
                    for (int i = 1; i < arrayList.size(); i++) {
                        String str = arrayList.get(i);
                        String[] all = str.split("-");
                        if (MainActivity.string_app_info.equals(all[0])) {
                            arrayList.remove(i);
                            continue;
                        }
                    }
                    SaveArrayImageUtil.saveArrayList(context, arrayList, "1");
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        } catch (Exception e) {
            DeBugDialog.debug_show_dialog(context, e.toString());
        }
    }
}
