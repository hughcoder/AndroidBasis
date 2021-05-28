package com.hugh.basis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Environment;
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

import com.hugh.basis.MediaPlay.MediaPlayActivity;
import com.hugh.basis.activities.BindServiceActivity;
import com.hugh.basis.activities.FragmentActivity;
import com.hugh.basis.activities.ListActivity;
import com.hugh.basis.activities.TransParentActivity;
import com.hugh.basis.animate.AnimationActivity;
import com.hugh.basis.binder.UserManager;
import com.hugh.basis.camera2face.Camera2FaceActivity;
import com.hugh.basis.constraintlayout.ConstraintLayoutActivity;
import com.hugh.basis.coordinatorLayoutPage.FoldActivity;
import com.hugh.basis.dialog.DialogShowActivity;
import com.hugh.basis.eventbus.EventActivity1;
import com.hugh.basis.exoplayer.ExoPlayerActivity;
import com.hugh.basis.eyeshield.EyeProtectActivity;
import com.hugh.basis.footer.HeaderAndFooterActivity;
import com.hugh.basis.highOrderUI.UiActivity;
import com.hugh.basis.hook.HookTestActivity;
import com.hugh.basis.ijkplayer.PlayActivity;
import com.hugh.basis.leakcanary.LeakActivity;
import com.hugh.basis.puzzleGame.PuzzleActivity;
import com.hugh.basis.qrcode.QrCodeActivity;
import com.hugh.basis.record.RecordActivity;
import com.hugh.basis.retrofit.RetrofitActivity;
import com.hugh.basis.rvlooper.LooperActivity;
import com.hugh.basis.scrolltest.ScrollTestActivity;
import com.hugh.basis.scrolltest.scroll3.ScrollTest3Activity;
import com.hugh.basis.scrolltest.test2.ScrollTest2Activity;
import com.hugh.basis.services.TestOneService;
import com.hugh.basis.third.greendao.NoteActivity;
import com.hugh.basis.thread.ThreadTestAcitivity;
import com.hugh.basis.timer.TimerActivity;
import com.hugh.basis.videoView.VideoViewActivity;
import com.hugh.basis.viewpager.ViewPagerActivity;
import com.hugh.basis.webView.WebViewActivity;
import com.tencent.tinker.lib.tinker.TinkerInstaller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
    private int ppp;
    private TextView mTvMsg;
    private TextView show;
    private Activity mActivity;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "--- onSaveInstanceState");
        outState.putString("extra_test", "test");
        mActivity = MainActivity.this;
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
//            Log.e("a", width + "");
//            Log.e("a", height + "");
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
//                Log.e("b", width + "");
//                Log.e("b", height + "");
            }
        });
        show = findViewById(R.id.tv_show);

        List a = new ArrayList();
        a.size();

        ViewTreeObserver observer = button.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = button.getMeasuredWidth();
                int height = button.getMeasuredHeight();
//                Log.e("c", width + "");
//                Log.e("c", height + "");
            }
        });

        findViewById(R.id.btn_goService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BindServiceActivity.class);
                startActivity(intent);
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
//        Log.e("d", button.getMeasuredHeight() + "");
//        Log.e("d", button.getMeasuredWidth() + "");
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent");
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

        // 连续启动Service
        //多次startService不会重复执行onCreate回调，但每次都会执行onStartCommand回调。
        Intent intentOne = new Intent(this, TestOneService.class);
        startService(intentOne);
        Intent intentTwo = new Intent(this, TestOneService.class);
        startService(intentTwo);


        UserManager.uUserId = 2;

        Log.e(TAG, "uUserID " + UserManager.uUserId);


        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.e(TAG, "test:" + test);
        }

        setContentView(R.layout.activity_main);
        mTvMsg = findViewById(R.id.tv_hello_world);
        button = findViewById(R.id.btn_goto);

        findViewById(R.id.btn_goto_puzzle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Intent intent = new Intent(MainActivity.this, AnimationActivity.class);

                startActivity(intent);
            }
        });

        findViewById(R.id.btn_goList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
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
        findViewById(R.id.btn_transparent_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransParentActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btn_leakcanary_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeakActivity.class);
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

        findViewById(R.id.btn_goEventBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFragment = new Intent(MainActivity.this, EventActivity1.class);
                startActivity(intentFragment);
            }
        });

        findViewById(R.id.btn_goWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFragment = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intentFragment);
            }
        });

        findViewById(R.id.btn_goPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFragment = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intentFragment);
            }
        });

        findViewById(R.id.btn_goMediaPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, MediaPlayActivity.class);
                startActivity(intent2);
            }
        });
        findViewById(R.id.btn_goVideoView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, VideoViewActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_goretrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, RetrofitActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_goTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_goviewPager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_goDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, DialogShowActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_goconstraintLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, ConstraintLayoutActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_looper_rv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, LooperActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_header_footer_rv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, HeaderAndFooterActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.btn_exoplayer_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, ExoPlayerActivity.class);
                startActivity(intent2);
            }
        });

        findViewById(R.id.tv_load_patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
            }
        });

        findViewById(R.id.tv_text_patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setText("测试结果：" + "bug");
            }
        });

        findViewById(R.id.tv_go_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecordActivity.class));
            }
        });

        findViewById(R.id.tv_go_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UiActivity.class));
            }
        });

        findViewById(R.id.tv_go_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HookTestActivity.class));
            }
        });

        if (isApkInDebug(this)) {
            mTvMsg.setText("这是debug打开状态");
        } else {
            mTvMsg.setText("这是debug关闭状态");
        }

        findViewById(R.id.tv_go_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThreadTestAcitivity.class));
            }
        });

        findViewById(R.id.tv_go_protect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EyeProtectActivity.class));
            }
        });

        findViewById(R.id.tv_go_camera2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Camera2FaceActivity.class));
            }
        });

        findViewById(R.id.tv_go_greendao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });

        findViewById(R.id.btn_goto_ffmpeg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        goCCMainActivity();

        findViewById(R.id.tv_go_fold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoldActivity.class));
            }
        });


        findViewById(R.id.tv_go_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
            }
        });
//        MessageQueue.IdleHandler ideHandler =new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                return false;
//            }
//        };
//
//        Looper.myQueue().addIdleHandler(ideHandler);

//        HandlerThread handlerThread = new HandlerThread("A-Thread");
//        handlerThread.start();
//        Handler handler = new Handler(handlerThread.getLooper());

        findViewById(R.id.btn_goto_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ScrollTestActivity.class));
                startActivity(new Intent(MainActivity.this, ScrollTest3Activity.class));
            }
        });

        findViewById(R.id.btn_goto_scroll2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollTest2Activity.class));
            }
        });
    }


    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    //1 创建 client对象
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();

    // 同步
    public void synRequest() {
        //2 创建request 对象
        Request request = new Request.Builder().url("www.baidu.com").get().build();
        //3 代表一个实际的okhttp请求
        Call call = client.newCall(request);
//        call.cancel();
        //4返回response 第四步可以分为同步和异步
        try {
            Response response = call.execute();
            Log.e("aaa", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyRequest() {
        Request request = new Request.Builder().url("http://www.baidu.com").get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() { //enqueue这个方法会开启一个新的线程
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aaa", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("aaa", response.body().string());
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "newConfig.orientation:" + newConfig.orientation);
    }
}
