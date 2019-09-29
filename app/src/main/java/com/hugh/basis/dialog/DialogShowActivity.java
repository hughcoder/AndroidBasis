package com.hugh.basis.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hugh.basis.R;

/**
 * Created by chenyw on 2019-07-19.
 */
public class DialogShowActivity extends AppCompatActivity {

    private Button button;
    private SelfDialog selfDialog;
    private LinearLayout linearLayout;
    private TeenagerTipDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        linearLayout = findViewById(R.id.lv_dialogShow);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selfDialog!=null){selfDialog.dismiss();}
            }
        });
        button = findViewById(R.id.btn_show_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfDialog = new SelfDialog(DialogShowActivity.this);
                selfDialog.setTitle("提示");
                selfDialog.setMessage("确定退出应用?");
                selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Toast.makeText(DialogShowActivity.this, "点击了--确定--按钮", Toast.LENGTH_LONG).show();
                        selfDialog.dismiss();
                    }
                });
                selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(DialogShowActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();


            }
        });

        findViewById(R.id.btn_show_teenager_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new TeenagerTipDialog();
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
        });

        showDialog();

    }

    private void showDialog(){
        selfDialog = new SelfDialog(DialogShowActivity.this);
        selfDialog.setTitle("提示");
        selfDialog.setMessage("确定退出应用?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Toast.makeText(DialogShowActivity.this, "点击了--确定--按钮", Toast.LENGTH_LONG).show();
                selfDialog.dismiss();
            }
        });
        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                Toast.makeText(DialogShowActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
                selfDialog.dismiss();
            }
        });
        selfDialog.show();

        dialog = new TeenagerTipDialog();
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
}
