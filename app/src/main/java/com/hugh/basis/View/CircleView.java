package com.hugh.basis.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hugh.basis.R;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/12}
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);
        a.recycle();
        init();
    }

    private void init(){
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddiingLeft = getPaddingLeft();
        final int paddintRight = getPaddingRight();
        final int paddinTop = getPaddingTop();
        final int paddinBottom = getPaddingBottom();
        int width =getWidth()-paddiingLeft-paddintRight;
        int height = getHeight()-paddinTop-paddinBottom;
        int radius = Math.min(width,height)/2;
        canvas.drawCircle((paddiingLeft+width) >> 1, (height+paddinTop) >> 1,radius,mPaint);
    }
}
