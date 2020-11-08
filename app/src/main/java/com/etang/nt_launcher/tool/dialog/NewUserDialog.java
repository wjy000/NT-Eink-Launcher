package com.etang.nt_launcher.tool.dialog;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.toast.DiyToast;

import java.lang.reflect.Field;
import java.security.MessageDigest;

public class NewUserDialog {
    private static String SKEY = "SCU66788Tac2bf7385575174e067c917d471e25365dd3983cec5ee";
    private static String web_index = "sc.ftqq.com";


    public static void dialog_show(Context context, String info) {
        test(context, info);
    }

    private static void test(Context context, String info) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newuser, null, false);
        WebView wv = (WebView) view.findViewById(R.id.webview_newuser);
        wv.loadUrl("https://" + web_index + "/" + SKEY
                + ".send?text=奶糖桌面-NML" + "---" + info + "&desp="
                + "设备详情信息：\n" + getDeviceInfo());
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取指定字段信息
     *
     * @return
     */
    private static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("---主板：" + Build.BOARD);
        sb.append("\n---系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("\n---系统定制商：" + Build.BRAND);
        sb.append("\n---cpu指令集：" + Build.CPU_ABI);
        sb.append("\n---cpu指令集2：" + Build.CPU_ABI2);
        sb.append("\n---设置参数：" + Build.DEVICE);
        sb.append("\n---显示屏参数：" + Build.DISPLAY);
        sb.append("\n---无线电固件版本：" + Build.getRadioVersion());
        sb.append("\n---硬件识别码：" + Build.FINGERPRINT);
        sb.append("\n---硬件名称：" + Build.HARDWARE);
        sb.append("\n---HOST:" + Build.HOST);
        sb.append("\n---修订版本列表：" + Build.ID);
        sb.append("\n---硬件制造商：" + Build.MANUFACTURER);
        sb.append("\n---版本：" + Build.MODEL);
        sb.append("\n---硬件序列号：" + Build.SERIAL);
        sb.append("\n---手机制造商：" + Build.PRODUCT);
        sb.append("\n---描述Build的标签：" + Build.TAGS);
        sb.append("\n---TIME:" + Build.TIME);
        sb.append("\n---builder类型：" + Build.TYPE);
        sb.append("\n---USER:" + Build.USER);
        return sb.toString();
    }

    /**
     * 通过反射获取所有的字段信息
     *
     * @return
     */
    public String getDeviceInfo2() {
        StringBuilder sbBuilder = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sbBuilder.append("\n" + field.getName() + ":" + field.get(null).toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sbBuilder.toString();
    }
}
