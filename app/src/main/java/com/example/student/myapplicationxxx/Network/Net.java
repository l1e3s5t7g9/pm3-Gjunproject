package com.example.student.myapplicationxxx.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;

/**
 * Created by student on 2017/10/11.
 */

public final class Net {


    public static String nTypeName;               //目前連線方式
    public static int nType;
    public static String nSubtypeName;
    public static int nSubtype;
    public static NetworkInfo.State nState;         //目前連線狀態
    public static NetworkInfo.DetailedState nDetailedState;
    public static String nExtraInfo;
    public static String nReason;
    public static boolean nAvailable;               //目前網路是否可使用
    public static boolean nConnected;              //網路是否已連接
    public static boolean nConnectedOrConnecting;   //網路是否已連接 或 連線中
    public static boolean nFailover;                //網路目前是否有問題
    public static boolean nRoaming;               //網路目前是否在漫遊中


    public static final boolean NetCheck(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            nTypeName = null;
            nType = -1;
            nSubtypeName = null;
            nSubtype = -1;
            nState = null;
            nDetailedState = null;
            nExtraInfo = null;
            nReason = null;
            nAvailable = false;
            nConnected = false;
            nConnectedOrConnecting = false;
            nFailover = false;
            nRoaming = false;
            return false;
        } else {
            nTypeName = ni.getTypeName();
            nType = ni.getType();
            nSubtypeName = ni.getSubtypeName();
            nSubtype = ni.getSubtype();
            nState = ni.getState();
            nDetailedState = ni.getDetailedState();
            nExtraInfo = ni.getExtraInfo();
            nReason = ni.getReason();
            nAvailable = ni.isAvailable();
            nConnected = ni.isConnected();
            nConnectedOrConnecting = ni.isConnectedOrConnecting();
            nFailover = ni.isFailover();
            nRoaming = ni.isRoaming();
            return true;
        }
    }

    public static final boolean NetCheck(Context ctx, TextView tv) {

        boolean rtn = Net.NetCheck(ctx);
        NetShowOnTextView(tv);
        return rtn;

    }

    public static final void NetShowOnTextView(TextView tv) {
        StringBuilder sb = new StringBuilder();
        sb.append("TypeName ->" + Net.nTypeName);
        sb.append("\n");
        sb.append("Type ->" + Net.nType);
        sb.append("\n");
        sb.append("SubtypeName ->" + Net.nSubtypeName);
        sb.append("\n");
        sb.append("Subtype ->" + Net.nSubtype);
        sb.append("\n");
        sb.append("\n");

        sb.append("State ->" + Net.nState);
        sb.append("\n");
        sb.append("DetailedState ->" + Net.nDetailedState);
        sb.append("\n");
        sb.append("\n");

        sb.append("ExtraInfo ->" + Net.nExtraInfo);
        sb.append("\n");
        sb.append("Reason ->" + Net.nReason);
        sb.append("\n");
        sb.append("\n");

        sb.append("Available ->" + Net.nAvailable);
        sb.append("\n");
        sb.append("Connected ->" + Net.nConnected);
        sb.append("\n");
        sb.append("ConnectedOrConnecting ->" + Net.nConnectedOrConnecting);
        sb.append("\n");
        sb.append("Failover ->" + Net.nFailover);
        sb.append("\n");
        sb.append("Roaming ->" + Net.nRoaming);
        sb.append("\n");

        tv.setText(sb);
    }


}
