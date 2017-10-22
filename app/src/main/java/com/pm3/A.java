package com.pm3;

import android.app.Application;

import com.pm3.Account.Info;
import com.pm3.Account.Sign;
import com.pm3.Class_Object.Plan;
import com.pm3.Tools.CloudSync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Plan> getAllPublicPlan() {
        return mPublicPlanList;
    }

    public List<Map<String, Object>> getAllPublicPlanMsg() {

        List<Map<String, Object>> rtn = new ArrayList<>();

        for (Plan p : getAllPublicPlan()) {

            for (Map<String, Object> m : p.getMsgarr()) {
                rtn.add(m);
            }

        }

//        return seqMsg(rtn); //排序
        return (rtn);
    }

//    public List<Message> seqMsg(List<Message> lm) {
//
//        ArrayList<Message> rtn = new ArrayList<>();
//
//        for (int i = 0; i < lm.size(); i++) {
//
//            if (rtn.size() <= 0) {
//                rtn.add(lm.get(i));
//                lm.remove(i);
//            } else {
//                Calendar cal = (Calendar) lm.get(i).getValue(KEY_TIM);
//                Calendar min = (Calendar) rtn.get(0).getValue(KEY_TIM);
//                if (cal.compareTo(min) < 0) {     // cal < min
//                    rtn.add(lm.get(i));
//                    lm.remove(i);
//                }
//            }
//
//        }
//        rtn.add(new min);
//
//    }

    public void setAllPublicPlan(List<Plan> planarr) {
        mPublicPlanList = planarr;
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
        mPublicPlanList.add(plan);
        uptoCloud();    //更新雲端
    }

    public void addMyPublicPlanMsg(Map<String, Object> hm) {

        int siz = getAllPublicPlanSize();
        if (siz <= 0) {  //無內容
            return;
        }

        int item = siz - 1;
        Plan plan = getMyPublicPlan();  //取得我的公開 Plan

        plan.addMsg(hm);
        mPublicPlanList.set(item, plan);

        uptoCloud();    //更新雲端
    }

    public int getPlanMsgSiz(Plan plan) {

        if (plan != null) {
            return plan.getMsgarr().size();
        } else {
            return 0;
        }

    }

    public Map<String,Object> getPlanMsgLatest(Plan plan) {

        if (plan != null) {
            List<Map<String,Object>> lm = plan.getMsgarr();    //取訊息陣列
            return lm.get(lm.size() - 1);                       //取最終一筆訊息
        } else {
            return null;
        }

    }

    public void delPlanMsg(int item) {

        Plan plan = getMyPublicPlan();       //取得我的公開 Plan
        if (plan != null) {
            plan.getMsgarr().remove(item);
            uptoCloud();    //更新雲端
        }

    }

    public void uptoCloud() {      //更新雲端

        if (mCloudSync != null) {
            mCloudSync.publish();       //發佈資料
        }

    }


}
