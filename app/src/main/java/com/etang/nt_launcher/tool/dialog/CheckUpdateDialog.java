package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.etang.nt_launcher.BuildConfig;
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


public class CheckUpdateDialog {
    public static void check_update(final Context context, final ProgressDialog progressDialog) {
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
                        DeBugDialog.debug_show_dialog(context, error_message);
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
                        version_update(context, new_version_message);
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
                    Document doc = Jsoup.connect(" https://naiyouhuameitang.club/index.php/24.html").get();
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

    private static void version_update(final Context context, String s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //截取之后的字符
        String version_web = s.substring(0, s.indexOf("(" + context.getPackageName() + ")"));
        String version = BuildConfig.VERSION_NAME;
        if (!version.equals(version_web)) {
            builder.setMessage("当前版本：" + "\n" + version + "\n" + "现有版本：" + "\n" + version_web + "\n" + "有更新，请到“酷安”进行更新，或登录博客在电脑端或者浏览器内下载后自行安装，软件内更新功能即将上线");
            DiyToast.showToast(context, "有更新，请到“酷安”进行更新，或登录博客在电脑端或者浏览器内下载后自行安装，软件内更新功能即将上线", true);
//            builder.setNeutralButton("下载", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    downLoadFile("http://naiyouhuameitang.club/apk/Launcher_project/NaiTang/update.apk", context);
//                }
//            });
        } else {
            builder.setMessage("当前版本：" + "\n" + version + "\n" + "现有版本：" + "\n" + version_web + "\n" + "你已经是最新版本了");
            DiyToast.showToast(context, "你已经是最新版本了", true);
        }
        builder.setPositiveButton("博客地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DiyToast.showToast(context, "https://naiyouhuameitang.club/nt_launcher.html", true);
                web_html(context);
            }
        });
        builder.show();
    }

    private static void web_html(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("在浏览器内输入：\n https://naiyouhuameitang.club/nt_launcher.html");
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
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return file;
    }
//打开APK程序代码

    private void openFile(File file, Context context) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}