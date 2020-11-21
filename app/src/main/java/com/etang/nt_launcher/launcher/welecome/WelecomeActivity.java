package com.etang.nt_launcher.launcher.welecome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.dialog.NewUserDialog;
import com.etang.nt_launcher.tool.permission.SavePermission;
import com.etang.nt_launcher.tool.toast.DiyToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WelecomeActivity extends FragmentActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    List<Fragment> list_fragment = new ArrayList<Fragment>();
    FloatingActionButton fab_welecome_pass;
    String state = "";
    private TextView tv_back, tv_button, tv_title;

    private class PagerAdapter extends FragmentPagerAdapter {


        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_welecome);
        initView();
        try {
            Intent intent = getIntent();
            state = intent.getStringExtra("state");
            if (!state.equals("false")) {
                show_dialog();
            }
        } catch (Exception e) {
            state = "true";
            show_dialog();
        }
        fab_welecome_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() == 1 && !state.equals("false")) {
                    dialog_newuser(true);
                } else if (viewPager.getCurrentItem() == 1 && state.equals("false")) {
                    dialog_newuser(false);
                }
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        tv_title.setText("使用向导");
                        tv_button.setText("欢迎使用");
                        break;
                    case 1:
                        tv_title.setText("说明书");
                        tv_button.setText("请扫码查阅说明书");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_title.setText("使用向导");
                        tv_button.setText("欢迎使用");
                        break;
                    case 1:
                        tv_title.setText("说明书");
                        tv_button.setText("请扫码查阅说明书");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tv_back.setText("MT-Project | ");
        tv_title.setText("使用向导");
        tv_button.setText("欢迎使用");
    }

    private void show_dialog() {
        new AlertDialog.Builder(WelecomeActivity.this)
                .setTitle("权限申请：")
                .setMessage("梅糖桌面想要使用“本地存储权限”" + "\n" + "权限说明：用于存放缓存以及“更新功能”和“更换图标功能”")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavePermission.check_save_permission(WelecomeActivity.this);
                    }
                }).setNegativeButton("也是确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SavePermission.check_save_permission(WelecomeActivity.this);
            }
        }).setNeutralButton("还是确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SavePermission.check_save_permission(WelecomeActivity.this);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DiyToast.showToast(WelecomeActivity.this, "请给予权限", true);
                SavePermission.check_save_permission(WelecomeActivity.this);
            }
        }).show();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_title_back);
        tv_button = (TextView) findViewById(R.id.tv_title_button);
        tv_title = (TextView) findViewById(R.id.tv_title_text);
        fab_welecome_pass = findViewById(R.id.fab_welecome_pass);
        viewPager = (ViewPager) findViewById(R.id.pager_welecome);
        list_fragment.add(new OneFragment());
        list_fragment.add(new TwoFragment());
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private void dialog_newuser(final boolean show_or_notshow) {
        final AlertDialog alertDialog = new AlertDialog.Builder(WelecomeActivity.this).create();
        alertDialog.setTitle("允许激活？");
        alertDialog.setMessage("只需激活一次，仅做用户设备型号统计，需要联网。\n如果不需要请直接点击“取消”\n本对话框只会显示一次");
        View view = LayoutInflater.from(WelecomeActivity.this).inflate(R.layout.dialog_newuserchecker, null, false);
        alertDialog.setView(view);
        alertDialog.setCanceledOnTouchOutside(false);
        Button btn_con = (Button) view.findViewById(R.id.btn_newusercheck_con);
        Button btn_cls = (Button) view.findViewById(R.id.btn_newusercheck_cls);
        if (show_or_notshow) {
            alertDialog.show();
        } else {
            startActivity(new Intent(WelecomeActivity.this, MainActivity.class));
            finish();
        }
        btn_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(WelecomeActivity.this, MainActivity.class));
                finish();
            }
        });
        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewUserDialog.dialog_show(WelecomeActivity.this, "设备激活（新用户）：");
                alertDialog.dismiss();
                startActivity(new Intent(WelecomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


}