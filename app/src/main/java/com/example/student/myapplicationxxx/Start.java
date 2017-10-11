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
    public void cancel(View v)
    {
        finish();
    }
    public void ok(View v)
    {
        finish();
    }
}
