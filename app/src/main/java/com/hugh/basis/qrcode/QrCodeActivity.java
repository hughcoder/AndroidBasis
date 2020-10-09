package com.hugh.basis.qrcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.hugh.basis.R;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2020/9/18.
 */
public class QrCodeActivity extends Activity {

    private ImageView mIvQrCode;
    private Bitmap mBitmap;
    private ImageView mIv22;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        mIvQrCode = findViewById(R.id.iv_img);
        mIv22 = findViewById(R.id.iv_img2);
        try {
            Bitmap logo = BitmapFactory.decodeResource(this.getResources(), R.drawable.module_commonpay_ic_qrcode_alipay);
            mBitmap = CodeUtils.createCode(this, "www.baidu.com");
            Bitmap qrCode = CodeUtils.createCode(this, "www.baidu.com", logo);
            mIvQrCode.setImageBitmap(qrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mIv22.setImageBitmap(mBitmap);
//        mIvQrCode.setImageBitmap(mBitmap);
    }
}
