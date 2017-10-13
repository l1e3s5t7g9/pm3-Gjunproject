package com.example.student.myapplicationxxx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.student.myapplicationxxx.Class_Object.Plan;
import com.example.student.myapplicationxxx.Network.Net;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener{
    int 回傳plan=0;
    ImageButton settinglink;
    ImageButton messagelink;
    private List<Plan> mPlanList = new ArrayList<>();
    private ListView mListView;


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
        startActivityForResult(in,回傳plan);
        overridePendingTransition(R.anim.push_in,R.anim.push_out);
    }
    public void  settinglink(View v){

    }

    public void  messagelink(View v){

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
    private void initListView() {
        mListView = (ListView) findViewById(R.id.onlinelist);
        mListView.setAdapter(new OnlinListAdapter(this));
        mListView.setOnItemClickListener(this);
    }
    public List<Plan> getmPlanList() {
        return mPlanList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==回傳plan) {
            if (resultCode == RESULT_OK) {
                initListView();
                Plan plan = (Plan) data.getSerializableExtra("plan");
                mPlanList.add(plan);
                OnlinListAdapter OnlinListAdapter = (OnlinListAdapter) mListView.getAdapter();
                OnlinListAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
