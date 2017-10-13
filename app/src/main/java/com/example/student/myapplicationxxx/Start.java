package com.example.student.myapplicationxxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.myapplicationxxx.Class_Object.Goods;
import com.example.student.myapplicationxxx.Class_Object.Plan;
import com.example.student.myapplicationxxx.Fragment.GoodsFragment;
import com.example.student.myapplicationxxx.Fragment.PlanFragment;

import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity
        implements GoodsFragment.chouse,
        PlanFragment.chouse,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<Goods> mGoodsList = new ArrayList<>();

    private List<Plan> mPlanList = new ArrayList<>();

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
                DialogFragment dialog = new PlanFragment();
                dialog.show(getSupportFragmentManager(), "PlanFragment");
            }


        });

        initListView();

    }


    public List<Goods> getmGoodsList() {
        return mGoodsList;
    }

    public List<Plan> getmPlanList() {
        return mPlanList;
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new MyListAdapter(this));
        mListView.setOnItemClickListener(this);
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
    public void 新增專案確定(String 出貨店家,String 集散地點,int 時間) {
        處理訊息("收到確定 plan");
        Intent data = new Intent();
        data.putExtra("user_input", 出貨店家+集散地點+時間);
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public void 新增專案取消() {

    }

    @Override
    public void 處理訊息(String string) {
        Toast toast = Toast.makeText(Start.this, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        處理訊息("點選了第" + (position+1) + "項");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
