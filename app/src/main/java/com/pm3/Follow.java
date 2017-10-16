package com.pm3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Goods;
import com.pm3.Class_Object.Plan;

import java.util.ArrayList;
import java.util.List;

public class Follow extends AppCompatActivity
        implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        Plan plan = (Plan) intent.getSerializableExtra("plan");

        mGoodsList=plan.getgoodsList();
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
