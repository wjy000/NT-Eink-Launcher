package com.etang.nt_launcher.locked;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;

import java.text.SimpleDateFormat;

public class FakerLockedActivity extends AppCompatActivity {
    private ImageView iv_lock_back, iv_lock_back_logo, iv_lock_setting_logo, iv_lock_setting;
    private TextView tv_time_lock, iv_lock_rundate_text, tv_one_text;
    private BroadcastReceiver batteryLevelRcvr;
    private IntentFilter batteryLevelFilter;
//    String string = "";
//    String Hitokoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_faker_locked);
        initView();//绑定控件
//        countTime_ontText();//开始计时
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
        iv_lock_setting_logo.setVisibility(View.GONE);
        iv_lock_setting.setVisibility(View.GONE);
        tv_one_text.setVisibility(View.GONE);
//        iv_lock_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isOpenNetwork()) {
//                    GET("https://v1.hitokoto.cn/");
//                    CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            tv_one_text.setText(Hitokoto);
//                        }
//                    }.start();
//                } else {
//                    open_wifi();
//                }
//            }
//        });

//        iv_lock_setting_logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendKeyCode1(223);
//                if (isOpenNetwork()) {
//                    GET("https://v1.hitokoto.cn/");
//                    CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            tv_one_text.setText(Hitokoto);
//                        }
//                    }.start();
//                } else {
//                    open_wifi();
//                }
//            }
//        });
        //更新进程
        handler.post(runnable);
    }

    private void countTime_ontText() {
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                GET("https://v1.hitokoto.cn/");
                countTime_ontText();
            }
        }.start();
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
        tv_one_text = (TextView) findViewById(R.id.tv_one_text);
//        GET("https://v1.hitokoto.cn/");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮",true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            //菜单
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮",true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            // 由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮",true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_POWER) {
            DiyToast.showToast(getApplicationContext(), "锁屏状态无法通过按键返回，请手动点击左上角关闭按钮",true);
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

//    private void open_wifi() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(FakerLockedActivity.this);
//        builder.setTitle("没有可用的网络").setMessage("请打开WIFI或数据流量");
//        builder.setPositiveButton("确定", null);
//        builder.show();
//        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = null;
//                try {
//                    String sdkVersion = android.os.Build.VERSION.SDK;
//                    if (Integer.valueOf(sdkVersion) > 10) {
//                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//                    } else {
//                        intent = new Intent();
//                        ComponentName comp2 = new ComponentName("com.android.settings", Settings.ACTION_WIFI_SETTINGS);
//
//                        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
//                        intent.setComponent(comp2);
//                        intent.setAction("android.intent.action.VIEW");
//                    }
//                    FakerLockedActivity.this.startActivity(intent);
//                } catch (Exception e) {
//                    Log.e("TAG", "open network settings failed, please check...");
//                    e.printStackTrace();
//                }
//            }
//        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                finish();
//            }
//        }).show();
//    }
//
//    /**
//     * 对网络连接状态进行判断
//     *
//     * @return true, 可用； false， 不可用
//     */
//    private boolean isOpenNetwork() {
//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connManager.getActiveNetworkInfo() != null) {
//            return connManager.getActiveNetworkInfo().isAvailable();
//        }
//        return false;
//    }
//
//    /**
//     * okhttp封装
//     *
//     * @param url
//     */
//    public void GET(String url) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        final Call call = okHttpClient.newCall(request);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    string = response.body().string();
//                    getJson(string);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_one_text.setText(Hitokoto);
//                        }
//                    });
//                    Log.e("GET", string);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("ERROE", e.toString());
//                    if (e.toString().equals("java.net.UnknownHostException: Unable to resolve host \"v1.hitokoto.cn\": No address associated with hostname")) {
//                        Log.e("错误", "无网络");
//                        DiyToast.showToast(FakerLockedActivity.this, "无网络");
//                    }
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * 获取返回的Json内容
//     *
//     * @param res
//     * @return
//     */
//    public JsonService getJson(String res) {
//        Gson gson = new Gson();
//        JsonService json = gson.fromJson(res, JsonService.class);
//        Hitokoto = json.getHitokoto();
//        Log.e("getHitokoto", json.getHitokoto());
//        Log.e("getId", String.valueOf(json.getId()));
//        Log.e("getFrom", json.getFrom());
//        Log.e("getFrom_who", String.valueOf(json.getFrom_who()));
//        return json;
//    }
}
