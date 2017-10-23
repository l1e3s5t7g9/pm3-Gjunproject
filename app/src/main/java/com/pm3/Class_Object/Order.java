package com.pm3.Class_Object;

import com.pm3.Account.Info;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by student on 2017/10/16.
 */

public class Order implements Serializable {

    private Calendar stamp;         // 時間戳記
//    private Plan plan;              // 計畫案
    private String organizer_id;     //發起者ID (識別跟隨的Plan是哪一個)
    private String subscriber_id;    // 訂購者ID
    private String subscriber;      // 訂購者
    private Goods goods;           // 商品         // 商品
    private int 數量;
    private String notes;          // 備註(規格暫記於此)

    public Order(String organizer_id, Goods goods,int 數量, String notes) {
        this.stamp = Calendar.getInstance();

//        this.plan = plan;
        this.organizer_id = organizer_id;
        this.subscriber_id = Info.gId;
        this.subscriber = Info.gDisplayNameNick;
        this.goods = goods;
        this.數量=數量;
        this.notes = notes;
    }

    public Calendar getStamp() {
        return stamp;
    }

    public String getOrganizer_id() {
        return organizer_id;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

//    public Plan getPlan() {
//        return plan;
//    }
//
//    public void setPlan(Plan plan) {
//        this.plan = plan;
//    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int get數量() {
        return 數量;
    }

    public void set數量(int 數量) {
        this.數量 = 數量;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
