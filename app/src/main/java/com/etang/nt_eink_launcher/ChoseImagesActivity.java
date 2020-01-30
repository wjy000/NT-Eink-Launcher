package com.etang.nt_eink_launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.etang.nt_eink_launcher.toast.DiyToast;
import com.etang.nt_launcher.R;

public class ChoseImagesActivity extends AppCompatActivity {
    private RadioButton ra_chahua;
    private RadioButton ra_erji;
    private RadioButton ra_meizi;
    private RadioButton ra_qinglv;
    private RadioButton ra_applist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chose_images);
        setTitle("");
        initView();
        ra_applist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "applist");
                editor.apply();
                MainActivity.tg_apps_state.setChecked(true);
                MainActivity.iv_index_back.setVisibility(View.INVISIBLE);
                MainActivity.mListView.setVisibility(View.VISIBLE);
                finish();
            }
        });
        ra_chahua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ch");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.mipmap.mi_chahua);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                finish();
            }
        });
        ra_erji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ej");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.mipmap.mi_erji);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                finish();
            }
        });
        ra_meizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "mz");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.mipmap.mi_meizi);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                finish();
            }
        });
        ra_qinglv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "ql");
                editor.apply();
                MainActivity.iv_index_back.setImageResource(R.mipmap.mi_haole);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                finish();
            }
        });


    }

    private void initView() {
        ra_applist = (RadioButton) findViewById(R.id.ra_chose_applist_info);
        ra_chahua = (RadioButton) findViewById(R.id.ra_chose_chahua);
        ra_erji = (RadioButton) findViewById(R.id.ra_chose_erji);
        ra_meizi = (RadioButton) findViewById(R.id.ra_chose_meizi);
        ra_qinglv = (RadioButton) findViewById(R.id.ra_chose_qinglv);
    }
}
