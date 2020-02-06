package com.etang.nt_eink_launcher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 绘制电量的自定义View
 */

public class MyCircleView extends View {

    private Paint paint;
    private Paint rectPaint;
    private Paint txtPaint;
    public int progress;
    private boolean isChanger = false;

    public float getProgress() {
        return progress;
    }

    public void setProgress(int progress, boolean isChanger) {
        this.progress = progress;
        this.isChanger = isChanger;
        //重新绘制
        invalidate();
    }

    public MyCircleView(Context context) {
        this(context, null);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //抗锯齿
        paint.setAntiAlias(true);
        //设置线宽
        paint.setStrokeWidth(1);
        rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.FILL);
        int rectColor = Color.parseColor("#FF14A903");
        rectPaint.setColor(rectColor);
        //抗锯齿
        rectPaint.setAntiAlias(true);
        //设置线宽
        rectPaint.setStrokeWidth(1);

        txtPaint = new Paint();
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setTextSize(15);
        int txtColor = Color.parseColor("#000000");
        txtPaint.setColor(txtColor);
        //抗锯齿
        txtPaint.setAntiAlias(true);
        //设置线宽
        txtPaint.setStrokeWidth(5);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽高，默认200dp
        int defaultSize = dp2px(getContext(), 30);
        setMeasuredDimension(measureWidth(widthMeasureSpec, defaultSize), measureHeight(heightMeasureSpec, defaultSize));
    }

    private int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //算半径
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        //取半径为长和宽中最小的那个的(1/2)
        int radius = Math.min(width, height) / 2;
        //计算圆心(横坐标)
        int cx = getPaddingLeft() + width / 2;
        //计算圆心(纵坐标)
        int cy = getPaddingTop() + height / 2;

        drawCircle(canvas, cx, cy, radius, paint);

        drawRectCircle(canvas, cx, cy, radius, paint);

        drawInsideCircle(canvas, cx, cy, radius / 5 * 4, paint);

        drawCenterText(canvas, cx, cy, txtPaint);
    }

    /**
     * 画圆
     *
     * @param canvas
     * @param cx
     * @param cy
     * @param radius
     * @param paint
     */
    private void drawCircle(Canvas canvas, int cx, int cy, int radius, Paint paint) {
        int color = Color.parseColor("#CDCDCD");
        paint.setColor(color);
        canvas.drawCircle(cx, cy, radius, paint);
    }


    /**
     * 画扇形
     *
     * @param canvas
     * @param cx
     * @param cy
     * @param radius
     * @param paint
     */
    private void drawRectCircle(Canvas canvas, int cx, int cy, int radius, Paint paint) {
        int color = Color.parseColor("#ff0000");
        paint.setColor(color);
        RectF rectf = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(rectf, 0, 360 * progress / 100, true, paint);
    }


    /**
     * 画内圆
     *
     * @param canvas
     * @param cx
     * @param cy
     * @param radius
     * @param paint
     */
    private void drawInsideCircle(Canvas canvas, int cx, int cy, int radius, Paint paint) {
        int color = Color.parseColor("#FFFFFFFF");
        paint.setColor(color);
        canvas.drawCircle(cx, cy, radius, paint);
    }

    /**
     * 画文字
     *
     * @param canvas
     * @param cx
     * @param cy
     * @param txtPaint
     */
    private void drawCenterText(Canvas canvas, int cx, int cy, Paint txtPaint) {

        //计算baseline
        //距离 = 文字高度的一半 - 基线到文字底部的距离（也就是bottom）
        //= (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = cy + distance;
        if (isChanger) {
            canvas.drawText("" + progress + "+", cx, baseline, txtPaint);
        } else {
            canvas.drawText("" + progress + "", cx, baseline, txtPaint);
        }
    }


    /**
     * 测量宽
     * 1.精确模式（MeasureSpec.EXACTLY）
     * <p>
     * 在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
     * <p>
     * 2.最大模式（MeasureSpec.AT_MOST）
     * <p>
     * 这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
     * <p>
     * 3.未指定模式（MeasureSpec.UNSPECIFIED）
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int measureWidth(int measureSpec, int defaultSize) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(defaultSize);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        result = Math.max(result, getSuggestedMinimumWidth());

        return result;
    }

    /**
     * 测量高
     * 1.精确模式（MeasureSpec.EXACTLY）
     * <p>
     * 在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
     * <p>
     * 2.最大模式（MeasureSpec.AT_MOST）
     * <p>
     * 这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
     * <p>
     * 3.未指定模式（MeasureSpec.UNSPECIFIED）
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int measureHeight(int measureSpec, int defaultSize) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(defaultSize);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        result = Math.max(result, getSuggestedMinimumHeight());

        return result;
    }

}
