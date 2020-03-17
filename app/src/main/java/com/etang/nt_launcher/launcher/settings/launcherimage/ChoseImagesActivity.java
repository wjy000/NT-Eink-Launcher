package com.etang.nt_launcher.launcher.settings.launcherimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;

public class ChoseImagesActivity extends AppCompatActivity {
    private RadioButton ra_chahua, ra_erji, ra_meizi, ra_qinglv, ra_applist, ra_luoli, ra_pinbo, ra_yali, ra_zhiyu;
    private Button btn_see_image;
    private ImageView iv_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);//应用运行时，保持屏幕高亮，不锁屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_chose_images);
        setTitle("请选择");//设置title标题
        initView();//绑定控件
        check_image();
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
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("壁纸设置");
        btn_see_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                String images_mode = sharedPreferences.getString("images_info", null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChoseImagesActivity.this);
                View view = LayoutInflater.from(ChoseImagesActivity.this).inflate(R.layout.dialog_chose_image, null);
                builder.setView(view);
                ImageView iv_see_image = (ImageView) view
                        .findViewById(R.id.iv_see_image);
                if (images_mode.equals("ql")) {
                    iv_see_image.setImageResource(R.drawable.mi_haole);
                }
                if (images_mode.equals("ej")) {
                    iv_see_image.setImageResource(R.drawable.mi_erji);
                }
                if (images_mode.equals("mz")) {
                    iv_see_image.setImageResource(R.drawable.mi_meizi);
                }
                if (images_mode.equals("ch")) {
                    iv_see_image.setImageResource(R.drawable.mi_chahua);
                }
                if (images_mode.equals("ll")) {
                    iv_see_image.setImageResource(R.drawable.mi_luoli);
                }
                if (images_mode.equals("yl")) {
                    iv_see_image.setImageResource(R.drawable.mi_yali);
                }
                if (images_mode.equals("pb")) {
                    iv_see_image.setImageResource(R.drawable.mi_pinbo);
                }
                if (images_mode.equals("zy")) {
                    iv_see_image.setImageResource(R.drawable.mi_zhiyu);
                }
                if (images_mode.equals("applist")) {
                    DiyToast.showToast(ChoseImagesActivity.this, "应用列表");
                }
                if (images_mode.equals("")) {
                    DiyToast.showToast(ChoseImagesActivity.this, "请选择壁纸或者应用列表（设置-壁纸设置）");
                }
                builder.setTitle("图片预览：" + images_mode);
                builder.setPositiveButton("关闭", null);
                builder.show();
            }
        });
    }

    private void check_image() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String images_mode = sharedPreferences.getString("images_info", null);
        if (images_mode.equals("ql")) {
            ra_qinglv.setChecked(true);
        }
        if (images_mode.equals("ej")) {
            ra_erji.setChecked(true);
        }
        if (images_mode.equals("mz")) {
            ra_meizi.setChecked(true);
        }
        if (images_mode.equals("ch")) {
            ra_chahua.setChecked(true);
        }
        if (images_mode.equals("ll")) {
            ra_luoli.setChecked(true);
        }
        if (images_mode.equals("yl")) {
            ra_yali.setChecked(true);
        }
        if (images_mode.equals("pb")) {
            ra_pinbo.setChecked(true);
        }
        if (images_mode.equals("zy")) {
            ra_zhiyu.setChecked(true);
        }
        if (images_mode.equals("applist")) {
            ra_applist.setChecked(true);
            DiyToast.showToast(this, "应用列表");
        }
        if (images_mode.equals("")) {
            DiyToast.showToast(this, "请选择壁纸或者应用列表（设置-壁纸设置）");
        }
    }

    /**
     * 绑定控件
     */
    private void initView() {
        btn_see_image = (Button) findViewById(R.id.btn_see_image);
        iv_back = (ImageView) findViewById(R.id.iv_title_back);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
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
