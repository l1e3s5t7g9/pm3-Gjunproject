package com.pm3.Network;

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
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;
import com.pm3.MainActivity;
import com.pm3.Tools.AES;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pm3.MessageActivity.KEY_ID;

/**
 * Created by student on 2017/10/19.
 */

public class CloudSync {

    private final static String C_PUB = "public";
    private final static String C_PRI = "private";

    private final static String C_PLN = "plan";
    private final static String C_ODR = "order";
    private final static String C_MSG = "message";
    private static String onlineId;

    private MainActivity act;
    private A prm;
    private Gson gson;

    private FirebaseDatabase fdb;
    private DatabaseReference refpub;
    private DatabaseReference refpri;

    private class MSGPCK {
        List<Map<String, Object>> msgs = new ArrayList<>();
    }

    private MSGPCK msgpck = new MSGPCK();

    private class ORDPCK {
        List<Order> ords = new ArrayList<>();
    }

    private ORDPCK ordpck = new ORDPCK();


    public CloudSync(final MainActivity act) {

        //初值設定
        this.act = act;
        this.prm = (A) act.getApplication();
        this.gson = new Gson();

        onlineId = Info.gId;

        //Firebase Realtime Database
        fdb = FirebaseDatabase.getInstance();

//        refpub = fdb.getReference().child("public").child("plans");
        refpub = fdb.getReference();
        refpub.addValueEventListener(velpub);

        refpri = fdb.getReference();
        refpri.addValueEventListener(velpri);

    }

    private void setUser() {

        onlineId = Info.gId;

        refpri.removeEventListener(velpri);
        refpri = fdb.getReference().child(C_PRI).child(onlineId).child(C_PLN);
        refpri.addValueEventListener(velpri);

    }

    public void clear(DatabaseReference ref) {
        ref.removeValue();
    }

    public void clear() {
        fdb.getReference().removeValue();
    }


    public void publish(Plan plan) {

//        //設定 realtime database 路徑
//        if (onlineId.equals(Info.gId) == false) {
//            setUser();
//        }

        //推送
        //String -> Json
        final String id = plan.getOrganizer_id();
        String value = gson.toJson(plan);

        //AES 加密
        value = AES.encrypt(prm.AES_KEY, value);

        //Write to Realtime Database
        final String finalvalue = value;
        refpub.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                refpub.child(C_PUB).child(id).child(C_PLN).setValue(finalvalue);

                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });


    }


    public void publish(List<Order> orders) {

        //取得筆數
        int siz = 0;
        List<Order> lo1 = prm.getAllPublicOrders();
        if (lo1 != null) {
            List<Order> lo2 = prm.getMyPublicOrders(lo1);
            if (lo2 != null) {
                siz = lo2.size();
            }
        }

        for (Order o : orders) {    //一次發送所有Order

            //推送
            //String -> Json
            final String sn = String.valueOf(siz++);
            final String id = o.getOrganizer_id();  //發起者ID
//        ordpck.ords = prm.getMyPublicOrders(prm.getAllPublicOrders());     //取出雲上所有自己的信息並加包裝
//        List<Order> lo = prm.getMyPublicOrders(prm.getAllPublicOrders());   //取出雲上所有自己的信息並加包裝
//        for (Order o : order) {
//            lo.add(o);
//        }
            String value = gson.toJson(o);

            //AES 加密
            value = AES.encrypt(prm.AES_KEY, value);

            //Write to Realtime Database
            final String finalvalue = value;
            refpub.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {

                    refpub.child(C_PUB).child(id).child(C_ODR + "_" + sn).setValue(finalvalue);

                    return null;
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });

        }

    }

    public void publish(Map<String, Object> Message) {

        //String -> Json
        final String id = (String) Message.get(KEY_ID);
        msgpck.msgs = prm.getMyPublicMsgs(prm.getAllPublicMsgs());     //取出雲上所有自己的信息並加包裝
        msgpck.msgs.add(Message);    //加入一筆信息
        String value = gson.toJson(msgpck);

        //AES 加密
        value = AES.encrypt(prm.AES_KEY, value);

        //Write to Realtime Database
        final String finalvalue = value;
        refpub.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                refpub.child(C_PUB).child(id).child(C_MSG).setValue(finalvalue);

                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }

    private ValueEventListener velpub = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //拉取Message
