package com.etang.nt_launcher.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.app.NotificationCompat;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.settings.SettingActivity;
import com.etang.nt_launcher.launcher.settings.instructions.InstructionsActivity;
import com.etang.nt_launcher.launcher.settings.uirefresh.UireFreshActivity;
import com.etang.nt_launcher.launcher.settings.weather.WeatherActivity;
import com.etang.nt_launcher.tool.dialog.CheckUpdateDialog;
import com.etang.nt_launcher.tool.dialog.DeBugDialog;
import com.etang.nt_launcher.tool.dialog.UnInstallDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayListUtil;
import com.etang.nt_launcher.tool.server.AppInstallServer;
import com.etang.nt_launcher.tool.sql.MyDataBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.tool.util.AppInfo;
import com.etang.nt_launcher.tool.util.DeskTopGridViewBaseAdapter;
import com.etang.nt_launcher.tool.util.GetApps;
import com.etang.nt_launcher.tool.util.StreamTool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static MyDataBaseHelper dbHelper_name_sql;
    private static SQLiteDatabase db;
    public static View view_buttom;
    public static TextView tv_user_id, tv_time_hour, tv_time_min,
            tv_main_batterystate, tv_city, tv_wind, tv_temp_state,
            tv_last_updatetime, tv_main_nowdate;
    public static ImageView iv_setting_button, iv_setting_yinliang, iv_setting_refresh, iv_setting_rss, iv_clean_button;
    public static ToggleButton tg_apps_state;
    public static LinearLayout line_wather, line_bottom;
    public static String string_app_info = "";
    public static ImageView iv_index_back;
    public static GridView mListView;
    public static List<AppInfo> appInfos = new ArrayList<AppInfo>();
    public static boolean offline_mode = false;
    private AppInstallServer appinstallserver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        //绑定各类
        initView();// 绑定控件
        check_first_user();//检查是不是第一次使用
        SavePermission.check_save_permission(MainActivity.this, MainActivity.this);//检查存取权限
        new_time_Thread();// 启用更新时间进程
        rember_name(MainActivity.this);// 读取昵称
        initAppList(this);// 获取应用列表
        monitorBatteryState();// 监听电池信息
        check_text_size(MainActivity.this);//检查文本大小
        update_wathers(sharedPreferences);//更新天气
        check_view_hind(MainActivity.this, sharedPreferences);//检查底栏是否隐藏
        check_offline_mode(MainActivity.this, sharedPreferences);//检查离线模式是否打开
        images_upgrade();//更新图像信息
        get_applist_number();//获取设定的应用列表列数
        set_app_setStackFromBottomMode(sharedPreferences);//检查并设置APP列表排列方式
        // 长按弹出APP信息
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                try {
                    string_app_info = appInfos.get(position).getPackageName();
                    UnInstallDialog.uninstall_app(MainActivity.this, MainActivity.this, string_app_info, appInfos.get(position).getName());
                } catch (Exception e) {
                    DeBugDialog.debug_show_dialog(MainActivity.this, e.toString());//显示错误信息
                }
                return true;
            }
        });
        // 当点击GridView时，获取ID和应用包名并启动
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    // Intent intent=appInfos.get(position).getIntent();
                    // startActivity(intent);
                    Intent intent = getPackageManager().getLaunchIntentForPackage(
                            appInfos.get(position).getPackageName());
                    if (intent != null) {//点击的APP无异常
                        intent.putExtra("type", "110");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (appInfos.get(position).getPackageName().equals(getPackageName() + ".weather")) {//点击了“天气”
                        intent = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(intent);
                    } else if (appInfos.get(position).getPackageName().equals(getPackageName() + ".systemupdate")) {//点击了“检查更新”
                        CheckUpdateDialog.check_update(MainActivity.this, MainActivity.this);
                    } else if (appInfos.get(position).getPackageName().equals(getPackageName() + ".launchersetting")) {//点击了“桌面设置”
                        intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                    } else if (appInfos.get(position).getPackageName().equals(getPackageName() + ".uirefresh")) {//点击了“刷新屏幕”
                        String s = Build.BRAND;
                        if (s.equals("Allwinner")) {
                            Intent intent_refresh = new Intent("android.eink.force.refresh");
                            sendBroadcast(intent_refresh);
                        } else {
                            startActivity(new Intent(MainActivity.this, UireFreshActivity.class));
                        }
                    } else if (appInfos.get(position).getPackageName().equals(getPackageName() + ".systemclean")) {//点击了“清理”
                        String s_clean = Build.BRAND;
                        if (s_clean.equals("Allwinner")) {
                            Intent intent_clear = new Intent("com.mogu.clear_mem");
                            sendBroadcast(intent_clear);
                        }
                    } else {//出现异常
                        DeBugDialog.debug_show_dialog(MainActivity.this, "启动APP时出现“Intent”相关的异常");
                    }
                } catch (Exception e) {
                    DeBugDialog.debug_show_dialog(MainActivity.this, e.toString());
                }
            }
        });
        //切换应用列表
        tg_apps_state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    mListView.setVisibility(View.VISIBLE);
                    iv_index_back.setVisibility(View.GONE);
                } else {
                    mListView.setVisibility(View.GONE);
                    iv_index_back.setVisibility(View.VISIBLE);
                }
            }
        });
        //长按“小时”进入设置
        tv_time_hour.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            }
        });
        /**
         * 每次回到桌面开启常驻通知
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setNotification();
            }
        }, 50);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        check_view_hind(MainActivity.this, sharedPreferences);//检查底栏是否隐藏
        check_offline_mode(MainActivity.this, sharedPreferences);//检查离线模式是否打开
        images_upgrade();//更新图像信息
        get_applist_number();//获取设定的应用列表列数
        set_app_setStackFromBottomMode(sharedPreferences);//检查并设置APP列表排列方式
    }

    public static void initSkinMode(Context context, String s) {
        try {
            // 获取壁纸管理器
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            // 获取当前壁纸
            BitmapDrawable wallpaperDrawable = (BitmapDrawable) wallpaperManager.getDrawable();
            // 将Drawable,转成Bitmap
            Bitmap bitmap = Bitmap.createBitmap(wallpaperDrawable.getBitmap());
//        val bm = (wallpaperDrawable as BitmapDrawable).bitmap
            // 设置 背景
//        .background = (BitmapDrawable(bm))
            if (s.equals("app_wallpaper")) {
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                MainActivity.iv_index_back.setImageBitmap(bitmap);
                MainActivity.tg_apps_state.setVisibility(View.VISIBLE);
                tg_apps_state.setChecked(false);
            }
            if (s.equals("app_wallpaper_applist")) {
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.VISIBLE);
                MainActivity.iv_index_back.setImageBitmap(bitmap);
                MainActivity.tg_apps_state.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            /**
             * 创建纯白bitmap
             */
            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888); // 创建画布
            drawCanvas(bitmap);
            MainActivity.iv_index_back.setVisibility(View.VISIBLE);
            MainActivity.mListView.setVisibility(View.VISIBLE);
            MainActivity.iv_index_back.setImageBitmap(bitmap);
            MainActivity.tg_apps_state.setVisibility(View.GONE);
