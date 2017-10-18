package com.pm3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.student.myapplicationxxx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pm3.Account.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    class MSG {
        public ArrayList<Map<String, Object>> data = new ArrayList<>();
    }

    private final int MsgLimit = 5;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private MSG msg;
    private Gson gson;

    private EditText et;
    private ListView lv;
    //    private ArrayAdapter aa;
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        msg = new MSG();
        gson = new Gson();

        //EditText
        et = (EditText) findViewById(R.id.editTextMessage);

        //Firebase Realtime Database
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("message");
        //Add data change Listener
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                //解Json
                try {
                    msg = gson.fromJson(value, MSG.class);
                } catch (RuntimeException e) {
                    ;
                }

                //顯示至ListView
//                als.add(value);                 //Add new data to ListView
//                aa.notifyDataSetChanged();      //Update
                initListView();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                        Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    public void onClickMsg(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.buttonSend:

                final String s = et.getText().toString();
                if (s.equals("") == false) {    //檢查空輸入

//                    msg.data.add(s);    //輸入一筆訊息
                    msg.data.add(new HashMap<String, Object>() {
                        {
                            put("NICK", Info.gDisplayNameNick);
                            put("MSG", s);
                        }
                    });

                    while (msg.data.size() > MsgLimit) {   //限制總訊息量
                        msg.data.remove(0);
                    }


                    Gson gson = new Gson();
                    String js = gson.toJson(msg);

                    ref.setValue(js);                    //Write to Realtime Database
                    et.setText("");
                }

                break;
        }
    }

    private void initListView() {

        sa = new SimpleAdapter(
                this,
                msg.data,
                android.R.layout.simple_list_item_2,      // 內建
                new String[]{"NICK", "MSG"},
                new int[]{android.R.id.text1, android.R.id.text2}                            // 內建
        );
        lv = (ListView) findViewById(R.id.listViewMessage);
        lv.setAdapter(sa);

//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                als.remove(i);
//                aa.notifyDataSetChanged();
//                return true;
//            }
//        });
//        lv.setAdapter(aa);
    }

}
