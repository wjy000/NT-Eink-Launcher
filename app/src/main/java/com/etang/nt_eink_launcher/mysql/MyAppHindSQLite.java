package com.etang.nt_eink_launcher.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 */
public class MyAppHindSQLite extends SQLiteOpenHelper {

    public MyAppHindSQLite(Context context, String name,
                           CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table app_hind(_id integer primary key autoincrement,app_name text)");
        db.execSQL("insert into app_hind(app_name)values(?)",
                new String[]{""});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
