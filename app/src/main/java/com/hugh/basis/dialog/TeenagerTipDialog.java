package com.hugh.basis.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hugh.basis.R;

/**
 * Created by chenyw on 2019-07-19.
 */
public class TeenagerTipDialog extends DialogFragment {

    private LinearLayout mLayoutSetting;
    private Button btn_off;
    private onDialogClickListener mDialogClickListener;


    /**
     * 设置确定按钮监听
     *
     * @param onDialogClickListener
     */
    public void setOnclickListener(onDialogClickListener onDialogClickListener) {
        mDialogClickListener = onDialogClickListener;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MyDialog);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.dialog_teenager_tip, container, false);
        btn_off = rootView.findViewById(R.id.btn_dialog_off);
        mLayoutSetting = rootView.findViewById(R.id.teenage_setting);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onDialogOffClick();
                }
            }
        });

        mLayoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onSettingClick();
                }
            }
        });
        return rootView;
    }

    public interface onDialogClickListener {
        public void onSettingClick();

        public void onDialogOffClick();
    }
}
