package com.etang.nt_launcher.launcher.settings.uirefresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class UireFreshActivity extends AppCompatActivity {
    int number = 0;
    View uirefresh_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_uirefresh);
        uirefresh_id = (View) findViewById(R.id.uirefresh_id);
        DiyToast.showToast(UireFreshActivity.this, "刷新中", true);
        handler.post(timeRunnable);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 % 2 == 0) {
                uirefresh_id.setBackgroundColor(Color.BLACK);
            } else {
                uirefresh_id.setBackgroundColor(Color.WHITE);
            }
            handler.postDelayed(timeRunnable, 100);
        }
    };
    Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            number++;
            Message message = handler.obtainMessage();
            message.arg1 = number;
            if (number < 6) {
                handler.sendMessage(message);
            } else {
                handler.removeCallbacks(timeRunnable);
                finish();
            }
        }
    };
}
