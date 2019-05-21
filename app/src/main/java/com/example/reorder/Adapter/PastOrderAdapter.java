package com.example.reorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentPastOrderInfo;
import com.example.reorder.info.RenewPastOrderInfo;
import com.example.reorder.info.PastOrderInfo;
import com.example.reorder.info.RenewPastOrderInfo;

import java.util.List;

public class PastOrderAdapter  extends RecyclerView.Adapter<PastOrderAdapter.ViewHolder> {
    List<PastOrderInfo> pastOrderInfoList;
    Context context;

    public PastOrderAdapter(List<PastOrderInfo> pastOrderInfoList, Context context) {
        this.pastOrderInfoList = pastOrderInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //같은 주문 번호인 애들끼리는 한 아이템에 기록
        viewHolder.order_time.setText(RenewPastOrderInfo.getRenewPast().getList().get(i).getOrder_date());
        viewHolder.tv_past_order_serial.setText(RenewPastOrderInfo.getRenewPast().getList().get(i).getOrder_serial());
        viewHolder.menu_name.setText(RenewPastOrderInfo.getRenewPast().getList().get(i).getMenu_name());
        viewHolder.menu_count.setText(RenewPastOrderInfo.getRenewPast().getList().get(i).getMenu_count());
        viewHolder.used_mileage.setText(RenewPastOrderInfo.getRenewPast().getList().get(i).getUsed_mileage());//이제 allprice도 계산 해야해
        viewHolder.all_price.setText(String.valueOf(RenewPastOrderInfo.getRenewPast().getList().get(i).getMenu_price()));


    }

    @Override
    public int getItemCount() {
        return pastOrderInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView order_time,tv_past_order_serial,menu_name,menu_count
                ,used_mileage,all_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_time=itemView.findViewById(R.id.tv_past_order_time);
            tv_past_order_serial=itemView.findViewById(R.id.tv_past_order_serial);
            menu_name=itemView.findViewById(R.id.tv_past_menu_name);
            menu_count=itemView.findViewById(R.id.tv_past_menu_count);
            used_mileage=itemView.findViewById(R.id.tv_past_used_mileage);
            all_price=itemView.findViewById(R.id.tv_past_all_price);
        }
    }
}