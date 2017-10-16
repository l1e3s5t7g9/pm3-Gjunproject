package com.pm3.Class_Object;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by student on 2017/10/16.
 */

public class Order implements Serializable {

    private Calendar stamp;         // 時間戳記
//    private Plan plan;              // 計畫案
    private String subscriber;      // 訂購者
    private Goods goods;           // 商品
    private String notes;          // 備註(規格暫記於此)

    public Order(String subscriber, Goods goods, String notes) {
        this.stamp = Calendar.getInstance();

//        this.plan = plan;
        this.subscriber = subscriber;
        this.goods = goods;
        this.notes = notes;
    }

    public Calendar getStamp() {
        return stamp;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
