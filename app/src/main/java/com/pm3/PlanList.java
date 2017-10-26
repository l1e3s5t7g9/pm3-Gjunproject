package com.pm3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        AdapterView.OnItemClickListener {

    A prm;

    private List<Order> mPrivatePlanList = new ArrayList<>();
    private ListView mListView;
    PlanListAdapter PlanListAdapter;
    private TextView tv_topic, tv_location, tv_deadline, tv_arrivaltime,tv_合計;
    private TextView tv_發起人;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        prm = (A) getApplication();

        Plan plan = (Plan) prm.getMyPublicPlan();




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
    }



    private void initListView() {
        mListView = (ListView) findViewById(R.id.PlanlistView);
        mListView.setAdapter(new PlanListAdapter(this));
        mListView.setOnItemClickListener(this);
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
