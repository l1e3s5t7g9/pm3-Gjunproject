package com.pm3.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Goods;
import com.pm3.Follow;

/**
 * Created by student on 2017/10/12.
 * 在跟單畫面上的listVuew會使用到
 */

public class FollowListAdapter extends BaseAdapter {

    private Follow activity;

    public FollowListAdapter(Follow activity) {
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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_follow_layout, null);//格式ｘｍｌ

        TextView tvItemId = (TextView) v.findViewById(R.id.follow_itemId);
        TextView tvItemGoods = (TextView) v.findViewById(R.id.follow_itemGoods);
        TextView tvItemPrice = (TextView) v.findViewById(R.id.follow_itemPrice);
        TextView tvItemQuantity = (TextView) v.findViewById(R.id.follow_itemQuantity);
        TextView tvItemCommente = (TextView) v.findViewById(R.id.follow_itemCommente);

        Goods goods = activity.getmGoodsList().get(position);                              //goods資料來源
        Integer quantity = activity.getmQuantityList().get(position);                      //數量
        String Commente = activity.getmCommenteList().get(position);                       //註解


        //設定資料//
        tvItemId.setText("id");
        tvItemGoods.setText(goods.getgoods());
        tvItemPrice.setText(String.valueOf(goods.getprice()) + "");
        tvItemQuantity.setText(String.valueOf(quantity));
        tvItemCommente.setText(Commente);
        return v;
    }
}

