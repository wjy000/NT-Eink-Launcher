package com.etang.nt_launcher.launcher.settings.launcherimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.dialog.DeBugDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.etang.nt_launcher.R;

import java.io.IOException;

public class ChoseImagesActivity extends AppCompatActivity {
    private RadioButton ra_meizi, ra_qinglv, ra_applist, ra_luoli, ra_zhiyu, ra_wallpaper, ra_wallpaper_and_applist;
    private Button btn_set_wallpaperimage;
    private static final int IMAGE_PICK = 2654;
    Bitmap bitmap = null;
    private TextView tv_back, tv_button, tv_title;
    private LinearLayout lv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.setting_chose_images);
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
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：应用列表", true);
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
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：妹子", true);
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
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：情侣", true);
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
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：萝莉", true);
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
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：知遇", true);
            }
        });
        //系统壁纸
        ra_wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "app_wallpaper");
                editor.apply();
                //
//                SavePermission.check_save_permission(ChoseImagesActivity.this, ChoseImagesActivity.this);//检查存取权限
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                MainActivity.iv_index_back.setImageResource(R.drawable.mi_yali);
                MainActivity.tg_apps_state.setChecked(false);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.INVISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：系统壁纸", true);
                MainActivity.initSkinMode(ChoseImagesActivity.this, "app_wallpaper");
            }
        });
        //系统壁纸+应用列表
        ra_wallpaper_and_applist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                editor.putString("images_info", "app_wallpaper_applist");
                editor.apply();
//                //
//                SavePermission.check_save_permission(ChoseImagesActivity.this, ChoseImagesActivity.this);//检查存取权限
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, IMAGE_PICK);
//                MainActivity.iv_index_back.setImageResource(R.drawable.mi_yali);
                MainActivity.tg_apps_state.setVisibility(View.GONE);
                MainActivity.iv_index_back.setVisibility(View.VISIBLE);
                MainActivity.mListView.setVisibility(View.VISIBLE);
                DiyToast.showToast(ChoseImagesActivity.this, "已更换：系统壁纸和应用列表", true);
                MainActivity.initSkinMode(ChoseImagesActivity.this, "app_wallpaper_applist");
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("壁纸设置");
        tv_button.setText("预览壁纸");
        lv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePermission.check_save_permission(ChoseImagesActivity.this);//检查存取权限
                show_dialog();
            }
        });
        btn_set_wallpaperimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePermission.check_save_permission(ChoseImagesActivity.this);//检查存取权限
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK);
            }
        });

    }

    private void show_dialog() {
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
        if (images_mode.equals("mz")) {
            iv_see_image.setImageResource(R.drawable.mi_meizi);
        }
        if (images_mode.equals("ll")) {
            iv_see_image.setImageResource(R.drawable.mi_luoli);
        }
        if (images_mode.equals("zy")) {
            iv_see_image.setImageResource(R.drawable.mi_zhiyu);
        }
        if (images_mode.equals("applist")) {
            iv_see_image.setImageResource(R.drawable.ic_setting);
        }
        if (images_mode.equals("")) {
            DiyToast.showToast(ChoseImagesActivity.this, "请选择壁纸或者应用列表", true);
        }
        if (images_mode.equals("app_wallpaper")) {
            iv_see_image.setImageBitmap(bitmap);
        }
        if (images_mode.equals("app_wallpaper_applist")) {
            iv_see_image.setImageBitmap(bitmap);
        }
        builder.setTitle("图片预览：" + images_mode);
        builder.setPositiveButton("关闭", null);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        try {
                            setWallpaper(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            DeBugDialog.debug_show_dialog(ChoseImagesActivity.this, "设置壁纸时出现错误：" + e.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        DeBugDialog.debug_show_dialog(ChoseImagesActivity.this, "获取图片时出现错误：" + e.toString());
                    }
                }
                DiyToast.showToast(getApplicationContext(), "选择成功，可点击右上角进行预览。\n路径：", true);
            } else {
                DiyToast.showToast(getApplicationContext(), "你并没有选择什么", true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void check_image() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String images_mode = sharedPreferences.getString("images_info", null);
        if (images_mode.equals("ql")) {
            ra_qinglv.setChecked(true);
        }
        if (images_mode.equals("mz")) {
            ra_meizi.setChecked(true);
        }
        if (images_mode.equals("ll")) {
            ra_luoli.setChecked(true);
        }
        if (images_mode.equals("zy")) {
            ra_zhiyu.setChecked(true);
        }
        if (images_mode.equals("applist")) {
            ra_applist.setChecked(true);
        }
        if (images_mode.equals("")) {
            DiyToast.showToast(this, "请选择壁纸或者应用列表", true);
        }
        if (images_mode.equals("app_wallpaper")) {
            ra_wallpaper.setChecked(true);
        }
        if (images_mode.equals("app_wallpaper_applist")) {
            ra_wallpaper_and_applist.setChecked(true);
        }
    }

    /**
     * 绑定控件
     */
    private void initView() {
        lv_back = (LinearLayout) findViewById(R.id.lv_back);
        tv_back = (TextView) findViewById(R.id.tv_title_back);
        tv_button = (TextView) findViewById(R.id.tv_title_button);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        btn_set_wallpaperimage = (Button) findViewById(R.id.btn_set_wallpaperimage);
        ra_applist = (RadioButton) findViewById(R.id.ra_chose_applist_info);
        ra_meizi = (RadioButton) findViewById(R.id.ra_chose_meizi);
        ra_qinglv = (RadioButton) findViewById(R.id.ra_chose_qinglv);
        ra_luoli = (RadioButton) findViewById(R.id.ra_chose_luoli);
        ra_zhiyu = (RadioButton) findViewById(R.id.ra_chose_zhiyu);
        ra_wallpaper_and_applist = (RadioButton) findViewById(R.id.ra_wallpaper_and_applist);
        ra_wallpaper = (RadioButton) findViewById(R.id.ra_wallpaper);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
