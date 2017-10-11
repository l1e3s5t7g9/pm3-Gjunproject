package com.example.student.myapplicationxxx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.student.myapplicationxxx.Network.Net;

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

        // ==========檢查Network==========
        if (Net.NetCheck(this) == false) {
            openWifi();                            // 提示開啟WIFI
        }

        settinglink = (ImageButton) findViewById(R.id.settinglink);
        messagelink = (ImageButton) findViewById(R.id.messagelink);
    }

    public void start(View v) {

        Intent in = new Intent();
        in.setClass(MainActivity.this, Start.class);
        startActivity(in);

    }

    public void openWifi() {

        //若wifi狀態為關閉則將它開啟
        WifiManager wfm = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        if (!wfm.isWifiEnabled()) {

            new AlertDialog.Builder(this)
                    .setMessage("應用程式要求開啟 Wi-Fi 功能。")
                    .setPositiveButton("允許", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(true);
                        }
                    })
                    .setNegativeButton("拒絕", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ;
                        }
                    })
                    .show();

        }

    }
}
