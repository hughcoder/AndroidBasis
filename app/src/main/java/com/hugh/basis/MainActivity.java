package com.hugh.basis;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hugh.basis.activities.FragmentActivity;
import com.hugh.basis.binder.UserManager;


import java.util.HashMap;

import java.util.concurrent.ThreadPoolExecutor;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private Button button2;
    private VelocityTracker mVelocityTracker;
    private LinearLayout linearLayout;
    private RelativeLayout button3;
    private Button button4;
    private View view;
    private ListView listView;
    public static String TAG = MainActivity.class.getSimpleName();
    private HashMap hashMap = new HashMap();
    public static int markNum = 100;
    private Button button5;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "--- onSaveInstanceState");
        outState.putString("extra_test", "test");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
        Log.e("onRestoreInstanceState", savedInstanceState.toString());
        String test = savedInstanceState.getString("extra_test");
        Log.e("onRestoreInstanceState", " test:" + test);
        //当Activity被重新创建后,onRestoreInstanceState会被调用
        //通过这个和onCreate方法来判断Activity是否被重建
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int width = button.getMeasuredWidth();
            int height = button.getMeasuredHeight();
            Log.e("a", width + "");
            Log.e("a", height + "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        button.post(new Runnable() {
            @Override
            public void run() {
                int width = button.getMeasuredWidth();
                int height = button.getMeasuredHeight();
                Log.e("b", width + "");
                Log.e("b", height + "");
            }
        });

        ViewTreeObserver observer = button.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = button.getMeasuredWidth();
                int height = button.getMeasuredHeight();
                Log.e("c", width + "");
                Log.e("c", height + "");
            }
        });

//      针对具体数值  如果相对应的View给出了具体dp就填入就行了
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(dip2px(this, 100), View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(dip2px(this, 100), View.MeasureSpec.EXACTLY);
        button.measure(widthMeasureSpec, heightMeasureSpec);

        // 如果是wrapContent的话
        //makeMeasureSpec()  传入测量大小 ，测量模式
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((a << 30) -a, View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((a << 30) -a, View.MeasureSpec.AT_MOST);
//        button.measure(widthMeasureSpec, heightMeasureSpec);
        Log.e("d", button.getMeasuredHeight() + "");
        Log.e("d", button.getMeasuredWidth() + "");
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        UserManager.uUserId = 2;

        Log.e(TAG, "uUserID " + UserManager.uUserId);

        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.e(TAG, "test:" + test);
        }

        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_goto);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent);
            }
        });

        button2 = findViewById(R.id.btn_goself);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_goReceive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReceiveActivity.class);
                intent.putExtra("send", "发送了消息");
                startActivity(intent);

            }
        });

        linearLayout = findViewById(R.id.ll_contain);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_CANCEL:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });

        button3 = findViewById(R.id.btn_View);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button3.scrollBy(300,200);
//                ObjectAnimator.ofFloat(button3,"translationX",0,100).setDuration(500).start();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button3.getLayoutParams();
                params.width += 100;
                params.leftMargin += 100;
                button3.requestLayout();
            }
        });

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                        .setIcon(R.mipmap.ic_launcher_round)
//                        .setCancelable(false)
//                        .setMessage("Error")
//                        .setTitle("Warring")
//                        .show();
                Intent intentFragment = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(intentFragment);
            }
        });

//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "newConfig.orientation:" + newConfig.orientation);
    }
}
