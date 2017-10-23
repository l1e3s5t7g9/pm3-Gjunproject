package com.pm3.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.student.myapplicationxxx.R;
import com.pm3.Class_Object.Goods;
import com.pm3.Exception.MyDialogFragmentException;

/**
 * Created by student on 2017/10/12.
 * 建立商品項目時的自定義Dialog
 */

public class FollowFragment extends DialogFragment {
    private TextView tv_goods;             //商品
    private TextView tv_price;             //價錢
    private EditText et_quantity;
    private EditText et_notes;
    private View mDialogView;           //自定義Dialog的畫面
    private AlertDialog mDialog;        //自定義Dialog
    private Goods goods;

    public FollowFragment(Goods goods) {
        this.goods=goods;
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
        mDialogView = inflater.inflate(R.layout.fragment_follow, null);
        tv_goods=(TextView)mDialogView.findViewById(R.id.goods);
        tv_price=(TextView)mDialogView.findViewById(R.id.price);
        tv_goods.setText(goods.getgoods().toString());
        tv_price.setText(String.valueOf(goods.getprice()));
    }

    private chouse okcancelHandler;

    //介面
    public interface chouse {
        void 新增訂單確定(Goods goods, int 數量, String 備註);

        void 新增訂單取消();

        void 處理訊息(String String);
    }

    //讓對象使用Override的功能
    private void initOkcancelHandler() {
        try {
            okcancelHandler = (chouse) getActivity();

        } catch (ClassCastException cce) {
            String message = "Activity 沒有實作 chouse的功能";
            throw new MyDialogFragmentException(message, cce);

        }

    }


    //設定Dialog選項功能
    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("新增訂單")
                .setView(mDialogView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            et_quantity = (EditText) mDialogView.findViewById(R.id.quantity);
                            et_notes = (EditText) mDialogView.findViewById(R.id.notes);
                            int 數量 = (int) Integer.parseInt(et_quantity.getText().toString());
                            String 備註=et_notes.getText().toString();

                            if (數量 <= 0 || 數量 >= 100) {
                                throw new Exception("數量必須介於1~99之間");

                            } else {
                                okcancelHandler.新增訂單確定(goods,數量,備註);
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
                        okcancelHandler.新增訂單取消();
                    }
                });
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }


}