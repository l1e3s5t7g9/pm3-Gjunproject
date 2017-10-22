package com.pm3.Class_Object;

import java.io.Serializable;

/**
 * Created by student on 2017/10/12.
 */


public class Goods implements Serializable {

    private String Store;           // 店家
    private String Goods;          // 商品名
    private float Price;            // 單價

    public Goods(String store, String goods, float price) {
        this.Store = store;
        this.Goods = goods;
        this.Price = price;
    }
    public String getstore(){
        return Store;
    }
    public void setstore(String store){
        this.Store = store;
    }

    public String getgoods(){
        return Goods;
    }
    public void setgoods(String goods){
        this.Goods = goods;
    }

    public float getprice(){
        return Price;
    }
    public void setprice(float price){
        this.Price = price;
    }
}
