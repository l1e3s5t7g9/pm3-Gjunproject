package com.pm3.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Order;
import com.pm3.PlanList;

/**
 * Created by TiGerTomb on 2017/10/13.
 * 主畫面上的listVuew會使用到
 */

public class PlanListAdapter extends BaseAdapter {
    TextView tvItemId;
    TextView tvitemtopic;
    TextView tvitemlocation;
    TextView tvitemdeadline;
    TextView tvitemarrivaltime;

    private PlanList activity;

    public PlanListAdapter(PlanList activity){
        this.activity =activity;
    }




    @Override
    public int getCount() {
        return activity.getmPrivatePlanList().size();
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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_order_layout,null);//格式ｘｍｌ

        TextView tvItemId = (TextView) v.findViewById(R.id.order_itemId);
        TextView tvItemSubscriber = (TextView) v.findViewById(R.id.order_itemSubscriber);
        TextView tvItemGoods = (TextView) v.findViewById(R.id.order_itemGoods);
        TextView tvItemPrice = (TextView) v.findViewById(R.id.order_itemPrice);
        TextView tvItemCommente = (TextView) v.findViewById(R.id.order_itemCommente);

        Order order = activity.getmPrivatePlanList().get(position);                        //plan資料來源
        String Subscriber=order.getSubscriber();
        String Goods=order.getGoods().getgoods();
        String Price =String.valueOf(order.getGoods().getprice());
        String Commente=order.getNotes();
        //設定資料//
        tvItemId.setText("id");
        tvItemSubscriber.setText(Subscriber);
        tvItemGoods.setText(Goods);
        tvItemPrice.setText(Price+"元");
        tvItemCommente.setText(Commente);
        return v;
    }

}