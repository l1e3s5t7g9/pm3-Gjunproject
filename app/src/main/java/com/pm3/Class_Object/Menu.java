package com.pm3.Class_Object;

import java.io.Serializable;
import java.util.List;

/**
 * Created by student on 2017/10/27.
 */

public class Menu implements Serializable {
    private String  菜單名稱;           // 菜單名稱
    private String  菜名說明;           // 菜名說明
    private List<Goods> 商品內容;       // 商品內容

    public String get菜單名稱(){
        return 菜單名稱;
    }
    public String get菜名說明(){
        return 菜名說明;
    }
    public List<Goods> get商品內容(){
        return 商品內容;
    }

    public void set菜單名稱(String 菜單名稱){
        this.菜單名稱=菜單名稱;
    }
    public void set菜名說明(String 菜名說明){
        this.菜名說明=菜名說明;
    }
    public void set商品內容(List<Goods> 商品內容){
        this.商品內容=商品內容;
    }

}

