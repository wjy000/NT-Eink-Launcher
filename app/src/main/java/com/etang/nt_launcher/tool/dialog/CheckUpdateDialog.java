package com.etang.nt_launcher.tool.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.etang.nt_launcher.BuildConfig;
import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.toast.DiyToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * 用于展示软件更新信息的弹出框
 */
public class CheckUpdateDialog {
    //  上下文
    private static Context mContext;
    //  进度条
    private static ProgressBar mProgressBar;
    //  对话框
    private static Dialog mDownloadDialog;
    //  判断是否停止
    private static boolean mIsCancel = false;
    //  进度
    private static int mProgress;
    //  文件保存路径
    private static String mSavePath;
    //  版本名称
    private static String mVersion_name = "梅糖桌面";
    //当前TAG
    private static String TAG = "CheckUpdateDialog";

    public static void check_update(final Context context, final Activity activity) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载......");
        progressDialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("weblink_state");
                switch (val) {
                    case "1":
                        DiyToast.showToast(context, "正在连接，请稍后", true);
                        break;
                    case "2":
                        DiyToast.showToast(context, "出现错误，请重试！", true);
                        Bundle data_error = msg.getData();
                        String error_message = data_error.getString("error_message");
                        DeBugDialog.debug_show_dialog(context, error_message, TAG);
                        progressDialog.dismiss();
                        break;
                    case "3":
                        DiyToast.showToast(context, "连接成功，解析中", true);
                        progressDialog.setMessage("连接成功，解析中");
                        break;
                    case "4":
                        DiyToast.showToast(context, "", true);
                        progressDialog.setMessage("");
                        break;
                    case "5":
                        DiyToast.showToast(context, "加载完成", true);
                        progressDialog.dismiss();
                        Bundle data_version = msg.getData();
                        String version_message = data_version.getString("version_message");
                        String new_version_message = version_message.replace("<li>", ""); //得到新的字符串
                        new_version_message = new_version_message.replace("</li>", "");
                        version_update(context, new_version_message, activity);
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg_start = new Message();
                Bundle data = new Bundle();
                data.putString("weblink_state", "1");
                msg_start.setData(data);
                handler.sendMessage(msg_start);
                try {
                    Log.e("TAG111111111111111", "开始链接");
                    Document doc = Jsoup.connect(" https://blog.nyanon.online/24").get();
                    /**
                     * 连接成功
                     * */
                    Message msg_link_ok = new Message();
                    Bundle data_link_ok = new Bundle();
                    data_link_ok.putString("weblink_state", "3");
                    msg_link_ok.setData(data_link_ok);
                    handler.sendMessage(msg_link_ok);
                    /**
                     * 开始解析
                     * */
                    final Elements titleAndPic = doc.select("div.post-body");
                    Log.e("HTML", String.valueOf(titleAndPic.get(0).select("ul").select("li")));
                    /**
                     * 发送结果
                     * */
                    Message msg_version = new Message();
                    Bundle data_version = new Bundle();
                    data_version.putString("weblink_state", "5");
                    data_version.putString("version_message", String.valueOf(titleAndPic.get(0).select("ul").select("li")));
                    msg_version.setData(data_version);
                    handler.sendMessage(msg_version);
                } catch (Exception e) {
                    Log.e("TAG111111111111111", e.toString());
                    Message msg_link_error = new Message();
                    Bundle data_error = new Bundle();
                    data_error.putString("weblink_state", "2");
                    data_error.putString("error_message", e.toString());
                    msg_link_error.setData(data_error);
                    handler.sendMessage(msg_link_error);
                }
            }
        }).start();
    }

    private static void version_update(final Context context, String s, final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //截取之后的字符
        String version_web = s.substring(0, s.indexOf("(" + context.getPackageName() + ")"));
        String version_web_1 = version_web.substring(s.indexOf("code"));
        Log.e("VERSION 1", version_web_1);
        String version_web_5 = version_web_1.substring(version_web_1.indexOf("code"));
        version_web_5 = version_web_5.replace("code", ""); //得到新的字符串
        String version = BuildConfig.VERSION_NAME;
        int version_code = BuildConfig.VERSION_CODE;
        int version_web_code = Integer.valueOf(version_web_5);
        if (version.indexOf("beta") != -1) { //"如果是beta版"
            check_beta(builder, version_code, version_web_code, context, activity, true);//内测版
        } else {
            check_beta(builder, version_code, version_web_code, context, activity, false);//稳定版
        }
        builder.setPositiveButton("博客地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DiyToast.showToast(context, "https://blog.nyanon.online/nt_launcher", true);
                web_html(context);
            }
        });
        builder.setTitle("当前APP版本：" + BuildConfig.VERSION_NAME);
        builder.show();
    }

    private static void check_beta(final AlertDialog.Builder builder, int version_code, int version_web_code, final Context context, final Activity activity, boolean is_beta) {
        if (version_code != version_web_code) {
            if (is_beta) {
                mVersion_name = mVersion_name + "_beta";
                builder.setMessage("你是内测版，请前往“爱发电”电圈内查看是否有新版本，或者尝试直接下载。");
                builder.setNeutralButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startUpdate(context, activity);
                    }
                });
            } else {
                if (version_code > version_web_code) {
                    mVersion_name = mVersion_name + "_" + String.valueOf(version_web_code);
                    builder.setMessage("当前版本CODE：\n" + String.valueOf(version_code) + "\n最新版本CODE：\n" + String.valueOf(version_web_code) + "\n\n你的版本比目前发布的稳定版还要高，可能你使用的是内测版或者第三方修改的不稳定版本");
                    DiyToast.showToast(context, "你的版本比目前发布的稳定版还要高，可能你使用的是内测版或者第三方修改的不稳定版本。", true);
                    builder.setNeutralButton("关闭", null);
                } else {
                    mVersion_name = mVersion_name + "_" + String.valueOf(version_web_code);
                    builder.setMessage("当前版本CODE：\n" + String.valueOf(version_code) + "\n最新版本CODE：\n" + String.valueOf(version_web_code) + "\n\n你的“奶糖桌面”需要更新，请到酷安、博客，或者点击“更新”进行更新。");
                    DiyToast.showToast(context, "你的“奶糖桌面”需要更新，请到酷安、博客，或者点击“更新”进行更新。", true);
                    builder.setNeutralButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startUpdate(context, activity);
                        }
                    });
                }
            }
        } else {
            mVersion_name = mVersion_name + "_" + String.valueOf(version_web_code);
            builder.setMessage("当前版本CODE：" + "\n" + String.valueOf(version_code) + "\n" + "现有版本CODE：" + "\n" + version_web_code + "\n" + "\n你已经是最新版本了");
            DiyToast.showToast(context, "你已经是最新版本了", true);
            builder.setNeutralButton("重新下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startUpdate(context, activity);
                }
            });
        }
    }


    private static void web_html(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("在浏览器内输入：\n https://blog.nyanon.online/nt_launcher");
        builder.setPositiveButton("关闭", null);
        builder.show();
    }

    protected static File downLoadFile(String httpUrl, Context context) {
        // TODO Auto-generated method stub
        final String fileName = "updata.apk";
        File tmpFile = new File("/sdcard/");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/" + fileName);
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    DiyToast.showToast(context, "连接超时", true);
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (
                MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return file;
    }

    //打开APK程序代码
    private static void openFile(File file, Context context) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private static void startUpdate(Context context, Activity activity) {
        SavePermission.check_save_permission(activity);
        mIsCancel = false;
//              展示对话框
        mContext = context;
        showDownloadDialog(context);
    }


    /*
     * 显示正在下载对话框
     */
    protected static void showDownloadDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("下载中，请勿退出");
        builder.setMessage("下载目录为：根目录/ntlauncher,\n下载完成后会自动打开文件管理器，请到指定目录进行安装，\n如果本软件无法唤起设定的文件管理器，请您回到桌面手动打开文件管理器进行安装。" +
                "\n如果无法下载，请访问博客或酷安进行下载");
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 下载文件
        downloadAPK(context);
    }

    /*
     * 开启新线程下载apk文件
     */
    private static void downloadAPK(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        Log.e("更新下载目录", sdPath);
//                      文件保存路径
                        mSavePath = sdPath + "ntlauncher";
                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        String str = BuildConfig.VERSION_NAME;
                        String url = "";
                        if (str.indexOf("beta") != -1) {
                            Log.e("version", "测试版");
                            //  内测版请求链接
                            url = "https://yp.nyanon.online/data/User/admin/home/NaiYouApks/Launcher/app-release.apk";
                        } else {
                            Log.e("version", "稳定版");
                            //  稳定版请求链接
                            url = "https://yp.nyanon.online/data/User/admin/home/NaiYouApks/Launcher/app-release.apk";
                        }
                        // 下载文件
                        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();
                        File apkFile = new File(mSavePath, mVersion_name + ".apk");
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        byte[] buffer = new byte[5120];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);
                            // 下载完成
                            if (numread < 0) {
                                try {
                                    openFile(apkFile, context);
                                } catch (Exception e) {
                                    mUpdateProgressHandler.sendEmptyMessage(2);
                                }
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private static Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    installAPK(mContext);
            }
        }

        ;
    };


    /*
     * 下载到本地后执行安装
     */
    protected static void installAPK(final Context context) {
        DiyToast.showToast(context, "下载完成，请打开“ntlauncher”目录找到安装包进行安装", true);
        String s_clean = Build.BRAND;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (s_clean.equals("Allwinner")) {
            builder.setTitle("你的设备是：\n 多看电纸书");
            builder.setMessage("由于 多看电纸书权限申请限制 和 Android 7 安全限制，无法自动安装，点击确定后自动跳转到文件管理，请到“ntlauncher”目录进行安装更新");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    ComponentName cn = new ComponentName("com.softwinner.explore", "com.softwinner.explore.Main");
                    intent.setComponent(cn);
                    context.startActivity(intent);
                }
            });
            builder.setNeutralButton("稍后安装", null);
        } else {
            builder.setTitle("你的设备是：\n暂未适配");
            builder.setMessage("由于 Android 7 及以上版本新增权限限制，而且电纸书设备申请权限有BUG，所以请点击确定后请手动打开系统自带的文件管理，到“ntlauncher”目录进行安装更新");
            builder.setNeutralButton("确定", null);
        }
        builder.show();
    }

}