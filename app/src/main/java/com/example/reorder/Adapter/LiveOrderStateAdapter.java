package com.example.reorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentLiveOrderStateInfo;
import com.example.reorder.info.LiveOrderStateInfo;
import com.example.reorder.info.StoreMenuInfo;

import org.w3c.dom.Text;

import java.util.List;

public class LiveOrderStateAdapter extends RecyclerView.Adapter<LiveOrderStateAdapter.ViewHolder> {

    List<LiveOrderStateInfo> liveOrderStateInfoList;
    Context context;

    public LiveOrderStateAdapter(List<LiveOrderStateInfo> liveOrderStateInfoList, Context context) {
        this.liveOrderStateInfoList = liveOrderStateInfoList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.live_order_state,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(CurrentLiveOrderStateInfo.getLiveOrderState().getLiveOrderStateInfos().get(i).getOrder_state()==0){
            viewHolder.order_state.setText("현재 제조중입니다.");
        }
        //viewHolder.order_state.setText(CurrentLiveOrderStateInfo.getLiveOrderState().getLiveOrderStateInfos().get(i).getOrder_state());
        viewHolder.order_time.setText(String.valueOf(CurrentLiveOrderStateInfo.getLiveOrderState().getLiveOrderStateInfos().get(i).getOrder_serial()));
        //현재 order_time에는 order_serial이 보여질 것임
    }

    @Override
    public int getItemCount() {
        return liveOrderStateInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView order_time,order_state;//일단 time에는 오더 serial찍어볼 예정

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_time=itemView.findViewById(R.id.tv_order_time);
            order_state=itemView.findViewById(R.id.tv_state);
        }
    }
}