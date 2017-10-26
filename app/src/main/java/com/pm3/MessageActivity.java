package com.pm3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Account.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pm3.Tools.time.calendar2string;
import static com.pm3.Tools.time.nowtime;

public class MessageActivity extends AppCompatActivity {

    public final static String KEY_ID = "ID";
    public final static String KEY_TIM = "TIM";
    public final static String KEY_NICK = "NICK";
    public final static String KEY_MSG = "MSG";
    private final int MsgLimit = 100;

    private A prm;

    private EditText et;
    private List<Map<String, Object>> lm;
    private ListView lv;
    private SimpleAdapter sa;

    private Handler han;
    private Runnable runn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        TabHost tabHost = (TabHost) findViewById(R.id.TabHost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("訊息").setContent(R.id.tab1));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("2").setContent(R.id.tab2));

        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("3").setContent(R.id.tab3));

        prm = (A) getApplication();

        //初始化ListView
        initListView();

        //EditText
        et = (EditText) findViewById(R.id.editTextMessage);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {

                    sendMessage();

                }
                return false;
            }
        });

    }

    //回上一頁
    public void backclick(View view) {
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.push_in, R.anim.push_out);
    }

    private void initListView() {

        lm = new ArrayList<>();
        for (Map<String, Object> mso : prm.getAllPublicMsgs()) {
            lm.add(mso);
        }
        sa = new SimpleAdapter(
                this,
                lm,
                android.R.layout.simple_list_item_2,      // 內建
                new String[]{KEY_NICK, KEY_MSG},
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

    public void updateListView() {

        //重塞資料
        lm.clear();
        for (Map<String, Object> mso : prm.getAllPublicMsgs()) {
            lm.add(mso);
        }

        //通知Adapter更新ListView
        sa.notifyDataSetChanged();

    }

    private void sendMessage() {

        final String s = et.getText().toString();
        if (s.equals("") == false) {    //檢查空輸入

//                    msg.data.add(s);    //輸入一筆訊息
            Map<String, Object> hm = new HashMap<String, Object>() {
                {
                    put(KEY_ID, Info.gId);
                    put(KEY_TIM, calendar2string(nowtime()));
                    put(KEY_NICK, Info.gDisplayNameNick);
                    put(KEY_MSG, s);
                }
            };
            prm.addMyPublicMsg(hm);

            //限制總訊息量
            while (prm.getMsgSiz(prm.getAllPublicMsgs()) > MsgLimit) {
                prm.getAllPublicMsgs().remove(0);
            }

            //Clear input box
            et.setText("");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        han = new Handler();
        runn = new runnUpdate();
        han.post(runn);

    }

    @Override
    protected void onPause() {
        super.onPause();

        han.removeCallbacks(runn);  //停止更新ListView

    }


    private class runnUpdate implements Runnable {
        @Override
        public void run() {

            han.postDelayed(this, 500);     //定時
            updateListView();                 //更新Message ListView

        }
    }


}
