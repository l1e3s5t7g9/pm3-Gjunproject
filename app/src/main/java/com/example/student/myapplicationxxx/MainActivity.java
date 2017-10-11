package com.example.student.myapplicationxxx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton settinglink;
    ImageButton messagelink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        Runnable rab = new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);     //設定 Layout
            }
        };
        new Handler().postDelayed(rab, 5000);    // 5 sec 後轉跳主頁
//                runLoging();                               //執行登入程序



        settinglink = (ImageButton) findViewById(R.id.settinglink);
        messagelink = (ImageButton) findViewById(R.id.messagelink);
    }

    public void start(View v) {

        Intent in = new Intent();
        in.setClass(MainActivity.this, Start.class);
        startActivity(in);

    }
}
