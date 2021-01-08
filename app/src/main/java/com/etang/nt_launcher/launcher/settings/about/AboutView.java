package com.etang.nt_launcher.launcher.settings.about;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class AboutView extends View {

    private int height = 0;
    private int width = 0;
    private int radius = 0;

    public AboutView(Context context) {
        super(context);
    }

    public AboutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        height = canvas.getHeight() / 2;
        width = canvas.getWidth() / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);// 画笔线条
        paint.setStrokeWidth(3);// 画笔宽
        radius = height - 3;
        for (int i = 0; i < 60; i++) {
            canvas.save();// 保存画布
            canvas.rotate(i * 6, width, height);// 旋转画布
            // 绘制线条
            canvas.drawLine(width, height - radius, width,
                    height - radius + 10, paint);
            canvas.restore();// 恢复画布
        }
//        invalidate();
    }
}
