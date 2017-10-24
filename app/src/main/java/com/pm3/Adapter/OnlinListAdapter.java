package com.pm3.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.A;
import com.pm3.Class_Object.Plan;
import com.pm3.MainActivity;

import static com.pm3.Tools.time.calendar2string;

/**
 * Created by TiGerTomb on 2017/10/13.
 * 主畫面上的listVuew會使用到
 */

public class OnlinListAdapter extends BaseAdapter {

    private A prm;

    TextView tvItemId;
    TextView tvitemtopic;
    TextView tvitemlocation;
    TextView tvitemdeadline;
    TextView tvitemarrivaltime;

    private MainActivity activity;

    public OnlinListAdapter(MainActivity activity){
        this.activity =activity;
        this.prm = (A) this.activity.getApplication();
    }

    @Override
    public int getCount() {
        return prm.getAllPublicPlanSize();
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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_plan_layout,null);//格式ｘｍｌ

        TextView tvItemId=(TextView)v.findViewById(R.id.itemId);
        TextView tvitemtopic=(TextView)v.findViewById(R.id.itemtopic);
        TextView tvitem發起人=(TextView)v.findViewById(R.id.item發起人);
        TextView tvitemlocation=(TextView)v.findViewById(R.id.itemlocation);
        TextView tvitemdeadline=(TextView)v.findViewById(R.id.itemdeadline);
        TextView tvitemarrivaltime=(TextView)v.findViewById(R.id.itemarrivaltime);

        Plan plan = prm.getPlan(position);      //plan資料來源


        //設定資料//
        tvItemId.setText("id");
        tvitemtopic.setText(plan.getTopic());
        tvitem發起人.setText(plan.getOrganizer());
        tvitemlocation.setText(plan.getLocation());
        tvitemdeadline.setText(calendar2string(plan.getDeadline()));
        tvitemarrivaltime.setText(calendar2string(plan.getArrivaltime()));
        System.out.println(v.getBackground());
        return v;
    }

}