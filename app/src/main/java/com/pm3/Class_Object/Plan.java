package com.pm3.Class_Object;

import com.pm3.Account.Info;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TiGerTomb on 2017/10/12.
 */

public class Plan implements Serializable {

    private String organizer_id;    // 發起人帳戶ID
    private String organizer;       // 發起人帳戶
    private String location;        // 集散地點
    private Calendar deadline;      // 截止時間
    private Calendar arrivaltime;   //預計送達時間
    private String topic;           // 發起名目
    private List<Goods> goods;      // 發起商品
    private List<Order> orders;     // 訂單
//    private List<Map<String, Object>> msgarr = new ArrayList<>(); // 信息

    public Plan(String location, Calendar deadline, Calendar arrivaltime, String topic, List<Goods> goods, List<Order> orders) {
        this.organizer_id = Info.gId;
        this.organizer = Info.gDisplayNameNick;
        this.location = location;
        this.deadline = deadline;
        this.arrivaltime = arrivaltime;
        this.topic = topic;
        this.goods = goods;
        this.orders = orders;
    }

    public String getOrganizer_id() {
        return organizer_id;
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

    public Calendar getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(Calendar arrivaltime) {
        this.arrivaltime = arrivaltime;
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

    public List<Goods> getgoodsList() {
        return goods;
    }

    public void setgoodsList(List<Goods> goods) {
        this.goods = goods;
    }

    public List<Order> getordersList() {
        return orders;
    }

    public void setordersList(List<Order> orders) {
        this.orders = orders;
    }
//    public Goods setGoods(int g, Goods item) {
//        return goods.set(g, item);
//    }

//    public List<Map<String, Object>> getMsgarr() {
//        return msgarr;
//    }
//
//    public void addMsg(Map<String, Object> msg) {
//        this.msgarr.add(msg);
//    }

}