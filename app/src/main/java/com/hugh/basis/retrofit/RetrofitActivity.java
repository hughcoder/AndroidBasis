package com.hugh.basis.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.basis.R;
import com.hugh.basis.retrofit.bean.Translation;
import com.hugh.basis.retrofit.getApi.GetRequest_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenyw on 2019-07-11.
 */
public class RetrofitActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        textView = findViewById(R.id.tv_text_retrofit);
        request();
    }

//    步骤1：添加Retrofit库的依赖
//    步骤2：创建 接收服务器返回数据 的类
//    步骤3：创建 用于描述网络请求 的接口

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://fy.iciba.com/")
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                if (response.body() != null) {
                    response.body().show();
                    textView.setText(response.body().getVerdor());
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.e("aaa", "请求失败");
            }
        });

    }
}
