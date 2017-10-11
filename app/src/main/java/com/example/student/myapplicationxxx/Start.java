package com.example.student.myapplicationxxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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
}
