package com.pm3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Info;
import com.pm3.Account.Sign;
import com.pm3.Adapter.OnlinListAdapter;
import com.pm3.Class_Object.Plan;
import com.pm3.Network.Net;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener{
    int 回傳plan=1;
    ImageButton settinglink;
    ImageButton messagelink;
    private List<Plan> mPlanList = new ArrayList<>();
    private ListView mListView;

    Sign gSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = new Intent();
        in.setClass(MainActivity.this, Cover.class);
        startActivity(in);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runLoging();

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
                .setPositiveButton("離開", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        overridePendingTransition(R.anim.push_in,R.anim.push_out);
                    }
                })
                .setNegativeButton("繼續", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ;
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



        // ====== Get Account Information ======
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Sign.RC_SIGN_IN) {

            // ====== 取得帳戶資訊並放置於 Info class ======
            gSign.Result(data);

        }else if (requestCode==回傳plan) {
            if (resultCode == RESULT_OK) {
                initListView();
                Plan plan = (Plan) data.getSerializableExtra("plan");

//                plan.addGoods(new Goods("asdnji","nnjo",121));
//                plan.addGoods(new Goods("asdn1253","nnj7777",12));
//                plan.addGoods(new Goods("asdn1250003","nnj770077",999));
                Gson gson = new Gson();
                String 上傳plan=gson.toJson(plan);

                Plan 下載plan=gson.fromJson(上傳plan,Plan.class);



                mPlanList.add(下載plan);
                OnlinListAdapter OnlinListAdapter = (OnlinListAdapter) mListView.getAdapter();
                OnlinListAdapter.notifyDataSetChanged();
            }else if(requestCode == RESULT_CANCELED){
                ;
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        處理訊息("點選了第" + (position+1) + "項");
        //new一個intent物件，並指定Activity切換的class
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Follow.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("plan",mPlanList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void 處理訊息(String string) {
        Toast toast = Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }






    public final void runOnUiTextView(final int id, final CharSequence cs) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(id)).setText(cs);
            }
        });

    }

    public final void runOnUiImageView(final int id, final Bitmap bm) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ImageView) findViewById(id)).setImageBitmap(bm);
            }
        });

    }

    public final void runLoging() {

        new Thread(new Runnable() {
            @Override
            public void run() {

//                // ====== 等待歡迎頁面結束 ======
//                while ((findViewById(R.id.TextViewName) == null)
//                        || (findViewById(R.id.ImageViewIcon) == null)
//                        ) {
//                    ;
//                }

                runOnUiTextView(R.id.TextViewName, "＜尚未連接網路＞");
                while (Net.NetCheck(getApplicationContext()) == false) {
                    ;
                }

                runOnUiTextView(R.id.TextViewName, "＜正在登入帳戶＞");
                // ==========取得 Account 資訊==========
                gSign = new Sign(MainActivity.this);   // 取得 Account 資訊
                while (Info.gLoginOk == false) {
                    ;
                }

                gSign.dispData();   // 準備暱稱及圖
                runOnUiTextView(R.id.TextViewName, Info.gDisplayNameNick);
                while (Info.gPhotoUrlBitmap == null) {
                    ;
                }
                runOnUiImageView(R.id.ImageViewIcon, Info.gPhotoUrlBitmap);

            }
        }).start();

    }




}
