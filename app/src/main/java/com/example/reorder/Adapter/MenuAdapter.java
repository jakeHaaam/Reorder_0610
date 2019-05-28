package com.example.reorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentMenuInfo;
import com.example.reorder.info.StoreMenuInfo;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    List<StoreMenuInfo> currentStoreMenuInfo;
    Context context;

    public MenuAdapter(List<StoreMenuInfo> currentStoreMenuInfo, Context context) {
        this.currentStoreMenuInfo = currentStoreMenuInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder viewHolder, int i) {
        final int menu_id=currentStoreMenuInfo.get(i).getMenu_id();
        final String menu_name=currentStoreMenuInfo.get(i).getMenu_name();
        viewHolder.tv_menu_name.setText(menu_name);
        final String menu_price=Integer.toString(currentStoreMenuInfo.get(i).getMenu_price());
        viewHolder.tv_menu_price.setText(menu_price);
        viewHolder.tv_menu_info.setText(currentStoreMenuInfo.get(i).getMenu_info());

        // list item click
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //해당 메뉴의 수량을 고르게 되는 페이지로 이동 구현
                //동시에 해당 메뉴의 menu_id,price,name을 같이 전달
                CurrentMenuInfo.setMenu_id(menu_id);
                CurrentMenuInfo.setMenu_name(menu_name);
                CurrentMenuInfo.setMenu_price(Integer.parseInt(menu_price));
                CurrentMenuInfo.setMenu_count(1);
                ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(7);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentStoreMenuInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu_name;
        private TextView tv_menu_price;
        private TextView tv_menu_info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_menu_name=itemView.findViewById(R.id.tv_menu_name);
            tv_menu_price=itemView.findViewById(R.id.tv_menu_price);
            tv_menu_info=itemView.findViewById(R.id.tv_menu_info);
        }
    }
}