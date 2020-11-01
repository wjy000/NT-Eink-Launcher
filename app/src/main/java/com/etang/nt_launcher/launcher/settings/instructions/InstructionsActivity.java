package com.etang.nt_launcher.launcher.settings.instructions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.launcher.MainActivity;
import com.etang.nt_launcher.tool.dialog.NewUserDialog;
import com.etang.nt_launcher.tool.toast.DiyToast;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ((Button) findViewById(R.id.btn_ins_cls)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyToast.showToast(getApplicationContext(), "正在尝试联网激活，请稍后......", true);
                NewUserDialog.dialog_show(InstructionsActivity.this, "设备激活（从说明书点击）：");
                finish();
            }
        });
    }
}
