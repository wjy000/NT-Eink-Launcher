package com.etang.nt_launcher.tool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.diary.DiaryActivity;
import com.etang.nt_launcher.tool.sql.MyDiaryBaseHelper;
import com.etang.nt_launcher.tool.toast.DiyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteDiaryDialog {
    private static SQLiteDatabase db;
    private static MyDiaryBaseHelper myDiaryBaseHelper;

    public static void write_show_dialog(final Context context) {
        /**
         * 初始化数据库
         */
        myDiaryBaseHelper = new MyDiaryBaseHelper(context, "diary.db", null, 2);
        db = myDiaryBaseHelper.getWritableDatabase();
        final AlertDialog builder = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_write_diary, null, false);
        builder.setView(view);
        builder.setTitle("新建日记");
        final Button btn_dialog_diary_cls = (Button) view.findViewById(R.id.btn_dialog_diary_cls);
        final Button btn_dialog_diary_con = (Button) view.findViewById(R.id.btn_dialog_diary_con);
        final Button btn_dialog_diary_clean = (Button) view.findViewById(R.id.btn_dialog_diary_clean);
        final EditText et_dialog_diary_edit = (EditText) view.findViewById(R.id.et_dialog_diary_edit);
        btn_dialog_diary_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_dialog_diary_edit.setText("");
            }
        });
        btn_dialog_diary_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btn_dialog_diary_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_dialog_diary_edit.getText().toString().isEmpty()) {
                    DiyToast.showToast(context, "你并没有输入什么", true);
                } else if (et_dialog_diary_edit.getText().toString().equals("")) {
                    DiyToast.showToast(context, "你并没有输入什么", true);
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                    db.execSQL("insert into diary(diarydate,diarytext)values(?,?)", new String[]{simpleDateFormat.format(new Date()).toString(), et_dialog_diary_edit.getText().toString()});
                    DiyToast.showToast(context, "创建成功", true);
                    builder.dismiss();
                    DiaryActivity.array_list_adapter(context);
                }
            }
        });
        builder.show();
    }
}