//            List<Map<String, Object>> lm = new ArrayList<>();
//            for (final DataSnapshot ds : dataSnapshot.child("pub").child("messages").getChildren()) {
//
//                String key = ds.getKey();
//                String value = (String) ds.getValue();
//
//                //AES 解密
//                if (value == null) {
//                    return;
//                }
//                value = AES.decrypt(prm.AES_KEY, value);
//
////                List<Map<String, Object>> message = new ArrayList<>();
//                //解Json
//                try {
//                    Type typ = new TypeToken<MSGPCK>() {
//                    }.getType();
//                    msgpck = gson.fromJson(value, typ);
//                } catch (RuntimeException e) {
//                    ;
//                }
//
////                if (message.size() > 0) {
//                for (Map<String, Object> mso : msgpck.msgs) {
//                    lm.add(mso);
//                }
////                }
//            }


            //拉取Message
            List<Map<String, Object>> lm = null;
            for (final DataSnapshot ds : dataSnapshot.child(C_PUB).getChildren()) {     //拉取所有資料節點
                for (final DataSnapshot ds_ctx : dataSnapshot.child(C_PUB).child(ds.getKey()).getChildren()) {      //進入節點

                    if (ds_ctx.getKey().equals(C_MSG) == true) {   //解出Message
                        String value = (String) ds_ctx.getValue();
                        value = deAES(value);
                        lm = deJSON_Msg(value);
                        break;
                    }

                }
            }
            prm.setAllPublicMessage(lm);


//
//            //拉取Orders
//            List<Map<String, Object>> lmorder = new ArrayList<>();
//            for (final DataSnapshot ds : dataSnapshot.child("pub").child("orders").getChildren()) {
//                lmorder.add(new HashMap<String, Object>() {
//                    {
//                        put(ds.getKey(), ds.getValue());
//                    }
//                });
//            }

            //拉取Order
            List<Order> lmtmp = new ArrayList<>();
            for (final DataSnapshot ds : dataSnapshot.child(C_PUB).getChildren()) {     //拉取所有資料節點
                for (final DataSnapshot ds_ctx : dataSnapshot.child(C_PUB).child(ds.getKey()).getChildren()) {      //進入節點

                    if (ds_ctx.getKey().indexOf(C_ODR) == 0) {   //解出Order
                        String value = (String) ds_ctx.getValue();
                        value = deAES(value);
                        lmtmp.add(deJSON_Order(value));
                    }

                }
            }
            prm.setAllPublicOrders(lmtmp);


            //拉取Plans
            List<Plan> lp = new ArrayList<>();
            for (final DataSnapshot ds : dataSnapshot.child(C_PUB).getChildren()) {     //拉取所有資料節點
                for (final DataSnapshot ds_ctx : dataSnapshot.child(C_PUB).child(ds.getKey()).getChildren()) {      //進入節點
                    if (ds_ctx.getKey().equals(C_PLN) == true) {        //解出Plan
                        String value = (String) ds_ctx.getValue();
                        value = deAES(value);
                        lp.add(deJSON_Plan(value));
                        break;
                    }
                }
            }

            //顯示至ListView
            if (lp != null) {
                prm.setAllPublicPlan(lp);    //所有Plan更新至本機
            }
            act.initListView();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    private ValueEventListener velpri = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //拉取資料
//            String value = dataSnapshot.getValue(String.class);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    private String deAES(String value) {
        //AES 解密
        if (value == null) {
            return null;
        } else {
            return AES.decrypt(prm.AES_KEY, value);
        }
    }

//    private Object deJSON(String value, Type typ) {
////  Type -> new TypeToken<Plan>() {}.getType();
//        //解Json
//        try {
//            return gson.fromJson(value, typ);
//        } catch (RuntimeException e) {
//            ;
//        }
//        return null;
//    }

    private Plan deJSON_Plan(String value) {

        Type typ = new TypeToken<Plan>() {
        }.getType();

        //解Json
        try {
            return gson.fromJson(value, typ);
        } catch (RuntimeException e) {
            ;
        }
        return null;

    }

    private List<Map<String, Object>> deJSON_Msg(String value) {

        Type typ = new TypeToken<MSGPCK>() {
        }.getType();

        //解Json
        try {
            msgpck = gson.fromJson(value, typ);
            return msgpck.msgs;
        } catch (RuntimeException e) {
            ;
        }
        return null;

    }


    private Order deJSON_Order(String value) {

        Type typ = new TypeToken<Order>() {
        }.getType();

        //解Json
        try {
            return gson.fromJson(value, typ);
        } catch (RuntimeException e) {
            ;
        }

        return null;

    }

}
