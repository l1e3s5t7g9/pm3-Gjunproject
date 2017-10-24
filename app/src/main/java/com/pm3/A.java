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

    public List<Order> getAllPublicOrders() {
        return mPublicOrderList;
    }

    public void setAllPublicOrders(List<Order> mPublicOrderList) {
        this.mPublicOrderList = mPublicOrderList;
    }

    public List<Order> getMyPublicOrders(List<Order> orders) {
        List<Order> rtn = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrganizer_id().equals(Info.gId) == true) {
                rtn.add(o);
            }
        }
        return rtn;
    }

    public List<Order> getMyOrders(List<Order> orders) {
        List<Order> rtn = new ArrayList<>();
        for (Order o : orders) {
            if (o.getSubscriber_id().equals(Info.gId) == true) {
                rtn.add(o);
            }
        }
        return rtn;
    }

    public List<Order> removeMyOrders(List<Order> orders) {
        List<Order> rtn = new ArrayList<>();
        for (Order o : orders) {
            if (o.getSubscriber_id().equals(Info.gId) == true) {
                rtn.remove(o);
            }
        }
        return rtn;
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
        uptoCloud(plan, null, null);    //更新雲端
    }

    public void addMyPublicMsg(Map<String, Object> hm) {
        uptoCloud(null, null, hm);    //更新雲端
    }

    public void addPublicOrder(List<Order> orders) {
        uptoCloud(null, orders, null);
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
//        return lm.get(lm.size() - 1);
        return lm.get(0);
    }

    public void uptoCloud(Plan pushPlan, List<Order> pushOrder, Map<String, Object> pushMsg) {      //更新雲端

        if (mCloudSync != null) {
            if (pushPlan != null) {
                mCloudSync.publish(pushPlan);    //發佈Plan資料
            }
            if (pushOrder != null) {
                mCloudSync.publish(pushOrder);   //發佈Order資料
            }
            if (pushMsg != null) {
                mCloudSync.publish(pushMsg);      //發佈Message資料
            }
        }

    }


}
