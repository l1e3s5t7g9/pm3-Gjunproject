package com.pm3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Sign;
import com.pm3.Adapter.OnlinListAdapter;
import com.pm3.Class_Object.Goods;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;
import com.pm3.Network.Net;
import com.pm3.Network.CloudSync;
import com.pm3.Tools.Notice;
import com.pm3.Tools.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private A prm;
    private Handler han = new Handler();
    private Runnable runnNotice;
    private Runnable runnNet;

    final int 回傳plan = 1;
    ImageButton settinglink;
    ImageButton messagelink;

    private OnlinListAdapter OnlinListAdapter;
    private ListView mListView;

    Button START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = new Intent();
        in.setClass(MainActivity.this, Cover.class);
        startActivity(in);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prm = (A) getApplication();

        prm.mCloudSync = new CloudSync(this);       //雲端自動同步物件

        taskLogin();                               //執行登入程序

        // ==========檢查Network==========
        if (Net.NetCheck(this) == false) {
            openWifi();                            // 提示開啟WIFI
        }

        settinglink = (ImageButton) findViewById(R.id.settinglink);
        messagelink = (ImageButton) findViewById(R.id.messagelink);
//        initListView();//初始設定
        check();//判定是否有自己的團購


    }

    public void check() {
        //===========判定是否有自己的團購=======

        START = (Button) findViewById(R.id.start);
        if (prm.getMyPublicPlan() == null) {    //沒有我的公開 Plan
            START.setText("發起團購");
        } else {                                //有我的公開 Plan
            START.setText("我的團購");
        }

    }

    public void start(View v) {
        Plan plan = prm.getMyPublicPlan();
        if (plan == null) {      //沒有我的公開 Plan
            Intent in = new Intent();
            in.setClass(MainActivity.this, Start.class);
            startActivityForResult(in, 回傳plan);
            overridePendingTransition(R.anim.push_in, R.anim.push_out);
        } else {                //有我的公開 Plan
            Intent data = new Intent();
            data.setClass(MainActivity.this, PlanList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("privatplan", plan);
            data.putExtras(bundle);
            startActivity(data);

            overridePendingTransition(R.anim.push_in, R.anim.push_out);
        }
    }

    public void settinglink(View v) {
        // ====== 新增商品 ======
        List<Goods> lg = new ArrayList<>();
        Goods g0 = (new Goods("50嵐", "珍珠奶茶", 50.0f));    // 商品0
        Goods g1 = (new Goods("50嵐", "茉莉綠茶", 51.0f));    // 商品1
        Goods g2 = (new Goods("50嵐", "四季春茶", 52.0f));    // 商品2
        Goods g3 = (new Goods("50嵐", "阿薩姆紅茶", 53.0f));   // 商品3
        lg.add(g0);
        lg.add(g1);
        lg.add(g2);
        lg.add(g3);

        // ====== 新增訂單 ======
        List<Order> lo = new ArrayList<>();
        lo.add(new Order("黃昊廷", g0, "半糖少冰"));    // 商品0
        lo.add(new Order("黃昊廷", g0, "微糖"));    // 商品1
        lo.add(new Order("黃昊廷", g1, "少冰"));    // 商品2
        lo.add(new Order("JOJO", g3, "熱飲"));    // 商品2

        // ====== 新增計畫 =====
        String location = "老地方拿飲料";
        Calendar deadline = time.settime(10);
        Calendar arrivaltime = time.settime(30);
        String topic = "50嵐湊兩百外送";
        List<Goods> goods = lg;
        List<Order> order = new ArrayList<>();
        prm.addPublicPlan(new Plan(location, deadline, arrivaltime, topic, goods, order));     // 計畫0

        // ====== 新增商品 ======
        lg = new ArrayList<>();
        g0 = (new Goods("COMEBUY", "珍珠奶茶", 54.0f));    // 商品0
        g1 = (new Goods("COMEBUY", "四季春茶", 55.0f));    // 商品1
        lg.add(g0);
        lg.add(g1);

        // ====== 新增訂單 ======
        lo = new ArrayList<>();
        lo.add(new Order("拎杯", g0, null));     // 訂購 計畫0 的 商品0
        lo.add(new Order("拎杯", g0, "少冰"));     // 訂購 計畫0 的 商品1
        lo.add(new Order("拎杯", g0, null));     // 訂購 計畫0 的 商品0
        lo.add(new Order("拎杯", g1, "少糖"));     // 訂購 計畫0 的 商品1
        lo.add(new Order("拎杯", g1, "去冰無糖"));     // 訂購 計畫1 的 商品0
        lo.add(new Order("拎杯", g1, null));     // 訂購 計畫2 的 商品1

        // ====== 新增計畫 ======
        location = "我的座位";
        deadline = time.settime(0);
        arrivaltime = time.settime(20);
        topic = "拎杯請喝COMEBUY";
        goods = lg;
        order = lo;
        prm.addPublicPlan(new Plan(location, deadline, arrivaltime, topic, goods, order));     // 計畫1


        // ====== 新增商品 ======
        lg = new ArrayList<>();
        lg.add(new Goods("茶湯會", "阿薩姆紅茶", 56.0f));   // 商品0
        lg.add(new Goods("清心福全", "茉莉綠茶", 57.0f));   // 商品1

        // ====== 新增計畫 ======
        location = "1F大廳櫃台";
        deadline = time.settime(60);
        arrivaltime = time.settime(90);
        topic = "清心&茶湯會預購從速";
        goods = lg;
        order = new ArrayList<>();
        prm.addPublicPlan(new Plan(location, deadline, arrivaltime, topic, goods, order));     // 計畫2


        //====== 我的訂單 ======
//        mPrivatePlanList.add(mPlanList.get(1));
        check();
        // ====== 更新畫面 ======
        OnlinListAdapter OnlinListAdapter = (OnlinListAdapter) mListView.getAdapter();
        OnlinListAdapter.notifyDataSetChanged();
    }

    public void messagelink(View v) {
        startActivity(new Intent(MainActivity.this, MessageActivity.class));
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
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
                        overridePendingTransition(R.anim.push_in, R.anim.push_out);
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

    //ListView 初始化設定
    public void initListView() {
        mListView = (ListView) findViewById(R.id.onlinelist);
        OnlinListAdapter = new OnlinListAdapter(this);
        mListView.setAdapter(OnlinListAdapter);
        mListView.setOnItemClickListener(this);
    }

    //回傳資料後...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Sign.RC_SIGN_IN:   //取得登入資訊
                prm.gSign.Result(data);     //更新資訊
                break;

            case 回傳plan:            //收Plan的資料
                if (resultCode == RESULT_OK) {
                    prm.addPublicPlan((Plan) data.getSerializableExtra("plan"));
                    check();
                } else if (requestCode == RESULT_CANCELED) {
                    ;
                }
                break;

        }

    }

    //點選ListView的項目
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        處理訊息("點選了第" + (position + 1) + "項");
        //new一個intent物件，並指定Activity切換的class
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Follow.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("plan", prm.getPlan(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //吐司方法
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


    public void runOnUiTextView(final int id, final CharSequence cs) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(id)).setText(cs);
            }
        });

    }

    public void runOnUiImageView(final int id, final Bitmap bm) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bm == null) {
                    ((ImageView) findViewById(R.id.ImageViewIcon)).setImageResource(R.drawable.user);
                } else {
                    ((ImageView) findViewById(id)).setImageBitmap(bm);
                }
            }
        });

    }

    public final void taskLogin() {

        han.post(new Runnable() {
            @Override
            public void run() {

                han.postDelayed(this, 3000);
                Net.statusUpdate(MainActivity.this);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        this.han.post(runnNet = new Runnable() {
//            @Override
//            public void run() {
//
//                han.postDelayed(this, 5000);
//
//
//            }
//        });


        //開始週期更替顯示
        this.han.post(runnNotice = new Runnable() {
            @Override
            public void run() {

                //開始下一個週期
                han.postDelayed(this, 300);

                //執行更替顯示
                Notice.showNext(MainActivity.this);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        han.removeCallbacks(runnNotice);

    }

}
