package com.example.reorder.Fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.Adapter.StoreAdapter;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentSelectCategory;

public class Dialog extends AppCompatDialogFragment {
    private Button bt_korean;
    private Button bt_west;
    private Button bt_cafe;
    private Button bt_snack;
    private Button bt_japanese;
    private Button bt_chinese;
    private Button bt_fastfood;
    private Button bt_no_filter;
    //private DialogListener listener;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("필터");

        bt_cafe=view.findViewById(R.id.bt_cafe);
        bt_west=view.findViewById(R.id.bt_west);
        bt_korean=view.findViewById(R.id.bt_korean);
        bt_japanese=view.findViewById(R.id.bt_japanese);
        bt_chinese=view.findViewById(R.id.bt_chinese);
        bt_no_filter=view.findViewById(R.id.bt_no_filter);
        bt_snack=view.findViewById(R.id.bt_snack);
        bt_fastfood=view.findViewById(R.id.bt_fastfood);

        bt_fastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("패스트푸드");
                Log.d("category",CurrentSelectCategory.getSt_category());
                NavigationnActivity navigationnActivity = (NavigationnActivity)getContext();
                navigationnActivity.categoryChanged();

                dismiss();
            }
        });

        bt_snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("스낵");
                Log.d("category",CurrentSelectCategory.getSt_category());

                dismiss();
            }
        });

        bt_no_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("");
                Log.d("category",CurrentSelectCategory.getSt_category());
                dismiss();
            }
        });

        bt_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("중식");
                dismiss();
            }
        });

        bt_japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("일식");
                Log.d("category",CurrentSelectCategory.getSt_category());
                dismiss();
            }
        });

        bt_korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("한식");
                Log.d("category",CurrentSelectCategory.getSt_category());
                dismiss();
            }
        });

        bt_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("카페");
                Log.d("category",CurrentSelectCategory.getSt_category());
                dismiss();
            }
        });

        bt_west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectCategory.setSt_category("양식");
                Log.d("category",CurrentSelectCategory.getSt_category());
                dismiss();
            }
        });

        return builder.create();
    }
//    public interface DialogListener{
//        void applybutton(Button selected_bt);
//    }
}
