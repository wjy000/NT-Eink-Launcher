package com.etang.nt_launcher.launcher.settings.instructions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.dialog.NewUserDialog;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class InstructionsActivity extends AppCompatActivity {

    TextView tv_back_text, tv_title_imagetext, tv_title_text;
    ImageView iv_title_back, iv_title_imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
        setContentView(R.layout.activity_instructions);
        initView();
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewUserDialog.dialog_show(InstructionsActivity.this, "查看说明书");
                finish();
            }
        });
        tv_title_text.setText("说明书");
        tv_title_imagetext.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        tv_back_text = (TextView) findViewById(R.id.tv_back_text);
        tv_title_imagetext = (TextView) findViewById(R.id.tv_title_imagetext);
        tv_title_text = (TextView) findViewById(R.id.tv_title_text);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_imagebutton = (ImageView) findViewById(R.id.iv_title_imagebutton);
    }
}
