package com.pm3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.student.myapplicationxxx.R;

public class Cover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        Runnable rab = new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(R.anim.push_in,R.anim.push_out);//轉跳主頁
            }
        };
        new Handler().postDelayed(rab, 3000);    // 5 sec 後轉跳主頁
    }

    @Override
    public void onBackPressed() {
        ;
    }
}
