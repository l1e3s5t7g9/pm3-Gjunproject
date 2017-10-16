package com.pm3.Class_Object;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TiGerTomb on 2017/10/12.
 */

public class Plan implements Serializable {

    private String organizer;    // 發起人帳戶
    private String location;     // 集散地點
    private Calendar deadline;   // 截止時間
    private String topic;        // 發起名目
    private List<Goods> goods;  // 發起商品
    private List<Order> orders; //訂單
    public Plan(String organizer, String location, Calendar deadline, String topic, List<Goods> goods) {
        this.organizer = organizer;
        this.location = location;
        this.deadline = deadline;
        this.topic = topic;
        this.goods = goods;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public void addGoods(Goods item) {
        this.goods.add(item);
    }

    public Goods delGoods(int g) {
        return goods.remove(g);
    }

    public Goods getGoods(int g) {
        return goods.get(g);
    }

    public List<Goods> getgoodsList(){
        return goods;
    }

//    public Goods setGoods(int g, Goods item) {
//        return goods.set(g, item);
//    }


}