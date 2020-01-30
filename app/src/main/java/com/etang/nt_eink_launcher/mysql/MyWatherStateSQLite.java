package com.etang.nt_eink_launcher.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 */
public class MyWatherStateSQLite extends SQLiteOpenHelper {

    public MyWatherStateSQLite(Context context, String name,
                               CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //存放天气离线信息
        db.execSQL("create table wather_offline(_id integer primary key autoincrement,city_state text,wind_state text,temp_state text,last_updatetime text)");
        db.execSQL("insert into wather_city(city)values(?)",
                new String[]{"北京  晴","西北风","高温 0℃  低温 0℃","首次使用，请长按此处确定您的城市"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
