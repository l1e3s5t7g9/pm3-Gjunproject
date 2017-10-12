package com.example.student.myapplicationxxx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.student.myapplicationxxx.Network.Net;

public class MainActivity extends AppCompatActivity {

    ImageButton settinglink;
    ImageButton messagelink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = new Intent();
        in.setClass(MainActivity.this, Cover.class);
        startActivity(in);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        overridePendingTransition(R.anim.push_in,R.anim.push_out);

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("您要離開應用程式？")
                .setPositiveButton("繼續", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ;
                    }
                })
                .setNegativeButton("離開", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        overridePendingTransition(R.anim.push_in,R.anim.push_out);
                    }
                })
                .show();
    }
}
