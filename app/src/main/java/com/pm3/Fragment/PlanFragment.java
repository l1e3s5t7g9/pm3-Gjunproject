package com.pm3.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Goods;
import com.pm3.Exception.MyDialogFragmentException;

import java.util.Calendar;
import java.util.List;

/**
 * Created by TiGerTomb on 2017/10/12.
 */

public class PlanFragment extends DialogFragment {
    private String organizer;    // 發起人帳戶
    private String location;     // 集散地點
    private Calendar deadline;   // 截止時間
    private String topic;        // 發起名目
    private List<Goods> goods;  // 發起商品

    private EditText et_topic;
    private EditText et_location;
    private EditText et_deadline;
    private EditText et_arrivaltime;
    private View mDialogView;
    private AlertDialog mDialog;

    public PlanFragment() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        initOkcancelHandler();
        initdialogView();
        initDialog();

        return mDialog;
    }


    private void initdialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mDialogView = inflater.inflate(R.layout.fragment_plan, null);
    }
    private chouse okcancelHandler;

    public interface chouse{
        void 新增專案確定(String 出貨店家,String 集散地點,int 截止時間,int 預計送達時間);

        void 新增專案取消();

        void 處理訊息(String String);
    }

    private void initOkcancelHandler() {
        try {
            okcancelHandler = (chouse) getActivity();

        } catch (ClassCastException cce) {
            String message = "Activity 沒有實作 chouse的功能";
            throw new MyDialogFragmentException(message, cce);

        }

    }




    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("新增專案")
                .setView(mDialogView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            et_topic = (EditText) mDialogView.findViewById(R.id.topic);
                            et_location = (EditText) mDialogView.findViewById(R.id.location);
                            et_deadline= (EditText) mDialogView.findViewById(R.id.deadline);
                            et_arrivaltime= (EditText) mDialogView.findViewById(R.id.arrivaltime);
                            String 發起名目=et_topic.getText().toString();
                            String 集散地點=et_location.getText().toString();
                            int 截止時間=Integer.parseInt(String.valueOf(et_deadline.getText()));
                            int 預計送達時間=Integer.parseInt(String.valueOf(et_arrivaltime.getText()));
                            if(false){
                                throw new Exception("價格必須介於0~1000之間");

                            }
                            else{
                                okcancelHandler.新增專案確定(發起名目,集散地點,截止時間,預計送達時間);
                            }
                        } catch (NumberFormatException e) {
                            okcancelHandler.處理訊息("價格必需是數字");
                        } catch (Exception e) {
                            okcancelHandler.處理訊息(e.getMessage());
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        okcancelHandler.新增專案取消();
                    }
                });
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }


}