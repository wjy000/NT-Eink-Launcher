package com.etang.nt_launcher.launcher.welecome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.etang.nt_launcher.R;

import java.util.Locale;

public class ChoseFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private RadioButton ra_welecome_auto, ra_welecome_cn, ra_welecome_en, ra_welecome_jp;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_welecome_chose, null, false);
        initView(view);
        if (sharedPreferences.getString("language", null).equals("0")) {
            ra_welecome_auto.setChecked(true);
        }
        if (sharedPreferences.getString("language", null).equals("1")) {
            ra_welecome_cn.setChecked(true);
        }
        if (sharedPreferences.getString("language", null).equals("2")) {
            ra_welecome_en.setChecked(true);
        }
        if (sharedPreferences.getString("language", null).equals("3")) {
            ra_welecome_jp.setChecked(true);
        }
        ra_welecome_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("language", "0").commit();
                check_Language(getActivity(), sharedPreferences);
                Intent intent = new Intent(getActivity(), getActivity().getClass());
                intent.putExtra("state", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        ra_welecome_cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("language", "1").commit();
                check_Language(getActivity(), sharedPreferences);
                Intent intent = new Intent(getActivity(), getActivity().getClass());
                intent.putExtra("state", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        ra_welecome_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("language", "2").commit();
                check_Language(getActivity(), sharedPreferences);
                Intent intent = new Intent(getActivity(), getActivity().getClass());
                intent.putExtra("state", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        ra_welecome_jp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("language", "3").commit();
                check_Language(getActivity(), sharedPreferences);
                Intent intent = new Intent(getActivity(), getActivity().getClass());
                intent.putExtra("state", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void check_Language(Context context, SharedPreferences sharedPreferences) {
        int language = 0;
        try {
            //读取SharedPreferences数据，默认选中第一项
            language = Integer.valueOf(sharedPreferences.getString("language", null));
        } catch (Exception e) {
            sharedPreferences.edit().putString("language", "0");
            check_Language(context, sharedPreferences);
        }
        //根据读取到的数据，进行设置
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                //自动获取
                configuration.setLocale(Locale.getDefault());
                break;
            case 1:
                //中文
                configuration.setLocale(Locale.CHINESE);
                break;
            case 2:
                //英文
                configuration.setLocale(Locale.ENGLISH);
                break;
            case 3:
                //日文
                configuration.setLocale(Locale.JAPANESE);
                break;
            default:
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }


    private void initView(View view) {
        sharedPreferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        ra_welecome_auto = (RadioButton) view.findViewById(R.id.ra_welecome_auto);
        ra_welecome_cn = (RadioButton) view.findViewById(R.id.ra_welecome_china);
        ra_welecome_en = (RadioButton) view.findViewById(R.id.ra_welecome_english);
        ra_welecome_jp = (RadioButton) view.findViewById(R.id.ra_welecome_japanese);
    }
}
