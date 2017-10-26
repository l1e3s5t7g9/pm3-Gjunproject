package com.pm3;

import android.app.Application;

import com.pm3.Account.Info;
import com.pm3.Account.Sign;
import com.pm3.Class_Object.Order;
import com.pm3.Class_Object.Plan;
import com.pm3.Network.CloudSync;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.pm3.MessageActivity.KEY_ID;
import static com.pm3.MessageActivity.KEY_TIM;
import static com.pm3.Tools.time.string2calendar;

/**
 * Created by student on 2017/10/19.
 */

public final class A extends Application {

    //    public final static String AES_KEY = "b4c8n93434";
    public final String AES_KEY = "b4c8n93434";

    public Sign gSign = null;

    public CloudSync mCloudSync = null;

    private List<Plan> mPublicPlanList = new ArrayList<>();     //公開進行中的 Plan Array
    private List<Plan> mPrivatePlanList = new ArrayList<>();    //已經結案的 Plan Array

    private List<Map<String, Object>> mPublicMessageList = new ArrayList<>(); //雲上所有信息

    private List<Order> mPublicOrderList = new ArrayList<>();    //雲上所有未整理的訂購單

    public List<Order> getAllPublicOrders() {       //取得雲上所有Order
        return mPublicOrderList;
    }

    public void setAllPublicOrders(List<Order> mPublicOrderList) {
        this.mPublicOrderList = mPublicOrderList;
    }

//    public List<Order> getAllPublicPlanOrders(String plan_id) {       //過濾出關於某個Plan的Order
//        List<Order> rtn = new ArrayList<>();
//        List<Order> lo = getAllPublicOrders();  //取得所有Order
//        for (Order o : lo) {                    //進行篩選
//            if (o.getOrganizer_id().equals(plan_id) == true) {
//                rtn.add(o);
//            }
//        }
//        return rtn;
//    }

    public Plan filterPlans_Orgid(List<Plan> plans, String filter_id) {          //過濾 發起者ID = 指定ID
        for (Plan p : plans) {
            if (p.getOrganizer_id().equals(filter_id) == true) {
                return p;
            }
        }
        return null;
    }

    public List<Order> filterOrders_Orgid(List<Order> orders, String filter_id) {          //過濾 發起者ID = 指定ID
        List<Order> rtn = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrganizer_id().equals(filter_id) == true) {
                rtn.add(o);
            }
        }
        return rtn;
    }

    public List<Order> filterOrders_Subid(List<Order> orders,String filter_id) {        //過濾 下訂者ID = 指定ID
        List<Order> rtn = new ArrayList<>();
        for (Order o : orders) {
            if (o.getSubscriber_id().equals(filter_id) == true) {
                rtn.add(o);
            }
        }
        return rtn;
    }

    public void removeMyOrders(Plan plan) {

        String my_id = Info.gId;   //取得我的ID
        String plan_id = plan.getOrganizer_id();

        mCloudSync.deleteOrders(plan_id,my_id);

    }

    public void finishMyPlan(){     //整理封存至雲端

        String plan_id = Info.gId;
        Plan plan = getMyPublicPlan();

        List<Order> orders = getAllPublicOrders();
        orders = filterOrders_Orgid(orders,plan_id);   //取得關於這個Plan的Order

        plan.setordersList(orders);

        mCloudSync.uptoCloud(plan);
        mCloudSync.deletePlanPackage(plan_id);

    }


    public List<Plan> getAllPublicPlan() {
        return mPublicPlanList;
    }

    public List<Map<String, Object>> seqMsg(List<Map<String, Object>> lm) {

        ArrayList<Map<String, Object>> rtn = new ArrayList<>();
        while (lm.size() > 0) {

            //找最小時間
            int index = 0;
            int i = 0;

            Calendar max = string2calendar((String) lm.get(i).get(KEY_TIM));
            for (; i < lm.size(); i++) {
                Calendar cal = string2calendar((String) lm.get(i).get(KEY_TIM));
//                if (cal.compareTo(min) < 0) {     // cal < min
                if (cal.before(max) == true) {
                    max = cal;
                    index = i;
                }
            }

            rtn.add(lm.get(index));
            lm.remove(index);

        }

        return rtn;
    }

    public void setAllPublicPlan(List<Plan> planarr) {
        mPublicPlanList = planarr;
    }

    public void setAllPublicMessage(List<Map<String, Object>> messages) {
        if (messages == null || messages.size() <= 0) {
            return;
        }
//        mPublicMessageList = messages;
        mPublicMessageList = seqMsg(messages); //排序
    }

    public Plan getPlan(int item) {
        return mPublicPlanList.get(item);
    }

    public Plan getMyPublicPlan() {
        for (int i = getAllPublicPlanSize() - 1; i >= 0; i--) {
            Plan plan = getPlan(i);
            if (plan.getOrganizer_id().equals(Info.gId) == true) {
                return plan;
            }
        }
        return null;

//        int siz = getAllPublicPlanSize();
//        if (siz > 0) {  //有內容
//            return mPublicPlanList.get(siz - 1);  //取最後(最新)一筆
//        } else {       //無內容
//            return null;
//        }
    }

    public int getAllPublicPlanSize() {
        return mPublicPlanList.size();
    }

    public void addPublicPlan(Plan plan) {
//        mPublicPlanList.add(plan);
        mCloudSync.uptoCloud(plan, null, null);    //更新雲端
    }

    public void addMyPublicMsg(Map<String, Object> hm) {
        mCloudSync.uptoCloud(null, null, hm);    //更新雲端
    }

    public void addPublicOrder(List<Order> orders) {
        mCloudSync.uptoCloud(null, orders, null);
    }

    public List<Map<String, Object>> getAllPublicMsgs() {
        return (mPublicMessageList);
    }

    public List<Map<String, Object>> getMyPublicMsgs(List<Map<String, Object>> lm) {
        List<Map<String, Object>> rtn = new ArrayList<>();
        for (Map<String, Object> mso : lm) {
            if (mso.get(KEY_ID).equals(Info.gId) == true) {
                rtn.add(mso);
            }
        }
        return rtn;
    }

    public int getMsgSiz(List<Map<String, Object>> message) {

        if (message != null) {
            return message.size();
        } else {
            return 0;
        }

    }


    public Map<String, Object> getMsgLatest(List<Map<String, Object>> lm) {

        return lm.get(lm.size()-1);

    }


}
