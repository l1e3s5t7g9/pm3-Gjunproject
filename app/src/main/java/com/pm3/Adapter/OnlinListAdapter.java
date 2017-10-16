package com.pm3.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Plan;
import com.pm3.MainActivity;

import static com.pm3.Tools.time.calendar2string;

/**
 * Created by TiGerTomb on 2017/10/13.
 */

public class OnlinListAdapter extends BaseAdapter {
    TextView tvItemId;
    TextView tvitemtopic;
    TextView tvitemlocation;
    TextView tvitemdeadline;
    TextView tvitemarrivaltime;

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

        View v = activity.getLayoutInflater().inflate(R.layout.listview_plan_layout,null);

        TextView tvItemId=(TextView)v.findViewById(R.id.itemId);
        TextView tvitemtopic=(TextView)v.findViewById(R.id.itemtopic);
        TextView tvitemlocation=(TextView)v.findViewById(R.id.itemlocation);
        TextView tvitemdeadline=(TextView)v.findViewById(R.id.itemdeadline);
        TextView tvitemarrivaltime=(TextView)v.findViewById(R.id.itemarrivaltime);

        Plan plan = activity.getmPlanList().get(position);

        tvItemId.setText("id");
        tvitemtopic.setText(plan.getTopic());
        tvitemlocation.setText(plan.getLocation());
        tvitemdeadline.setText(calendar2string(plan.getDeadline()));
        tvitemarrivaltime.setText(calendar2string(plan.getArrivaltime()));
        System.out.println(v.getBackground());
        return v;
    }

}