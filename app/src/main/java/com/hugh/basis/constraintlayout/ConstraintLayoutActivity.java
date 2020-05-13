package com.hugh.basis.constraintlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hugh.basis.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2019-08-06.
 */
public class ConstraintLayoutActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa","bb");
            }
        });
    }
}
