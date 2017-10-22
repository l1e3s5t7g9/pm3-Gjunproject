package com.pm3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Adapter.PlanListAdapter;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;

import java.util.ArrayList;
import java.util.List;

import static com.pm3.Tools.time.calendar2string;

public class PlanList extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private List<Order> mPrivatePlanList = new ArrayList<>();
    private ListView mListView;

    private TextView tv_topic, tv_location, tv_deadline, tv_arrivaltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        Intent intent = getIntent();
        Plan plan = (Plan) intent.getSerializableExtra("privatplan");

        tv_topic = (TextView) findViewById(R.id.名目);
        tv_location = (TextView) findViewById(R.id.地點);
        tv_deadline = (TextView) findViewById(R.id.截止時間);
        tv_arrivaltime = (TextView) findViewById(R.id.預計送達時間);
        tv_topic.setText(plan.getTopic());
        tv_location.setText(plan.getLocation());
        tv_deadline.setText(calendar2string(plan.getDeadline()));
        tv_arrivaltime.setText(calendar2string(plan.getArrivaltime()));

        initListView();

        mPrivatePlanList=plan.getordersList();
        PlanListAdapter PlanListAdapter = (PlanListAdapter) mListView.getAdapter();
        PlanListAdapter.notifyDataSetChanged();
    }

    public List<Order> getmPrivatePlanList() {
        return mPrivatePlanList;
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
}