//            DiyToast.showToast(context, "系统壁纸出错，重置为白色", true);
            DeBugDialog.debug_show_dialog(context, "系统壁纸获取出错 \n 请更改其他壁纸设置 \n 错误信息：" + e.toString());
        }
    }

    public static void drawCanvas(Bitmap bitmap) {
        bitmap.eraseColor(Color.parseColor("#ff0000")); // 填充颜色
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.DKGRAY);
        paint.setFlags(100);
        paint.setStyle(Paint.Style.STROKE); //用于设置字体填充的类型
//        canvas.drawText("Ken", 100, 100, paint);
        //最后通过Imageview显示出来
        MainActivity.iv_index_back.setImageBitmap(bitmap);
    }


    private void set_app_setStackFromBottomMode(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean("app_setStackFromBottomMode", true) == false) {
            mListView.setStackFromBottom(false);
        } else {
            mListView.setStackFromBottom(true);
        }
    }

    private void check_first_user() {
        if (isFirstStart(MainActivity.this)) {//第一次
            /**
             * 填充预设数据
             */
            SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
            editor.putString("images_info", "applist");//默认显示内容
            editor.putString("images_app_listifo", "true");
            editor.putString("appname_state", "nope");//是否显示APP名称
            editor.putString("applist_number", "auto");//默认APP列表大小
            editor.putString("timetext_min_size", "50");//分钟时间大小
            editor.putString("timetext_hour_size", "70");//小时时间大小
            editor.putString("nametext_size", "16");//昵称文本大小
            editor.putString("dianchitext_size", "16");//电池文本大小
            editor.putString("datetext_size", "16");//日期文本大小
            editor.putString("setting_ico_hind", "false");//隐藏底栏
            editor.putString("offline", "false");//离线模式
            editor.putBoolean("app_setStackFromBottomMode", false);//默认显示内容
            editor.apply();
            //更新桌面信息
            images_upgrade();
            //填充预设隐藏应用包名
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("frist");
            SaveArrayListUtil.saveArrayList(MainActivity.this, arrayList, "start");//存储在本地
            //第一次安装给出提示
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("欢迎使用");
            builder.setMessage("您是第一次安装，强烈建议您查看 桌面设置-说明书 相关条目");
            builder.setPositiveButton("确定", null);
            builder.setNeutralButton("查看说明书", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, InstructionsActivity.class));
                }
            });
            builder.show();
            initAppList(MainActivity.this);
        }
    }

    public static void check_offline_mode(Context context, SharedPreferences sharedPreferences) {
        try {
            String offline = sharedPreferences.getString("offline", null);
            if (offline.equals("true")) {
                offline_mode = true;
            } else {
                offline_mode = false;
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info", context.MODE_PRIVATE).edit();
            editor.putString("offline", "false");//日期文本大小
            editor.apply();
        }
    }

    public static void check_view_hind(Context context, SharedPreferences sharedPreferences) {
        try {
            String ico_info = sharedPreferences.getString("setting_ico_hind", null);
            if (ico_info.equals("true")) {
                line_bottom.setVisibility(View.GONE);
//                MainActivity.iv_setting_button.setVisibility(View.GONE);
//                iv_setting_lock.setVisibility(View.GONE);
//                iv_setting_yinliang.setVisibility(View.GONE);
//                tg_apps_state.setVisibility(View.GONE);
//                iv_setting_rss.setVisibility(View.GONE);
//                view_buttom.setVisibility(View.INVISIBLE);
            } else {
                line_bottom.setVisibility(View.VISIBLE);
//                iv_setting_button.setVisibility(View.VISIBLE);
//                iv_setting_lock.setVisibility(View.VISIBLE);
//                iv_setting_rss.setVisibility(View.VISIBLE);
//                iv_setting_yinliang.setVisibility(View.VISIBLE);
//                tg_apps_state.setVisibility(View.VISIBLE);
//                view_buttom.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info", context.MODE_PRIVATE).edit();
            editor.putString("setting_ico_hind", "false");//日期文本大小
            editor.apply();
        }
    }

    private void get_applist_number() {
        try {
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            String applist_number = sharedPreferences.getString("applist_number", null);
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
            mListView.setVisibility(View.GONE);
            tg_apps_state.setVisibility(View.VISIBLE);
            tg_apps_state.setChecked(false);
        }
        if (images_mode.equals("mz")) {
            iv_index_back.setImageResource(R.drawable.mi_meizi);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            tg_apps_state.setVisibility(View.VISIBLE);
            tg_apps_state.setChecked(false);
        }
        if (images_mode.equals("ll")) {
            iv_index_back.setImageResource(R.drawable.mi_luoli);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            tg_apps_state.setVisibility(View.VISIBLE);
            tg_apps_state.setChecked(false);
        }
        if (images_mode.equals("zy")) {
            iv_index_back.setImageResource(R.drawable.mi_zhiyu);
            iv_index_back.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            tg_apps_state.setVisibility(View.VISIBLE);
            tg_apps_state.setChecked(false);
        }
        if (images_mode.equals("applist")) {
            iv_index_back.setImageResource(R.drawable.mi_haole);
            iv_index_back.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            tg_apps_state.setVisibility(View.GONE);
        }
        if (images_mode.equals("")) {
            iv_index_back.setImageResource(R.drawable.mi_haole);
            iv_index_back.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            DiyToast.showToast(this, "请选择壁纸或者应用列表（设置-壁纸设置）", false);
        }
        if (images_mode.equals("app_wallpaper")) {
            initSkinMode(MainActivity.this, images_mode);
        }
        if (images_mode.equals("app_wallpaper_applist")) {
            initSkinMode(MainActivity.this, images_mode);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /**
         * 更新天气信息
         */
        if (!offline_mode) {
            Cursor cursor = db.rawQuery("select * from wather_city", null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                update_wather(MainActivity.this,
                        cursor.getString(cursor.getColumnIndex("city")));
            }
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            update_wathers(sharedPreferences);
        } else {
            line_wather.setVisibility(View.INVISIBLE);
        }
        initAppList(MainActivity.this);
    }

    /**
     * +获取应用列表、隐藏应用
     *
     * @param context
     */
    public static void initAppList(Context context) {
        appInfos = GetApps.GetAppList1(context);
        ArrayList<String> hind_apparrayList = new ArrayList<String>();
        String s = Build.BRAND;
        hind_apparrayList.clear();
        hind_apparrayList = SaveArrayListUtil.getSearchArrayList(context);
//        if (s.equals("Allwinner")) {
//            hind_apparrayList.add("com.duokan.einkreader");
//            hind_apparrayList.add("com.android.settings");
//        }
        for (int j = 0; j < hind_apparrayList.size(); j++) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (hind_apparrayList.get(j).equals(appInfos.get(i).getPackageName())) {
                    appInfos.remove(i);
//                    continue;
                }
            }
        }
        DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(appInfos,
                context);
        mListView.setAdapter(deskTopGridViewBaseAdapter);
    }

    /**
     * 读取昵称
     * <p>
     * SQLite
     */
    public static void rember_name(Context context) {
        Cursor cursor = MainActivity.db.rawQuery("select * from name", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            MainActivity.tv_user_id.setText(cursor.getString(cursor
                    .getColumnIndex("username")));
            if (MainActivity.tv_user_id.getText().toString().isEmpty()) {
                MainActivity.tv_user_id.setText("请设置文本（桌面设置中）");
            } else if (MainActivity.tv_user_id.getText().toString().equals("")) {
                MainActivity.tv_user_id.setText("请设置文本（桌面设置中）");
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
                SimpleDateFormat simpleDateFormat_date = new SimpleDateFormat(
                        "yyyy年MM月dd日");
                tv_main_nowdate.setText(simpleDateFormat_date
                        .format(new java.util.Date()));
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
        line_bottom = (LinearLayout) findViewById(R.id.linearLayout2);
        tv_main_nowdate = (TextView) findViewById(R.id.tv_main_nowdate);
        iv_clean_button = (ImageView) findViewById(R.id.iv_clean_button);
        iv_setting_rss = (ImageView) findViewById(R.id.iv_setting_rss);
        iv_setting_refresh = (ImageView) findViewById(R.id.iv_setting_refresh);
        view_buttom = (View) findViewById(R.id.view_buttom);
        mListView = (GridView) findViewById(R.id.mAppGridView);
        iv_setting_button = (ImageView) findViewById(R.id.iv_setting_button);
        tv_time_hour = (TextView) findViewById(R.id.tv_time_hour);
        tg_apps_state = (ToggleButton) findViewById(R.id.tg_apps_state);
        tv_time_min = (TextView) findViewById(R.id.tv_time_min);
        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        tv_main_batterystate = (TextView) findViewById(R.id.tv_main_batterystate);
        line_wather = (LinearLayout) findViewById(R.id.line_wather);
        tv_city = (TextView) findViewById(R.id.tv_city);
        iv_setting_yinliang = (ImageView) findViewById(R.id.iv_setting_yinliang);
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        iv_index_back = (ImageView) findViewById(R.id.iv_index_back);
        tv_temp_state = (TextView) findViewById(R.id.tv_temp_state);
        tv_last_updatetime = (TextView) findViewById(R.id.tv_last_updatetime);
        iv_setting_button.setOnClickListener(this);
        line_wather.setOnClickListener(this);
        iv_setting_yinliang.setOnClickListener(this);
        iv_setting_rss.setOnClickListener(this);
        iv_setting_refresh.setOnClickListener(this);
        iv_clean_button.setOnClickListener(this);
        String s_clean = Build.BRAND;
        if (s_clean.equals("Allwinner")) {
            iv_clean_button.setVisibility(View.VISIBLE);
        } else {
            iv_clean_button.setVisibility(View.INVISIBLE);
        }
        //数据库
        dbHelper_name_sql = new MyDataBaseHelper(getApplicationContext(), "info.db",
                null, 2);
        db = dbHelper_name_sql.getWritableDatabase();
        //动态注册
        appinstallserver = new AppInstallServer();
        appinstallserver.register(this);
    }

    private void update_wathers(SharedPreferences sharedPreferences) {
        if (!offline_mode) {
            tv_wind.setText(sharedPreferences.getString("wather_info_wind", null));
            tv_temp_state.setText(sharedPreferences.getString("wather_info_temp", null));
            tv_last_updatetime.setText(sharedPreferences.getString("wather_info_updatetime", null));
            tv_city.setText(sharedPreferences.getString("wather_info_citytype", null));
            /**
             * 判断设置是不是隐藏天气布局
             */
            check_weather_view(sharedPreferences);
        } else {
            line_wather.setVisibility(View.INVISIBLE);
        }
    }

    private void check_weather_view(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean("isHind_weather", false) == true) {
            line_wather.setVisibility(View.INVISIBLE);
        } else if (sharedPreferences.getBoolean("isHind_weather", false) == false) {
            line_wather.setVisibility(View.VISIBLE);
        } else {
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
                            DiyToast.showToast(getApplicationContext(), "请设置城市", true);
                        }
                        SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                        editor.putString("wather_info_citytype", cursor.getString(cursor
                                .getColumnIndex("city")) + "  " + type);
                        editor.putString("wather_info_wind", fengxiang);
                        editor.putString("wather_info_temp", high + "  " + low);
                        editor.putString("wather_info_updatetime", "于"
                                + tv_time_hour.getText().toString() + ":"
                                + tv_time_min.getText().toString() + "更新");
                        editor.apply();
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
                    DiyToast.showToast(getApplicationContext(), "城市无效（已重置为北京）", true);
                    db.execSQL("update wather_city set city = ? ",
                            new String[]{"北京"});
                    break;
                case 2:
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putString("wather_info_updatetime", "于"
                            + tv_time_hour.getText().toString() + ":"
                            + tv_time_min.getText().toString() + "更新（离线状态）");
                    editor.apply();
                    SharedPreferences sharedPreferences;
                    sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                    update_wathers(sharedPreferences);
                    break;
                default:
                    break;
            }
        }
    };

    public void update_wather(Context context, final String city) {
        if (TextUtils.isEmpty(city)) {
            DiyToast.showToast(context, "城市错误，不在数据库中", true);
            return;
        }
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
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
                        // 连接网络成功
                        InputStream in = conn.getInputStream();
                        String data = StreamTool.decodeStream(in);
                        // 解析json格式的数据
                        JSONObject jsonObj = new JSONObject(data);
                        // 获得desc的值
                        String result = jsonObj.getString("desc");
                        if ("OK".equals(result)) {
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
                            // 城市无效
                            Message msg = Message.obtain();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    } else {
                        // 联网失败
                        Message msg = Message.obtain();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
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
        appinstallserver.unregister(this);
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
                        sb.append(String.valueOf(level) + "% " + " FULL* ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_CHARGING) {//充电
                        sb.append(String.valueOf(level) + "% " + " C+ ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {//放电
                        sb.append(String.valueOf(level) + "% " + " U- ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                    if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {//未在充电
                        sb.append(String.valueOf(level) + "% " + " U- ");
                        tv_main_batterystate.setText(sb.toString());
                    }
                }
                sb.append(' ');
            }
        };
        batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }

    /**
     * 桌面底栏功能 点击事件监听
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            //设置
            case R.id.iv_setting_button:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            //天气
            case R.id.line_wather:
                Cursor cursor = db.rawQuery("select * from wather_city", null);
                if (!offline_mode) {
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
                        DiyToast.showToast(getApplicationContext(), "正在更新", true);
                    }
                } else {
                    DiyToast.showToast(getApplicationContext(), "当前处于离线模式", true);
                }
                break;
            //音量
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
            case R.id.iv_clean_button:
                String s_clean = Build.BRAND;
                if (s_clean.equals("Allwinner")) {
                    Intent intent_clear = new Intent("com.mogu.clear_mem");
                    sendBroadcast(intent_clear);
//                    Intent intent = new Intent("android.eink.force.refresh");
//                    sendBroadcast(intent);
                }
                break;
            //RSS订阅
            case R.id.iv_setting_rss:
                DiyToast.showToast(MainActivity.this, "调试中的功能", false);
                break;
            //刷新
            case R.id.iv_setting_refresh:
                String s = Build.BRAND;
                if (s.equals("Allwinner")) {
//                    Intent intent_clear = new Intent("com.mogu.clear_mem");
//                    sendBroadcast(intent_clear);
                    Intent intent = new Intent("android.eink.force.refresh");
                    sendBroadcast(intent);
                } else {
                    startActivity(new Intent(MainActivity.this, UireFreshActivity.class));
                }
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

    /**
     * 检查文本大小并设置
     *
     * @param context
     */
    public static void check_text_size(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("info", MODE_PRIVATE);
            MainActivity.tv_time_hour.setTextSize(Integer.valueOf(sharedPreferences.getString("timetext_hour_size", null)));
            MainActivity.tv_time_min.setTextSize(Integer.valueOf(sharedPreferences.getString("timetext_min_size", null)));
            MainActivity.tv_user_id.setTextSize(Integer.valueOf(sharedPreferences.getString("nametext_size", null)));
            MainActivity.tv_main_batterystate.setTextSize(Integer.valueOf(sharedPreferences.getString("dianchitext_size", null)));
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info", MODE_PRIVATE).edit();
            /**
             * 设定文本大小预填充
             */
            editor.putString("timetext_min_size", "50");
            editor.putString("timetext_hour_size", "70");
            editor.putString("nametext_size", "17");//昵称文本大小
            editor.putString("dianchitext_size", "17");//电池文本大小
            editor.putString("datetext_size", "17");//日期文本大小
            editor.apply();
            check_text_size(context);
        }
    }
}