package com.pm3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Info;
import com.pm3.Adapter.PlanListAdapter;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;

import java.util.ArrayList;
import java.util.List;

import static com.example.student.myapplicationxxx.R.id.itemlocation;
import static com.example.student.myapplicationxxx.R.id.itemtopic;
import static com.pm3.Tools.time.calendar2string;

public class PlanList extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    A prm;

    private List<Order> mPrivatePlanList = new ArrayList<>();
    private ListView mListView;
    PlanListAdapter PlanListAdapter;
    private TextView tv_topic, tv_location, tv_deadline, tv_arrivaltime,tv_合計;
    private TextView tv_發起人;
    private Button bt_截止;
    Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        prm = (A) getApplication();

        plan = (Plan) prm.getMyPublicPlan();




        tv_topic = (TextView) findViewById(itemtopic);
        tv_發起人 = (TextView) findViewById(R.id.item發起人);
        tv_location = (TextView) findViewById(itemlocation);
        tv_deadline = (TextView) findViewById(R.id.itemdeadline);
        tv_arrivaltime = (TextView) findViewById(R.id.itemarrivaltime);
        tv_合計 = (TextView) findViewById(R.id.合計);
        tv_topic.setText(plan.getTopic());
        tv_發起人.setText(plan.getOrganizer());
        tv_location.setText(plan.getLocation());
        tv_deadline.setText(calendar2string(plan.getDeadline()));
        tv_arrivaltime.setText(calendar2string(plan.getArrivaltime()));
        bt_截止=(Button)findViewById(R.id.Stop);
        bt_截止.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop();
            }
        });

        initListView();

    }

    public List<Order> getmPrivatePlanList() {
        return mPrivatePlanList;
    }



    public void alwaysrun(){
        mPrivatePlanList.clear();
        List<Order> lo = prm.getAllPublicOrders();
        lo = prm.filterOrders_Orgid(lo, Info.gId);
        for (Order o : lo) {
            mPrivatePlanList.add(o);
        }

        int 合計int = 0;
        for (int i = 0; i < mPrivatePlanList.size(); i++) {
            合計int = 合計int + mPrivatePlanList.get(i).get總價();
        }
        tv_合計.setText(合計int + "");
        PlanListAdapter = (PlanListAdapter) mListView.getAdapter();
        PlanListAdapter.notifyDataSetChanged();

        List<Plan> lp = prm.getAllPublicPlan();
        Plan p = prm.filterPlans_Orgid(lp,plan.getOrganizer_id());

        if(p.get截止()){
            bt_截止.setText("結束");
        }


    }



    private void initListView() {
        mListView = (ListView) findViewById(R.id.PlanlistView);
        mListView.setAdapter(new PlanListAdapter(this));
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);//設置長案效果
    }

    // ==========回上一頁==========
    public void back(View v) {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
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

    private Handler han;
    private Runnable runn;

    public void Stop() {
        String text=bt_截止.getText().toString();
        switch (text){
            case "截止":
                new AlertDialog.Builder(this)
                        .setTitle("您確定要截止這次的團購嗎?")
                        .setMessage("截止之後，其他人將不能更新或增加他們的訂單。")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                plan.set截止(true);
                                prm.addPublicPlan(plan);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ;
                            }
                        })
                        .show();
                break;
            case "結束":
                new AlertDialog.Builder(this)
                        .setTitle("您確定要結束這次的團購嗎?")
                        .setMessage("結束之後，這次的團購將會封存。\n您還是可以到紀錄查看")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                prm.finishMyPlan();
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
                break;
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        final int 這筆訂單 = position;
        final Order order=mPrivatePlanList.get(這筆訂單);
        new AlertDialog.Builder(this)
                .setMessage("這筆訂單繳費了？")
                .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        order.set繳費(true);
                        mPrivatePlanList.remove(這筆訂單);
                        mPrivatePlanList.add(order);
                        prm.updateMyOrders(plan,mPrivatePlanList);//更新雲上order資料
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        order.set繳費(false);
                    }
                })
                .show();


        PlanListAdapter.notifyDataSetChanged();//更新畫面
        return false;
    }

    private class runnUpdate implements Runnable {
        @Override
        public void run() {

            han.postDelayed(this, 500);     //定時
            alwaysrun();                //更新ListView

        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        han = new Handler();
        runn = new runnUpdate();
        han.post(runn);

    }

    @Override
    protected void onPause() {
        super.onPause();

        han.removeCallbacks(runn);  //停止更新ListView

    }

}
