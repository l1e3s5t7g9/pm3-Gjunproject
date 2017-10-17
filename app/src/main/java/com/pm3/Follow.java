package com.pm3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Adapter.FollowListAdapter;
import com.pm3.Class_Object.Goods;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;

import java.util.ArrayList;
import java.util.List;

import static com.pm3.Tools.time.calendar2string;

public class Follow extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();
    private TextView tv_topic,tv_location,tv_deadline,tv_arrivaltime;
    private List<Order> mOrderList = new ArrayList<>();
    private List<Integer> mQuantityList = new ArrayList<>();
    private List<String> mCommenteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        Plan plan = (Plan) intent.getSerializableExtra("plan");
        tv_topic=(TextView)findViewById(R.id.名目);
        tv_location=(TextView)findViewById(R.id.地點);
        tv_deadline=(TextView)findViewById(R.id.截止時間);
        tv_arrivaltime=(TextView)findViewById(R.id.預計送達時間);
        tv_topic.setText(plan.getTopic());
        tv_location.setText(plan.getLocation());
        tv_deadline.setText(calendar2string(plan.getDeadline()));
        tv_arrivaltime.setText(calendar2string(plan.getArrivaltime()));



        mGoodsList=plan.getgoodsList();


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






    public List<Goods> getmGoodsList() {
        return mGoodsList;
    }
    public List<Integer> getmQuantityList() {
        return mQuantityList;
    }
    public List<String> getmCommenteList() {
        return mCommenteList;
    }

    private void creatList(){
        for(int i=0;i<mGoodsList.size();i++){
            mQuantityList.add(0);
            mCommenteList.add("");
        }
    }


    private void initListView() {
        mListView = (ListView) findViewById(R.id.FollowlistView);
        mListView.setAdapter(new FollowListAdapter(this));
        mListView.setOnItemClickListener(this);
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
