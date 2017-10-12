package com.example.student.myapplicationxxx;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.myapplicationxxx.Class_Object.Goods;
import com.example.student.myapplicationxxx.Fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity
    implements GoodsFragment.chouse,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener{

    private ListView mListView;
    private  static final String TAG ="Start";
    private List<Goods> mGoodsList =new ArrayList<>();
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

        initListView();

    }

    public List<Goods> getmGoodsList(){
        return mGoodsList;
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new MyListAdapter(this));
        mListView.setOnItemClickListener(this);
    }

    // ==========回上一頁==========
    public void cancel(View v)
    {
        finish();
        overridePendingTransition(R.anim.push_in,R.anim.push_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_in,R.anim.push_out);
    }

    public void ok(View v)
    {
        finish();
        overridePendingTransition(R.anim.push_in,R.anim.push_out);
    }

    @Override
    public void 處理確定(Goods goods) {
        ImageButton fab=(ImageButton) findViewById(R.id.fab);
        Toast.makeText(this, "收到確定 goods ="+goods, Toast.LENGTH_LONG).show();
        Log.d(TAG,"收到確定 goods ="+goods);
        mGoodsList.add(goods);
        MyListAdapter myListAdapter=(MyListAdapter) mListView.getAdapter();
        myListAdapter.notifyDataSetChanged();
    }

    @Override
    public void 處理取消(Goods goods) {
        ImageButton fab=(ImageButton) findViewById(R.id.fab);
        Toast.makeText(this, "處理取消 goods ="+goods, Toast.LENGTH_LONG).show();
        Log.d(TAG,"處理取消 goods ="+goods);
    }

    @Override
    public void 處理訊息(String String) {
        Toast.makeText(Start.this,String, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        Toast.makeText(this, "點選了第" + position + "項", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
