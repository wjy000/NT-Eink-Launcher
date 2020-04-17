package com.etang.nt_launcher.tool.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 */
public class MyDiaryBaseHelper extends SQLiteOpenHelper {

    public MyDiaryBaseHelper(Context context, String name,
                             CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //存放日记内容
//        db.execSQL("create table diary(_id integer primary key autoincrement,diarydate text,diarytext text)");
//        db.execSQL("insert into diary(diarydate,diarytext)values(?,?)", new String[]{"某个时间", "“Oasis”来到了这个世界上"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
