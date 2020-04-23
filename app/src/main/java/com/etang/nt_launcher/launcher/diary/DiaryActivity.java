package com.etang.nt_launcher.launcher.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.dialog.DeBugDialog;
import com.etang.nt_launcher.tool.dialog.WriteDiaryDialog;
import com.etang.nt_launcher.tool.sql.MyDiaryBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class DiaryActivity extends AppCompatActivity {
    private TextView tv_title_text, tv_back_text, tv_title_imagetext;
    private ImageView iv_title_back, iv_title_imagebutton;
    private Button btn_delete_diary;
    private SQLiteDatabase db;
    private MyDiaryBaseHelper myDiaryBaseHelper;
    public static ListView lv_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_diary);
        initView();
        check_sql();//检查数据库是否已经创建
        tv_title_text.setText("日记");
        tv_title_imagetext.setText("写日记");
        tv_back_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_imagetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteDiaryDialog.write_show_dialog(DiaryActivity.this);
            }
        });
        iv_title_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteDiaryDialog.write_show_dialog(DiaryActivity.this);
            }
        });
        btn_delete_diary.setVisibility(View.GONE);
//        btn_delete_diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.rawQuery("delete table tb", null);
//            }
//        });
        array_list_adapter(DiaryActivity.this);
    }

    public static void array_list_adapter(Context context) {
        SQLiteDatabase db_array_list_adapter;
        MyDiaryBaseHelper myDiaryBaseHelper_array_list_adapter;
        /**
         * 初始化数据库
         */
        myDiaryBaseHelper_array_list_adapter = new MyDiaryBaseHelper(context, "diary.db", null, 2);
        db_array_list_adapter = myDiaryBaseHelper_array_list_adapter.getWritableDatabase();
        DiaryActivity.lv_diary.setAdapter(null);
        Cursor cursor = db_array_list_adapter.rawQuery("select * from diary",
                null);
        if (cursor.getCount() != 0) {
//            cursor.moveToLast();
//            String s1 = cursor.getString(cursor.getColumnIndex("diarydate"));
//            String s2 = cursor.getString(cursor.getColumnIndex("diarytext"));
//            Log.e("最新的diarydate", s1);
//            Log.e("最新的diarytext", s2);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                    R.layout.layout_diary_list, cursor, new String[]{"diarydate", "diarytext"},
                    new int[]{R.id.tv_diary_title, R.id.tv_diary_text}, 0);
            lv_diary.setAdapter(adapter);
        }
    }

    /**
     * 检查数据库是否已经创建
     */
    private void check_sql() {
        try {
            Cursor cursor = db.rawQuery("select * from diary",
                    null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                String s1 = cursor.getString(cursor.getColumnIndex("diarydate"));
                String s2 = cursor.getString(cursor.getColumnIndex("diarytext"));
                Log.e("最旧的diarydate", s1);
                Log.e("最旧的diarytext", s2);
            } else {
                DiyToast.showToast(getApplicationContext(), "数据库为空，自动创建“Oasis来到了这个世界上”", true);
                db.execSQL("insert into diary(diarydate,diarytext)values(?,?)", new String[]{"某个时间", "“Oasis”来到了这个世界上"});
            }
        } catch (Exception e) {
            db.execSQL("create table diary(_id integer primary key autoincrement,diarydate text,diarytext text)");
            db.execSQL("insert into diary(diarydate,diarytext)values(?,?)", new String[]{"某个时间", "“Oasis”来到了这个世界上"});
            DeBugDialog.debug_show_dialog(DiaryActivity.this, "请放心，这不是一个BUG，点击“关闭”后重新打开本页面即可，这只是提示你当前数据库未创建，目前已经自动创建，如不需要可在桌面内长按“日记”，隐藏“日记”功能");
        }
    }

    private void initView() {
        btn_delete_diary = (Button) findViewById(R.id.btn_delete_diary);
        lv_diary = (ListView) findViewById(R.id.lv_diary);
        iv_title_imagebutton = (ImageView) findViewById(R.id.iv_title_imagebutton);
        tv_title_imagetext = (TextView) findViewById(R.id.tv_title_imagetext);
        tv_back_text = (TextView) findViewById(R.id.tv_back_text);
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        /**
         * 初始化数据库
         */
        myDiaryBaseHelper = new MyDiaryBaseHelper(getApplicationContext(), "diary.db", null, 2);
        db = myDiaryBaseHelper.getWritableDatabase();
    }
}
