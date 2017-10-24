package com.pm3.Tools;

import com.example.student.myapplicationxxx.R;
import com.pm3.A;
import com.pm3.Account.Info;
import com.pm3.MainActivity;
import com.pm3.Network.Net;

import java.util.List;
import java.util.Map;

import static com.pm3.Account.Info.NO_SIGN;
import static com.pm3.MessageActivity.KEY_MSG;
import static com.pm3.MessageActivity.KEY_NICK;
import static com.pm3.Network.Net.OFFLINE;

/**
 * Created by Felix on 2017/10/21.
 */

public class Notice {

    private static int shift = 0;

    public static void showNext(MainActivity act) {

        A prm = (A) act.getApplication();

        Net.NetCheck(act);      //更新網路連接狀態
        act.check();//判定是否有自己的團購
        StringBuilder sb = new StringBuilder();
        if (Net.internet == OFFLINE) {                //尚未連接網路

            sb.append("＜尚未連接網路＞");

        } else if ((prm.getMyPublicPlan() == null)    //無 Plan
                || (prm.getMsgSiz(prm.getMyPublicMsgs(prm.getAllPublicMsgs())) <= 0)        //無我的公開 Plan 訊息
                ) {

            if (Info.gId.equals(NO_SIGN) == true) {    //尚未登入
                sb.append("＜正在登入帳戶＞");
            } else {                                    //已登入
                sb.append("　　　　　　　(❛◡❛✿)");     //預設訊息 Line 1
                sb.append("\n");
                sb.append("(╭￣3￣)╭♡");     //預設訊息 Line 2
            }

        } else {                         //有訊息

            //取得最新一筆訊息 Line 1
            List<Map<String, Object>> lm = prm.getAllPublicMsgs();
            sb.append(prm.getMsgLatest(lm).get(KEY_NICK));
            sb.append("：");
            sb.append("\n");

            //取得最新一筆訊息 Line 2
            StringBuilder sbsft = new StringBuilder();
            sbsft.append("　　　　　　　　　　　　　");
            sbsft.append(prm.getMsgLatest(lm).get(KEY_MSG));
            sbsft.append("　　　　　　");

            //切替畫面
            shift++;
            if (shift > sbsft.length()) {
                shift = 0;
            }
            sbsft.delete(0, shift);


            //結合 Line 1 + Line 2
            sb.append(sbsft);

        }
//        act.runOnUiTextView(R.id.textViewNickName, Info.gDisplayNameNick);
        act.runOnUiTextView(R.id.textViewNotification, sb);

    }


}
