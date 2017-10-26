package com.pm3.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pm3.Class_Object.Goods;
import com.example.student.myapplicationxxx.R;
import com.pm3.Start;

/**
 * Created by student on 2017/10/12.
 * 建立表單時，畫面上的listVuew會使用到
 */

public class MyListAdapter extends BaseAdapter {

    private Start activity;

    public MyListAdapter(Start activity) {
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return activity.getmGoodsList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View covnerview, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.listview_goods_layout, null);//格式ｘｍｌ

        TextView tvItemId = (TextView) v.findViewById(R.id.itemId);
        TextView tvItemStore = (TextView) v.findViewById(R.id.itemStore);
        TextView tvItemGoods = (TextView) v.findViewById(R.id.itemGoods);
        TextView tvItemPrice = (TextView) v.findViewById(R.id.itemPrice);

        Goods goods = activity.getmGoodsList().get(position);//goods資料來源

        //設定資料//
        tvItemId.setText("id");
        tvItemStore.setText(goods.getstore());
        tvItemGoods.setText(goods.getgoods());
        tvItemPrice.setText(String.valueOf(goods.getprice()));
        System.out.println(v.getBackground());
        return v;
    }
}

