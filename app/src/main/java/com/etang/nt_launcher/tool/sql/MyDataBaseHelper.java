package com.etang.nt_launcher.tool.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.etang.nt_launcher.tool.toast.DiyToast;

/**
 * 数据库
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //存放昵称
        db.execSQL("create table name(_id integer primary key autoincrement,username text)");
        db.execSQL("insert into name(username)values(?)", new String[]{""});
        //存放天气地理位置
        db.execSQL("create table wather_city(_id integer primary key autoincrement,city text)");
        db.execSQL("insert into wather_city(city)values(?)",
                new String[]{"北京"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
