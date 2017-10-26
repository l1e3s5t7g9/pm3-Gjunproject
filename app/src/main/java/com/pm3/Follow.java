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

import static com.example.student.myapplicationxxx.R.id.itemlocation;
import static com.example.student.myapplicationxxx.R.id.itemtopic;
import static com.pm3.Tools.time.calendar2string;

public class Follow extends AppCompatActivity
        implements
        FollowFragment.chouse,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private A prm;
    FollowListAdapter FollowListAdapter;
    Plan plan;
    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();
    private TextView tv_topic, tv_location, tv_deadline, tv_arrivaltime;
    private TextView tv_發起人;
    private Button bt_follow_plan;
    private List<Order> mOrderList = new ArrayList<>();
    private List<Integer> mQuantityList = new ArrayList<>();
    private List<String> mCommenteList = new ArrayList<>();

    private GridLayout HorzontalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);


        prm = (A) getApplication();


        Intent intent = this.getIntent();
        initplanset();//建置版面
        //取得傳遞過來的資料
        plan = (Plan) intent.getSerializableExtra("plan");
        bt_follow_plan = (Button) findViewById(R.id.follow_plan);

        //發配資料
        tv_topic.setText(plan.getTopic());
        tv_發起人.setText(plan.getOrganizer());
        tv_location.setText(plan.getLocation());
        tv_deadline.setText(calendar2string(plan.getDeadline()));
        tv_arrivaltime.setText(calendar2string(plan.getArrivaltime()));

        mGoodsList = plan.getgoodsList();

        creatList();

//        mOrderList = prm.filterOrders_Subid(prm.getAllPublicPlanOrders(plan.getOrganizer_id()));//顯示我在這個plan的orderlist
        //顯示我在這個plan的orderlist
        List<Order> lo = prm.getAllPublicOrders();  //取得所有Order
        String plan_id = plan.getOrganizer_id();     //取得這個Plan的ID (發起者ID)
        lo = prm.filterOrders_Orgid(lo, plan_id);   //過濾條件 -> 發起者 = plan_id
        mOrderList = prm.filterOrders_Subid(lo, Info.gId);  //過濾條件 -> 下訂者 = 本機登入ID

        //按鈕文字設定
        switch (mOrderList.size()) {
            case (0):
                bt_follow_plan.setText("跟單");
                break;
            default:
                bt_follow_plan.setText("更新");
                break;
        }
        initListView();
        FollowListAdapter = (FollowListAdapter) mListView.getAdapter();
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

    public void follow_plan(View v) {
        String msg = null;
        switch (bt_follow_plan.getText().toString()) {
            case ("跟單"):
                msg = "您確定要送出這些訂單？";
                break;
            case ("更新"):
                msg = "您確定要更新成這些訂單？";
                break;
        }
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prm.removeMyOrders(plan);   //命令刪除這個Plan中的所有我的Order
                        prm.addPublicOrder(mOrderList);     //我的修改後Order重新上傳
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
        HorzontalListView = (GridLayout) findViewById(R.id.HorzontalListView);
        for (int i = 0; i < mGoodsList.size(); i++) {
            Button button = new Button(this);
            button.setText(mGoodsList.get(i).getgoods() + "\n" + (mGoodsList.get(i).getprice() + "元"));
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
        mListView.setOnItemLongClickListener(this);//設置長案效果
    }

    private void initplanset() {
        tv_topic = (TextView) findViewById(itemtopic);
        tv_發起人 = (TextView) findViewById(R.id.item發起人);
        tv_location = (TextView) findViewById(itemlocation);
        tv_deadline = (TextView) findViewById(R.id.itemdeadline);
        tv_arrivaltime = (TextView) findViewById(R.id.itemarrivaltime);
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int 這筆訂單 = position;
        new AlertDialog.Builder(this)
                .setMessage("您確定要刪除這訂單？")
                .setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mOrderList.remove(這筆訂單);

                        FollowListAdapter.notifyDataSetChanged();//更新畫面
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
    public void 新增訂單確定(Goods goods, int 數量, String 備註) {

        String 發起者ID = plan.getOrganizer_id();
        Order order = new Order(發起者ID, goods, 數量, 備註);
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
