package com.etang.nt_eink_launcher.locked;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_eink_launcher.MainActivity;
import com.etang.nt_eink_launcher.receive.ScreenListener;
import com.etang.nt_eink_launcher.toast.DiyToast;
import com.etang.nt_launcher.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FakerLockedActivity extends AppCompatActivity {
    private ImageView iv_lock_back, iv_lock_back_logo, iv_lock_setting_logo, iv_lock_setting;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
    private TextView tv_time_lock, iv_lock_rundate_text;
    private ScreenListener screenListener;
    private BroadcastReceiver batteryLevelRcvr;
    private IntentFilter batteryLevelFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_faker_locked);
        initView();
        monitorBatteryState();//电量监听
//        iv_lock_rundate_text.setText(RunTimeDate.getUsedPercentValue(FakerLockedActivity.this));
        iv_lock_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }
        });
        iv_lock_back_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }
        });
        iv_lock_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKeyCode1(223);
            }
        });

        iv_lock_setting_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKeyCode1(223);
            }
        });
        screenListener = new ScreenListener(FakerLockedActivity.this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
//                Toast.makeText(FakerLockedActivity.this, "屏幕打开了", Toast.LENGTH_SHORT).show();
//                Log.e("LOCK", "屏幕打开了");
            }

            @Override
            public void onScreenOff() {
//                Toast.makeText(FakerLockedActivity.this, "屏幕关闭了", Toast.LENGTH_SHORT).show();
//                Log.e("LOCK", "屏幕关闭了");
            }

            @Override
            public void onUserPresent() {
//                Toast.makeText(FakerLockedActivity.this, "解锁了", Toast.LENGTH_SHORT).show();
//                Log.e("LOCK", "解锁了");
            }
        });
        //更新进程
        handler.post(runnable);
    }

    private void sendKeyCode1(int keyCode) {
        try {
            String keyCommand = "input keyevent " + keyCode;
            // 调用Runtime模拟按键操作
            Runtime.getRuntime().exec(keyCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    // 设置为横屏模式  
//    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//    // 设置为竖屏模式  
//    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//    // 设置为跟随系统sensor的状态 
//    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    private void initView() {
        iv_lock_back = (ImageView) findViewById(R.id.iv_lock_back);
        iv_lock_back_logo = (ImageView) findViewById(R.id.iv_lock_back_logo);
        iv_lock_setting = (ImageView) findViewById(R.id.iv_lock_setting);
        iv_lock_setting_logo = (ImageView) findViewById(R.id.iv_lock_setting_logo);
        tv_time_lock = (TextView) findViewById(R.id.tv_lock_time);
        iv_lock_rundate_text = (TextView) findViewById(R.id.iv_lock_rundate_text);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            //菜单
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            // 由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_POWER) {
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 更新时间
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            SimpleDateFormat simpleDateFormat_hour = new SimpleDateFormat(
                    "HH");
            SimpleDateFormat simpleDateFormat_min = new SimpleDateFormat(
                    "mm");
            SimpleDateFormat simpleDateFormat_year = new SimpleDateFormat(
                    "yyyy年MM月dd日");
            tv_time_lock.setText(simpleDateFormat_hour
                    .format(new java.util.Date()) + ":" + simpleDateFormat_min
                    .format(new java.util.Date()));
            handler.postDelayed(runnable, 1000);
        }
    };
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    };

    /**
     * Activity被销毁的同时销毁广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryLevelRcvr);
    }

    /**
     * 充电状态显示
     * <p>
     * Code Copy from http://blog.sina.com.cn/s/blog_c79c5e3c0102uyun.html
     */
    private void monitorBatteryState() {
        batteryLevelRcvr = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                StringBuilder sb = new StringBuilder();
                int rawlevel = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                int status = intent.getIntExtra("status", -1);
                int health = intent.getIntExtra("health", -1);
                int level = -1; // percentage, or -1 for unknown
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {
                    sb.append("'s battery feels very hot!");
                } else {
                    if (status == BatteryManager.BATTERY_STATUS_FULL) {//充电完成
                        iv_lock_rundate_text.setText(String.valueOf(level));
                    }
                    if (status == BatteryManager.BATTERY_STATUS_CHARGING) {//充电
                        iv_lock_rundate_text.setText(String.valueOf(level));
                    }
                    if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {//放电
                        iv_lock_rundate_text.setText(String.valueOf(level));
                    }
                    if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {//未在充电
                        iv_lock_rundate_text.setText(String.valueOf(level));
                    }
                }
                Log.e("电池", "level");
                sb.append(' ');
            }
        };
        batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }
}
