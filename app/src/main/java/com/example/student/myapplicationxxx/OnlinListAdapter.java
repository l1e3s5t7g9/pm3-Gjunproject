package com.example.student.myapplicationxxx;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.myapplicationxxx.Class_Object.Plan;

/**
 * Created by TiGerTomb on 2017/10/13.
 */

public class OnlinListAdapter extends BaseAdapter {

    private MainActivity activity;

    public OnlinListAdapter(MainActivity activity){
        this.activity =activity;
    }




    @Override
    public int getCount() {
        return activity.getmPlanList().size();
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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_layout,null);

        TextView tvItemId=(TextView)v.findViewById(R.id.itemId);
        TextView tvItemStore=(TextView)v.findViewById(R.id.itemStore);
        TextView tvItemGoods=(TextView)v.findViewById(R.id.itemGoods);
        TextView tvItemPrice=(TextView)v.findViewById(R.id.itemPrice);

        Plan plan = activity.getmPlanList().get(position);

        tvItemId.setText("id");
        tvItemStore.setText("123");
        tvItemGoods.setText("123");
        tvItemPrice.setText(String.valueOf(123));
        System.out.println(v.getBackground());
        return v;
    }
}

