package com.etang.nt_launcher.tool.dialog;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.toast.DiyToast;

import java.lang.reflect.Field;
import java.security.MessageDigest;

/**
 * 用于给服务器发送新用户激活信息、设备信息的弹出框（无View）
 */
public class NewUserDialog {
    private static String SKEY = "SCU66788Tac2bf7385575174e067c917d471e25365dd3983cec5ee";
    private static String web_index = "sc.ftqq.com";


    public static void dialog_show(Context context, String info, boolean newuser_or_debug) {
        if (newuser_or_debug) {
            test(context, info, newuser_or_debug);
        } else {
            test(context, info, newuser_or_debug);
        }
    }

    private static void test(Context context, String info, boolean newuser_or_debug) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newuser, null, false);
        WebView wv = (WebView) view.findViewById(R.id.webview_newuser);
        StringBuffer stringBuffer = new StringBuffer();
        //第一段
        stringBuffer.append("https://" + web_index + "/" + SKEY + ".send?text=梅糖桌面-MTL" + "---");
        //第二段
        stringBuffer.append("&desp=");
        //判断激活的属性
        if (newuser_or_debug) {
            stringBuffer.append(info);
            stringBuffer.append("设备激活");
            stringBuffer.append("%0D%0A%0D%0A");
            stringBuffer.append("设备信息：");
            stringBuffer.append("%0D%0A%0D%0A");
            stringBuffer.append(getDeviceInfo());
        } else {
            stringBuffer.append("设备报错");
            stringBuffer.append("%0D%0A%0D%0A");
            stringBuffer.append("报错信息：");
            stringBuffer.append(info);
            stringBuffer.append("%0D%0A%0D%0A");
            stringBuffer.append("设备信息：");
            stringBuffer.append("%0D%0A%0D%0A");
            stringBuffer.append(getDeviceInfo());
        }
        wv.loadUrl(stringBuffer.toString());
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
        sb.append("%0D%0A%0D%0A");
        sb.append("---系统启动程序版本号：" + Build.BOOTLOADER);
        sb.append("%0D%0A%0D%0A");
        sb.append("---系统定制商：" + Build.BRAND);
        sb.append("%0D%0A%0D%0A");
        sb.append("---cpu指令集：" + Build.CPU_ABI);
        sb.append("%0D%0A%0D%0A");
        sb.append("---cpu指令集2：" + Build.CPU_ABI2);
        sb.append("%0D%0A%0D%0A");
        sb.append("---设置参数：" + Build.DEVICE);
        sb.append("%0D%0A%0D%0A");
        sb.append("---显示屏参数：" + Build.DISPLAY);
        sb.append("%0D%0A%0D%0A");
        sb.append("---无线电固件版本：" + Build.getRadioVersion());
        sb.append("%0D%0A%0D%0A");
        sb.append("---硬件识别码：" + Build.FINGERPRINT);
        sb.append("%0D%0A%0D%0A");
        sb.append("---硬件名称：" + Build.HARDWARE);
        sb.append("%0D%0A%0D%0A");
        sb.append("---HOST:" + Build.HOST);
        sb.append("%0D%0A%0D%0A");
        sb.append("---修订版本列表：" + Build.ID);
        sb.append("%0D%0A%0D%0A");
        sb.append("---硬件制造商：" + Build.MANUFACTURER);
        sb.append("%0D%0A%0D%0A");
        sb.append("---版本：" + Build.MODEL);
        sb.append("%0D%0A%0D%0A");
        sb.append("---硬件序列号：" + Build.SERIAL);
        sb.append("%0D%0A%0D%0A");
        sb.append("---手机制造商：" + Build.PRODUCT);
        sb.append("%0D%0A%0D%0A");
        sb.append("---描述Build的标签：" + Build.TAGS);
        sb.append("%0D%0A%0D%0A");
        sb.append("---TIME:" + Build.TIME);
        sb.append("%0D%0A%0D%0A");
        sb.append("---builder类型：" + Build.TYPE);
        sb.append("%0D%0A%0D%0A");
        sb.append("---USER:" + Build.USER);
        sb.append("%0D%0A%0D%0A");
        return sb.toString();
    }

    /**
     * 通过反射获取所有的字段信息
     *
     * @return
     */
    public static String getDeviceInfo2() {
        StringBuilder sbBuilder = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sbBuilder.append("\n" + field.getName() + ":" + field.get(null).toString() + "%0D%0A%0D%0A");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sbBuilder.toString();
    }
}
