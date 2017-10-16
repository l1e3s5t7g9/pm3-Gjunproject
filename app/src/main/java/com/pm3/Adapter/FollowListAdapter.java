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
 */

public class FollowListAdapter extends BaseAdapter {

    private Follow activity;

    public FollowListAdapter(Follow activity){
        this.activity =activity;
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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_order_layout,null);

        TextView tvItemId=(TextView)v.findViewById(R.id.order_itemId);
        TextView tvItemGoods=(TextView)v.findViewById(R.id.order_itemGoods);
        TextView tvItemPrice=(TextView)v.findViewById(R.id.order_itemPrice);
        TextView tvItemQuantity=(TextView)v.findViewById(R.id.order_itemQuantity);
        TextView tvItemCommente=(TextView)v.findViewById(R.id.order_itemCommente);

        Goods goods = activity.getmGoodsList().get(position);
        Integer quantity = activity.getmQuantityList().get(position);
        String Commente = activity.getmCommenteList().get(position);

        tvItemId.setText("id");
        tvItemGoods.setText(goods.getgoods());
        tvItemPrice.setText(String.valueOf(goods.getprice())+"");
        tvItemQuantity.setText(String.valueOf(quantity));
        tvItemCommente.setText(Commente);
        return v;
    }
}

