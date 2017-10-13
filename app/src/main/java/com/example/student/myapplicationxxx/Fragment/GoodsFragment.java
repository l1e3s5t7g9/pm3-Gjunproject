package com.example.student.myapplicationxxx.Fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.student.myapplicationxxx.Class_Object.Goods;
import com.example.student.myapplicationxxx.Exception.MyDialogFragmentException;
import com.example.student.myapplicationxxx.R;

/**
 * Created by student on 2017/10/12.
 */

public class GoodsFragment extends DialogFragment {
    private EditText goods;
    private EditText price;
    private View mDialogView;
    private AlertDialog mDialog;

    public GoodsFragment() {
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

        mDialogView = inflater.inflate(R.layout.fragment_goods, null);
    }
    private chouse okcancelHandler;

    public interface chouse{
        void 新增商品確定(Goods goods);

        void 新增商品取消();

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
        builder.setTitle("新增商品")
                .setView(mDialogView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            goods = (EditText) mDialogView.findViewById(R.id.goods);
                            price = (EditText) mDialogView.findViewById(R.id.price);
                            int 價格 =(int)Float.parseFloat(price.getText().toString());

                            if(價格<0||價格>1000){
                                throw new Exception("價格必須介於0~1000之間");

                            }
                            else{
                                Goods goods = 輸入資料toNewGoods();
                                okcancelHandler.新增商品確定(goods);
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
                        okcancelHandler.新增商品取消();
                    }
                });
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    private Goods 輸入資料toNewGoods(){

        EditText et_goods = (EditText) mDialogView.findViewById(R.id.goods);
        EditText et_price = (EditText) mDialogView.findViewById(R.id.price);
        String goods=et_goods.getText().toString();
        float price =Float.parseFloat(et_price.getText().toString());
        return new Goods(goods,goods,price);




    }
}