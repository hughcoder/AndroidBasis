package com.hugh.basis.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hugh.basis.R;
import com.hugh.basis.dialog.TeenagerTipDialog;

/**
 * Created by chenyw on 2019-08-30.
 */
public class TransParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题头
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_transparent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = win.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;//设置对话框在底部显示
        win.setAttributes(layoutParams);

        TeenagerTipDialog dialog = new TeenagerTipDialog();
        dialog.setOnclickListener(new TeenagerTipDialog.onDialogClickListener() {
            @Override
            public void onSettingClick() {
            }

            @Override
            public void onDialogOffClick() {
                dialog.dismiss();
            }
        });

        dialog.show(getSupportFragmentManager(),"custom");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.e("aaa","ccc");
    }
}
