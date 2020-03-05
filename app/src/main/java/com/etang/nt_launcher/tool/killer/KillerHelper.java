package com.etang.nt_launcher.tool.killer;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.etang.nt_launcher.tool.toast.DiyToast;

import java.util.List;

public class KillerHelper {

    public static void CleaningOperation(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);
//                Log.d("TAGDD1", "-----------before memory info : " +this.getAvailMemory(getApplicationContext()));
        int count = 0;
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Log.e("TAGDD1", "process name : ----------" + appProcessInfo.processName);
                //importance 该进程的重要程度  分为几个级别，数值越低就越重要。
                Log.e("TAGDD2", "importance : -----------" + appProcessInfo.importance);
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
//                        if (appProcessInfo.importance > 100) {
//                            String[] pkgList = appProcessInfo.pkgList;
//                            for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
//                                Log.e("TAGDD4", "It will be killed, package name : " + pkgList[j]);
//                                am.killBackgroundProcesses(pkgList[j]);
//                                count++;
//                            }
//                        }
                //只要不是com.example.hasee.a1011ceshi这个包名的进程，其余进程全部禁止
                if (!appProcessInfo.processName.equals("com.etang.nt_eink_launcher")) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.e("TAGDD3", "It will be killed, package name : ----------" + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                        Log.e("TAGDD4", "count : ----------" + count + "");
                        DiyToast.showToast(context, "后台已清空");
                    }
                }
            }
        }
    }
}





