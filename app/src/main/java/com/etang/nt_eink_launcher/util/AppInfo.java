package com.etang.nt_eink_launcher.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 获取APP信息JavaBean
 */
public class AppInfo {
    private String packageName;
    private Drawable ico;
    private String Name;
    private Intent intent;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIco() {
        return ico;
    }

    public void setIco(Drawable ico) {
        this.ico = ico;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
