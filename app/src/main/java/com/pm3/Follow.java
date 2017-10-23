package com.pm3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Info;
import com.pm3.Adapter.FollowListAdapter;
import com.pm3.Class_Object.Goods;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;
import com.pm3.Fragment.FollowFragment;

import java.util.ArrayList;
import java.util.List;

import static com.pm3.Tools.time.calendar2string;

public class Follow extends AppCompatActivity
        implements
        FollowFragment.chouse,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {


    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();
    private TextView tv_topic, tv_location, tv_deadline, tv_arrivaltime;
    private List<Order> mOrderList = new ArrayList<>();
    private List<Integer> mQuantityList = new ArrayList<>();
    private List<String> mCommenteList = new ArrayList<>();

    private GridLayout HorzontalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        Plan plan = (Plan) intent.getSerializableExtra("plan");
        tv_topic = (TextView) findViewById(R.id.名目);
        tv_location = (TextView) findViewById(R.id.地點);
        tv_deadline = (TextView) findViewById(R.id.截止時間);
        tv_arrivaltime = (TextView) findViewById(R.id.預計送達時間);
        tv_topic.setText(plan.getTopic());
        tv_location.setText(plan.getLocation());
        tv_deadline.setText(calendar2string(plan.getDeadline()));
        tv_arrivaltime.setText(calendar2string(plan.getArrivaltime()));


        mGoodsList = plan.getgoodsList();


        creatList();

        initListView();
        FollowListAdapter FollowListAdapter = (FollowListAdapter) mListView.getAdapter();
        FollowListAdapter.notifyDataSetChanged();
    }

    // ==========回上一頁==========
    public void cancel(View v) {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    //===========follow_plan==========

    public void follow_plan(View v){
        new AlertDialog.Builder(this)
                .setMessage("您確定要送出這些訂單？")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        plan.setordersList(mOrderList);
//                        prm.addPublicPlan(plan);
                        finish();
                        overridePendingTransition(R.anim.push_in, R.anim.push_out);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ;
                    }
                })
                .show();
    }


    public List<Goods> getmGoodsList() {
        return mGoodsList;
    }

    public List<Integer> getmQuantityList() {
        return mQuantityList;
    }

    public List<String> getmCommenteList() {
        return mCommenteList;
    }

    private void creatList() {
        HorzontalListView=(GridLayout)findViewById(R.id.HorzontalListView);
        for (int i = 0; i < mGoodsList.size(); i++) {
            Button button =new Button(this);
            button.setText(mGoodsList.get(i).getgoods()+"\n"+(mGoodsList.get(i).getprice()+"元"));
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialog = new FollowFragment(mGoodsList.get(finalI));
                    dialog.show(getSupportFragmentManager(), "FollowFragment");

                }
            });
            HorzontalListView.addView(button);
        }
    }

    public List<Order> getmOrderList() {
        return mOrderList;
    }


    //ListView 初始化設定
    private void initListView() {
        mListView = (ListView) findViewById(R.id.FollowlistView);
        mListView.setAdapter(new FollowListAdapter(this));
        mListView.setOnItemClickListener(this);
    }


    //點選ListView的項目
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void 新增訂單確定(Goods goods,int 數量,String 備註) {

        String subscriber= Info.gDisplayNameNick;
        Order order=new Order(subscriber,goods,數量,備註);
        mOrderList.add(order);
        FollowListAdapter FollowListAdapter = (FollowListAdapter) mListView.getAdapter();
        FollowListAdapter.notifyDataSetChanged();
    }

    @Override
    public void 新增訂單取消() {

    }

    @Override
    public void 處理訊息(String String) {
        Toast toast = Toast.makeText(Follow.this, String, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
