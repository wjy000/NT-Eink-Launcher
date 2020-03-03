package com.etang.nt_eink_launcher.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.app.NotificationCompat;

import com.etang.nt_eink_launcher.locked.FakerLockedActivity;
import com.etang.nt_eink_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_eink_launcher.launcher.settings.SettingActivity;
import com.etang.nt_eink_launcher.launcher.settings.uninstallapk.UnInstallActivity;
import com.etang.nt_eink_launcher.launcher.settings.wather.WatherActivity;
import com.etang.nt_eink_launcher.tool.toast.DiyToast;
import com.etang.nt_eink_launcher.util.AppInfo;
import com.etang.nt_eink_launcher.util.DeskTopGridViewBaseAdapter;
import com.etang.nt_eink_launcher.util.GetApps;
import com.etang.nt_eink_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_eink_launcher.util.StreamTool;
import com.etang.nt_launcher.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 桌面主页
 * @package_name com.example.dklauncherdemo
 * @project_name DKLauncherDemo
 * @file_name MainActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class MainActivity extends Activity implements OnClickListener {
    private BroadcastReceiver batteryLevelRcvr;
    private IntentFilter batteryLevelFilter;
    private Handler handler;
    private Runnable runnable;
    private TextView tv_user_id, tv_time_hour, tv_time_min,
            tv_main_batterystate, tv_city, tv_wind, tv_temp_state,
            tv_last_updatetime;
    private MyDataBaseHelper dbHelper_name_sql;
    private SQLiteDatabase db;
    private Calendar calendar = Calendar.getInstance();
    private ImageView iv_setting_button, iv_setting_yinliang, iv_setting_lock;
    public static ToggleButton tg_apps_state;
    public static LinearLayout line_wather;
    public static String string_app_info = "";
    public static ImageView iv_index_back;
    public static GridView mListView;
    public static List<AppInfo> appInfos = new ArrayList<AppInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_main);
        //绑定各类
        initView();// 绑定控件
        new_time_Thread();// 启用更新时间进程
        rember_name();// 记住昵称
        initAppList(this);// 获取应用列表
        monitorBatteryState();// 监听电池信息
        mListView.setNumColumns(GridView.AUTO_FIT);
        /**
         * 更新天气信息
         */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        update_wathers(sharedPreferences);
        /**
         * 判断是不是第一次使用
         */
        if (isFirstStart(MainActivity.this)) {//第一次
            /**
             * 填充预设数据
             */
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("images_info", "ql");
            editor.putString("images_app_listifo", "true");
            editor.putString("appname_state", "nope");
            editor.putString("applist_number", "5");
            editor.apply();
            //更新桌面信息
            images_upgrade();
            //填充预设隐藏应用包名
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("com.android.settings");
            arrayList.add("com.android.mgs.pinyin");
            arrayList.add("com.duokan.einkreader");
            arrayList.add("com.mgs.factorytest");
            arrayList.add("com.softwinner.explore");
            SaveArrayListUtil.saveArrayList(MainActivity.this, arrayList, "start");//存储在本地

        }
        get_applist_number();//获取设定的应用列表列数
        images_upgrade();//更新图像信息
        // 长按弹出APP信息
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                string_app_info = appInfos.get(position).getPackageName();
                Intent intent = new Intent(MainActivity.this, UnInstallActivity.class);
                startActivity(intent);
                Log.e("CHOSE ID", String.valueOf(id));
                return true;
            }
        });
        // 当点击GridView时，获取ID和应用包名并启动
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Intent intent=appInfos.get(position).getIntent();
                // startActivity(intent);
                Intent intent = getPackageManager().getLaunchIntentForPackage(
                        appInfos.get(position).getPackageName());
                if (intent != null) {
                    intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                Log.e("CHOSE ID", String.valueOf(id));
            }
        });
        // 默认抽屉显示状态
        mListView.setVisibility(View.GONE);
        iv_index_back.setVisibility(View.VISIBLE);
        //切换应用列表
        tg_apps_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    mListView.setVisibility(View.VISIBLE);
                    iv_index_back.setVisibility(View.GONE);
                    initAppList(MainActivity.this);
                    get_applist_number();
                } else {
                    mListView.setVisibility(View.GONE);
                    iv_index_back.setVisibility(View.VISIBLE);
                    initAppList(MainActivity.this);
                    get_applist_number();
                }
            }
        });
        //长按弹出菜单选择城市
        line_wather.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getApplicationContext(),
                        WatherActivity.class));
                return true;
            }
        });
        //长按弹出昵称设置
        tv_user_id.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                show_name_dialog();
                return true;
            }
        });
        /**
         * 开启常驻通知
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setNotification();
            }
        }, 2000);
    }

    private void get_applist_number() {
        try {
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            String applist_number = sharedPreferences.getString("applist_number", null);
            Log.e("APPLIST_NUMBER", applist_number);
            if (applist_number.equals("auto")) {
                mListView.setNumColumns(GridView.AUTO_FIT);
            }
            if (!applist_number.equals("auto")) {
                mListView.setNumColumns(Integer.valueOf(applist_number));
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("applist_number", "auto");
            editor.apply();
            mListView.setNumColumns(GridView.AUTO_FIT);
        }
    }

    public boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查图片
     */
    private void images_upgrade() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String images_mode = sharedPreferences.getString("images_info", null);
        if (images_mode.equals("ql")) {
            iv_index_back.setImageResource(R.drawable.mi_haole);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("ej")) {
            iv_index_back.setImageResource(R.drawable.mi_erji);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("mz")) {
            iv_index_back.setImageResource(R.drawable.mi_meizi);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("ch")) {
            iv_index_back.setImageResource(R.drawable.mi_chahua);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("ll")) {
            iv_index_back.setImageResource(R.drawable.mi_luoli);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("yl")) {
            iv_index_back.setImageResource(R.drawable.mi_yali);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("pb")) {
            iv_index_back.setImageResource(R.drawable.mi_pinbo);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("zy")) {
            iv_index_back.setImageResource(R.drawable.mi_zhiyu);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
        if (images_mode.equals("applist")) {
            iv_index_back.setImageResource(R.drawable.mi_haole);
            iv_index_back.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
        }
        if (images_mode.equals("")) {
            iv_index_back.setImageResource(R.drawable.mi_haole);
            iv_index_back.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            DiyToast.showToast(this, "请选择壁纸或者应用列表（设置-壁纸设置）");
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Cursor cursor = db.rawQuery("select * from wather_city", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            update_wather(MainActivity.this,
                    cursor.getString(cursor.getColumnIndex("city")));
        }
        /**
         * 更新天气信息
         */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        update_wathers(sharedPreferences);
        initAppList(MainActivity.this);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Cursor cursor = db.rawQuery("select * from wather_city", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            update_wather(MainActivity.this,
                    cursor.getString(cursor.getColumnIndex("city")));
        }
        /**
         * 更新天气信息
         */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        update_wathers(sharedPreferences);
        initAppList(MainActivity.this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Cursor cursor = db.rawQuery("select * from wather_city", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            update_wather(MainActivity.this,
                    cursor.getString(cursor.getColumnIndex("city")));
        }
        /**
         * 更新天气信息
         */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        update_wathers(sharedPreferences);
        initAppList(MainActivity.this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Cursor cursor = db.rawQuery("select * from wather_city", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            update_wather(MainActivity.this,
                    cursor.getString(cursor.getColumnIndex("city")));
        }
        /**
         * 更新天气信息
         */
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        update_wathers(sharedPreferences);
        initAppList(MainActivity.this);
    }

    /**
     * +获取应用列表、隐藏应用
     *
     * @param context
     */
    public static void initAppList(Context context) {
        try {
            appInfos = GetApps.GetAppList1(context);
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.clear();
            arrayList = SaveArrayListUtil.getSearchArrayList(context);
            if (Build.BRAND.equals("Allwinner")) {
//                arrayList.add("com.android.mgs.pinyin");
//                arrayList.add("com.duokan.einkreader");
//                arrayList.add("com.mgs.factorytest");
//                arrayList.add("com.softwinner.explore");
            }
            Log.e("DATA_OJBK", arrayList.toString());
            for (int j = 0; j < arrayList.size(); j++) {
                for (int i = 0; i < appInfos.size(); i++) {
                    if (arrayList.get(j).equals(appInfos.get(i).getPackageName())) {
                        Log.e("APPDATA-------", appInfos.get(i).getPackageName());
                        appInfos.remove(i);
                        continue;
                    }
                }
            }
            DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(appInfos,
                    context);
            mListView.setAdapter(deskTopGridViewBaseAdapter);
        } catch (Exception e) {
            /**
             * 填充预设数据
             */
            SharedPreferences.Editor editor = context.getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("app_hind_info", "");
            editor.apply();
        }
    }

    /**
     * 读取昵称
     * <p>
     * SQLite
     */
    private void rember_name() {
        Cursor cursor = db.rawQuery("select * from name", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            tv_user_id.setText(cursor.getString(cursor
                    .getColumnIndex("username")));
            if (tv_user_id.getText().toString().isEmpty()) {
                tv_user_id.setText("请设置昵称（长按此处）");
            }
        }
    }

    /**
     * 更新时间
     */
    private void new_time_Thread() {
        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                SimpleDateFormat simpleDateFormat_hour = new SimpleDateFormat(
                        "HH");
                SimpleDateFormat simpleDateFormat_min = new SimpleDateFormat(
                        "mm");
                tv_time_hour.setText(simpleDateFormat_hour
                        .format(new java.util.Date()));
                tv_time_min.setText(simpleDateFormat_min
                        .format(new java.util.Date()));
                handler.postDelayed(runnable, 1000);
            }
        };
        handler.post(runnable);
    }

    /**
     * 绑定控件
     */
    private void initView() {
        mListView = (GridView) findViewById(R.id.mListView);
        iv_setting_button = (ImageView) findViewById(R.id.iv_setting_button);
        tv_time_hour = (TextView) findViewById(R.id.tv_time_hour);
        tg_apps_state = (ToggleButton) findViewById(R.id.tg_apps_state);
        tv_time_min = (TextView) findViewById(R.id.tv_time_min);
        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        tv_main_batterystate = (TextView) findViewById(R.id.tv_main_batterystate);
        line_wather = (LinearLayout) findViewById(R.id.line_wather);
        iv_setting_lock = (ImageView) findViewById(R.id.iv_setting_lock);
        tv_city = (TextView) findViewById(R.id.tv_city);
        iv_setting_yinliang = (ImageView) findViewById(R.id.iv_setting_yinliang);
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        iv_index_back = (ImageView) findViewById(R.id.iv_index_back);
        tv_temp_state = (TextView) findViewById(R.id.tv_temp_state);
        tv_last_updatetime = (TextView) findViewById(R.id.tv_last_updatetime);
        iv_setting_button.setOnClickListener(this);
        line_wather.setOnClickListener(this);
        iv_setting_yinliang.setOnClickListener(this);
        iv_setting_lock.setOnClickListener(this);
        dbHelper_name_sql = new MyDataBaseHelper(getApplicationContext(), "info.db",
                null, 2);
        db = dbHelper_name_sql.getWritableDatabase();
    }

    private void update_wathers(SharedPreferences sharedPreferences) {
        tv_wind.setText(sharedPreferences.getString("wather_info_wind", null));
        tv_temp_state.setText(sharedPreferences.getString("wather_info_temp", null));
        tv_last_updatetime.setText(sharedPreferences.getString("wather_info_updatetime", null));
        /**
         * 判断设置是不是隐藏天气布局
         */
        check_weather_view(sharedPreferences);
    }

    private void check_weather_view(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean("isHind_weather", false) == true) {
            Log.e("TAG", "1111");
            line_wather.setVisibility(View.INVISIBLE);
        } else if (sharedPreferences.getBoolean("isHind_weather", false) == false) {
            Log.e("TAG", "2222");
            line_wather.setVisibility(View.VISIBLE);
        } else {
            Log.e("TAG", "3333");
            line_wather.setVisibility(View.VISIBLE);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String fengxiang = "";
                    String fengli = "";
                    String high = "";
                    String type = "";
                    String low = "";
                    String date = "";
                    JSONArray dataArray = (JSONArray) msg.obj;
                    try {
                        String json_today = dataArray.getString(0);
                        JSONObject jsonObject = dataArray.getJSONObject(0);
                        System.out.println(jsonObject);
                        if (jsonObject != null) {
                            fengxiang = jsonObject.optString("fengxiang");
                            fengli = jsonObject.optString("fengli");
                            high = jsonObject.optString("high");
                            type = jsonObject.optString("type");
                            low = jsonObject.optString("low");
                            date = jsonObject.optString("date");
                        }
                        Cursor cursor = db.rawQuery("select * from wather_city",
                                null);
                        if (cursor.getCount() != 0) {
                            cursor.moveToFirst();
                            tv_city.setText(cursor.getString(cursor
                                    .getColumnIndex("city")) + "  " + type);
                        } else {
                            DiyToast.showToast(getApplicationContext(), "请选择天气城市");
                        }
                        SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                        editor.putString("wather_info_wind", fengxiang);
                        editor.putString("wather_info_temp", high + "  " + low);
                        editor.putString("wather_info_updatetime", "于"
                                + tv_time_hour.getText().toString() + ":"
                                + tv_time_min.getText().toString() + "更新");
                        editor.apply();

                        Log.e("TAG", "更新成功");
                        /**
                         * 更新天气信息
                         */
                        SharedPreferences sharedPreferences;
                        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                        update_wathers(sharedPreferences);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    public void update_wather(Context context, final String city) {
        if (TextUtils.isEmpty(city)) {
            DiyToast.showToast(context, "城市错误，不在数据库中");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                Log.e("weather", city);
                try {
                    URL url = new URL(
                            "http://wthrcdn.etouch.cn/weather_mini?city="
                                    + URLEncoder.encode(city, "UTF-8"));
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        Log.e("weather", "连接网络成功");
                        // 连接网络成功
                        InputStream in = conn.getInputStream();
                        String data = StreamTool.decodeStream(in);
                        // 解析json格式的数据
                        JSONObject jsonObj = new JSONObject(data);
                        // 获得desc的值
                        String result = jsonObj.getString("desc");
                        if ("OK".equals(result)) {
                            Log.e("weather", "城市有效");
                            // 城市有效，返回了需要的数据
                            JSONObject dataObj = jsonObj.getJSONObject("data");
                            JSONArray jsonArray = dataObj
                                    .getJSONArray("forecast");
                            // 通知更新ui
                            Message msg = Message.obtain();
                            msg.obj = jsonArray;
                            msg.what = 0;
                            mHandler.sendMessage(msg);
                        } else {
                            Log.e("weather", "城市无效");
                            // 城市无效
                            Message msg = Message.obtain();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("weather", "异常：" + e.toString());
                    Message msg = Message.obtain();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }
            }

            ;
        }.start();
    }

    /**
     * 拦截返回键、Home键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

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
                        sb.append(String.valueOf(level) + "% " + " 充电完成 ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_CHARGING) {//充电
                        sb.append(String.valueOf(level) + "% " + " 正在充电 ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {//放电
                        sb.append(String.valueOf(level) + "% " + " 使用中 ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {//未在充电
                        sb.append(String.valueOf(level) + "% " + " 使用中 ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                }
                sb.append(' ');
            }
        };
        batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }

    public void show_name_dialog() {
        final AlertDialog builder = new AlertDialog.Builder(
                MainActivity.this).create();
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.dialog_name_show, null, false);
        builder.setView(view);
        Window window = builder.getWindow();
        builder.getWindow();
        window.setGravity(Gravity.CENTER); // 底部位置
        window.setContentView(view);
        final EditText et_name_get = (EditText) view
                .findViewById(R.id.et_title_name);
        final RadioButton ra_0 = (RadioButton) view
                .findViewById(R.id.radio0);
        final RadioButton ra_1 = (RadioButton) view
                .findViewById(R.id.radio1);
        final RadioButton ra_2 = (RadioButton) view
                .findViewById(R.id.radio2);
        final RadioButton ra_3 = (RadioButton) view
                .findViewById(R.id.radio3);
        final Button btn_con = (Button) view.findViewById(R.id.btn_dialog_rename_con);
        final Button btn_cls = (Button) view.findViewById(R.id.btn_dialog_rename_cls);
        builder.setTitle("请输入你的要显示的内容");
        btn_cls.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btn_con.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name_get.getText().toString().isEmpty()
                        && !ra_0.isChecked() && !ra_2.isChecked()
                        && !ra_3.isChecked() && !ra_1.isChecked()) {
                    db.execSQL("update name set username = ?",
                            new String[]{""});
                } else {
                    if (ra_0.isChecked() || ra_1.isChecked()
                            || ra_2.isChecked() || ra_3.isChecked()) {
                        if (ra_0.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_0.getText()
                                            .toString() + "多看电纸书"});
                        }
                        if (ra_1.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_1.getText()
                                            .toString() + "多看电纸书"});
                        }
                        if (ra_2.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_2.getText()
                                            .toString() + "多看电纸书"});
                        }
                        if (ra_3.isChecked()) {
                            db.execSQL(
                                    "update name set username = ?",
                                    new String[]{ra_3.getText()
                                            .toString() + "多看电纸书"});
                        }
                    } else {
                        db.execSQL("update name set username = ?",
                                new String[]{et_name_get
                                        .getText().toString()});
                    }
                }
                builder.dismiss();
                rember_name();
            }
        });
        builder.show();
    }


    /**
     * 桌面左下角设置 点击事件监听
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_setting_button:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.line_wather:
                Cursor cursor = db.rawQuery("select * from wather_city", null);
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    update_wather(MainActivity.this,
                            cursor.getString(cursor.getColumnIndex("city")));
                    /**
                     * 更新天气信息
                     */
                    SharedPreferences sharedPreferences;
                    sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    update_wathers(sharedPreferences);
                }
                break;
            case R.id.iv_setting_yinliang:
                //解决华为，魅族等等手机扩音播放失败的bug
                try {
                    String keyCommand = "input keyevent " + KeyEvent.KEYCODE_VOLUME_UP;
                    Runtime runtime = Runtime.getRuntime();
                    Process proc = runtime.exec(keyCommand);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.iv_setting_lock:
                startActivity(new Intent(MainActivity.this, FakerLockedActivity.class));
                finish();//这一次要结束当前Activity，但是要确保用户返回的时候不会出错（屏蔽返回按键）
                break;
            default:
                break;
        }
    }

    // 添加常驻通知
    private void setNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        int channelId = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    //Android 8.0以上适配
            NotificationChannel channel = new NotificationChannel(String.valueOf(channelId), "channel_name",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, String.valueOf(channelId));
        } else {
            builder = new NotificationCompat.Builder(this);
        }
//        Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent();// 创建Intent对象
        intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
        intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
//        startActivity(intent);// 将Intent传递给Activity
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentTitle("点击回到桌面")//指定通知栏的标题内容
                .setContentText("后台运行中")//通知的正文内容
                .setWhen(0)//通知创建的时间
                .setAutoCancel(false)//点击通知后，自动取消
                .setStyle(new NotificationCompat.BigTextStyle().bigText(""))
                .setSmallIcon(R.drawable.title_back_alpha)//通知显示的小图标，只能用alpha图层的图片进行设置
                .setPriority(NotificationCompat.PRIORITY_MAX)//通知重要程度
                .setContentIntent(pi)//点击跳转
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        manager.notify(channelId, notification);
    }
}