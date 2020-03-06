package com.etang.nt_launcher.launcher.settings.launcherimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;

public class ChoseImagesActivity extends AppCompatActivity {
    private RadioButton ra_chahua;
    private RadioButton ra_erji;
    private RadioButton ra_meizi;
    private RadioButton ra_qinglv;
    private RadioButton ra_applist;

    private RadioButton ra_luoli;
    private RadioButton ra_pinbo;
    private RadioButton ra_yali;
    private RadioButton ra_zhiyu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chose_images);
        setTitle("请选择");//设置title标题
        initView();//绑定控件
        //APP列表
        ra_applist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "applist");
                editor.apply();
                MainActivity.tg_apps_state.setChecked(true);
                MainActivity.iv_index_back.setVisibility(View.INVISIBLE);
                MainActivity.mListView.setVisibility(View.VISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：应用列表");
                finish();
            }
        });
        //插画
        ra_chahua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ch");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_chahua);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：插画");
                finish();
            }
        });
        //耳机
        ra_erji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ej");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_erji);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：耳机");
                finish();
            }
        });
        //妹子
        ra_meizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "mz");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_meizi);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：妹子");
                finish();
            }
        });
        //情侣
        ra_qinglv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ql");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_haole);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：情侣");
                finish();
            }
        });
        //压力
        ra_yali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "yl");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_yali);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：压力");
                finish();
            }
        });
        //萝莉
        ra_luoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ll");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_luoli);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：萝莉");
                finish();
            }
        });
        //拼搏
        ra_pinbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "pb");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_pinbo);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：拼搏");
                finish();
            }
        });
        //知遇
        ra_zhiyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "zy");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_zhiyu);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：知遇");
                finish();
            }
        });
        //压力
        ra_yali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "yl");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.drawable.mi_yali);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：压力");
                finish();
            }
        });
    }

    /**
     * 绑定控件
     */
    private void initView() {
        ra_applist = (RadioButton) findViewById(R.id.ra_chose_applist_info);
        ra_chahua = (RadioButton) findViewById(R.id.ra_chose_chahua);
        ra_erji = (RadioButton) findViewById(R.id.ra_chose_erji);
        ra_meizi = (RadioButton) findViewById(R.id.ra_chose_meizi);
        ra_qinglv = (RadioButton) findViewById(R.id.ra_chose_qinglv);
        ra_luoli = (RadioButton) findViewById(R.id.ra_chose_luoli);
        ra_pinbo = (RadioButton) findViewById(R.id.ra_chose_pinbo);
        ra_yali = (RadioButton) findViewById(R.id.ra_chose_yali);
        ra_zhiyu = (RadioButton) findViewById(R.id.ra_chose_zhiyu);
    }
}