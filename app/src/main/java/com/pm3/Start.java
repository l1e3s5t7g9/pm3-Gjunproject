package com.pm3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Info;
import com.pm3.Adapter.MyListAdapter;
import com.pm3.Class_Object.Goods;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;
import com.pm3.Fragment.GoodsFragment;
import com.pm3.Fragment.PlanFragment;
import com.pm3.Tools.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.student.myapplicationxxx.R.id.itemlocation;
import static com.example.student.myapplicationxxx.R.id.itemtopic;

public class Start extends AppCompatActivity
        implements GoodsFragment.chouse,
        PlanFragment.chouse,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener,
        DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener {

    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();
    private List<Plan> mPlanList = new ArrayList<>();
    private String 發起名目;
    private String 集散地點;
    private Calendar 截止時間;
    private Calendar 預計送達時間;
    private MyListAdapter myListAdapter;
    private ImageButton bt_settinglink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new GoodsFragment();
                dialog.show(getSupportFragmentManager(), "GoodsFragment");
            }


        });
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoodsList.size() > 0) {
//                    DialogFragment dialog = new PlanFragment();
//                    dialog.show(getSupportFragmentManager(), "PlanFragment");
                    if (發起名目 != null || 集散地點 != null || 截止時間 != null || 預計送達時間 != null) {

                        發起名目 = tv_topic.getText().toString();
                        集散地點 = tv_location.getText().toString();

                        Plan plan = 輸入資料toNewPlan();
                        Intent data = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("plan", plan);

                        data.putExtras(bundle);
                        setResult(RESULT_OK, data);
                        finish();
                    } else {
                        處理訊息("上面資料不完整");
                    }


                } else {
                    處理訊息("至少要有一筆商品");
                }

            }


        });

        initListView();
        myListAdapter = (MyListAdapter) mListView.getAdapter();
        initplanset();

    }


    public List<Goods> getmGoodsList() {
        return mGoodsList;
    }

    //ListView 初始化設定
    private void initListView() {
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new MyListAdapter(this));
        mListView.setOnItemClickListener(this);
    }

    // ==========回上一頁==========
    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }


    @Override
    public void 新增商品確定(Goods goods) {
        處理訊息("收到確定 goods =" + goods.getgoods());
        mGoodsList.add(goods);
        MyListAdapter myListAdapter = (MyListAdapter) mListView.getAdapter();
        myListAdapter.notifyDataSetChanged();
    }

    @Override
    public void 新增商品取消() {
        ;
    }

    @Override
    public void 新增專案確定(String 發起名目, String 集散地點, int 截止時間, int 預計送達時間) {
//        處理訊息("收到確定 plan");
//        Plan plan = 輸入資料toNewPlan(發起名目, 集散地點, 截止時間, 預計送達時間);
//        Intent data = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("plan", plan);
//
//        data.putExtras(bundle);
//        setResult(RESULT_OK, data);
//        finish();

    }

    @Override
    public void 新增專案取消() {

    }

    @Override
    public void 處理訊息(String string) {
//        Toast toast = Toast.makeText(Start.this, string, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
    }

    //點選ListView的項目
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        final int 商品編號 = position;
        new AlertDialog.Builder(this)
                .setMessage("您確定要刪除這筆商品？")
                .setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mGoodsList.remove(商品編號);
                        myListAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ;
                    }
                })
                .show();
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //將資料存入Plan
    private Plan 輸入資料toNewPlan() {
        String location = 集散地點;
        Calendar deadline = 截止時間;
        Calendar arrivaltime = 預計送達時間;
        String topic = 發起名目;
        List<Goods> goods = mGoodsList;
        List<Order> order = new ArrayList<>();


        return new Plan(location, deadline, arrivaltime, topic, goods, order);
    }


    private TextView tv_topic;
    private TextView tv_發起人;
    private TextView tv_location;
    private TextView et_deadline;
    private TextView et_arrivaltime;

    private void initplanset() {
        tv_topic = (TextView) findViewById(itemtopic);
        tv_發起人 = (TextView) findViewById(R.id.item發起人);
        tv_location = (TextView) findViewById(itemlocation);
        et_deadline = (TextView) findViewById(R.id.itemdeadline);
        et_arrivaltime = (TextView) findViewById(R.id.itemarrivaltime);
        View[] v={tv_topic,tv_location,et_deadline,et_arrivaltime};
        for (int i=0;i<v.length;i++){
            v[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick2(view);
                }
            });
        }
        tv_發起人.setText(Info.gDisplayNameNick);
        sb_deadline = new StringBuffer();
        sb_arrivaltime = new StringBuffer();
    }

    private Context context = this;
    private StringBuffer sb_deadline;
    private StringBuffer sb_arrivaltime;
    private int year, month, day, hour, minute;

    public void onClick2(View v) {

        switch (v.getId()) {
            case itemtopic:
            case R.id.itemlocation:
                AlertDialog.Builder builder0 = new AlertDialog.Builder(this);
                String Title = null;
                switch (v.getId()){
                    case itemtopic:
                        Title="想一個吸引大家訂購的標題吧!";

                        break;
                    case R.id.itemlocation:
                        Title="之後大家要到哪裡取貨呢?";
                        break;
                }
                builder0.setTitle(Title);
                final EditText input = new EditText(this);
//                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder0.setView(input);
                final TextView tv=(TextView)v;
                builder0.setPositiveButton("設置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv.setText(input.getText().toString());
                    }
                });
                builder0.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder0.setCancelable(false);// 设置点击屏幕Dialog不消失
                builder0.show();
                break;
            case R.id.itemdeadline:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("設置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (sb_deadline.length() > 0) { //清除上次记录的日期
                            sb_deadline.delete(0, sb_deadline.length());
                        }
                        et_deadline.setText(sb_deadline.append(String.valueOf(hour)).append("時").append(String.valueOf(minute)).append("分"));
                        截止時間 = time.settime(hour, minute);

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true); //设置24小时制
                timePicker.setOnTimeChangedListener(this);
                dialog.setView(dialogView);
                dialog.show();
                break;
            case R.id.itemarrivaltime:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("設置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (sb_arrivaltime.length() > 0) { //清除上次记录的日期
                            sb_arrivaltime.delete(0, sb_arrivaltime.length());
                        }
                        et_arrivaltime.setText(sb_arrivaltime.append(String.valueOf(hour)).append("時").append(String.valueOf(minute)).append("分"));
                        預計送達時間 = time.settime(hour, minute);
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker2 = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker2.setCurrentHour(hour);
                timePicker2.setCurrentMinute(minute);
                timePicker2.setIs24HourView(true); //设置24小时制
                timePicker2.setOnTimeChangedListener(this);
                dialog2.setView(dialogView2);
                dialog2.show();
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }


}
