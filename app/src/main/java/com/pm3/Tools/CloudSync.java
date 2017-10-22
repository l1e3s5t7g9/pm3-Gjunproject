package com.pm3.Tools;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pm3.A;
import com.pm3.Account.Info;
import com.pm3.Class_Object.Plan;
import com.pm3.MainActivity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by student on 2017/10/19.
 */

public class CloudSync {

    private MainActivity act;
    private A prm;
    private Gson gson;

    private FirebaseDatabase fdb;
    private DatabaseReference refpub;
    private DatabaseReference refpri;

    public CloudSync(final MainActivity act) {

        //初值設定
        this.act = act;
        this.prm = (A) act.getApplication();
        this.gson = new Gson();

        setUser(Info.gId);
    }

    private void setUser(String user) {

        //        new Thread() {
//            @Override
//            public void setUser() {
//                super.setUser();
//
//                while (Info.gId.equals(NO_SIGN) == true) {   //帳戶未取得
//                    delay(1000);
//                }

        //Firebase Realtime Database
        fdb = FirebaseDatabase.getInstance();

        refpub = fdb.getReference().child("public").child("plans");
        initRefPub(refpub);

        refpri = fdb.getReference().child("private").child(user).child("plans");
        initRefPri(refpri);

//            }
//        }.start();

    }

    public void publish() {

        //設定 realtime database 路徑
        setUser(Info.gId);

        //String -> Json
        String tmp = gson.toJson(prm.getAllPublicPlan());

        //AES 加密
        tmp = AES.encrypt(prm.AES_KEY, tmp);

        //Write to Realtime Database
        final String finalTmp = tmp;
        refpub.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                refpub.setValue(finalTmp);
                refpri.setValue(finalTmp);

                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }

    private void pullpub(String value) {

        //AES 解密
        if (value == null) {
            return;
        }
        value = AES.decrypt(prm.AES_KEY, value);

        List<Plan> tmp = null;
        //解Json
        try {
            Type typ = new TypeToken<List<Plan>>() {
            }.getType();
            tmp = gson.fromJson(value, typ);
        } catch (RuntimeException e) {
            ;
        }

        //顯示至ListView
        if (tmp != null) {
            prm.setAllPublicPlan(tmp);    //所有Plan更新至本機
        }
        act.initListView();

    }

    private void pullpri(String value) {
        ;
    }

    private void initRefPub(DatabaseReference ref) {

        //Add data change Listener
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                pullpub(value);    //拉取資料

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    private void initRefPri(DatabaseReference ref) {

        //Add data change Listener
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                pullpri(value);    //拉取資料

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }


}
