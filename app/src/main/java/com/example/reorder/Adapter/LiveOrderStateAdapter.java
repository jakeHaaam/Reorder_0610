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
        viewHolder.order_state.setText("순번이 들어갈 에정.");
        viewHolder.order_serial.setText(String.valueOf(CurrentLiveOrderStateInfo.getLiveOrderState().getLiveOrderStateInfos().get(i).getOrder_serial()));
    }

    @Override
    public int getItemCount() {
        return liveOrderStateInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView order_state,order_serial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_serial=itemView.findViewById(R.id.tv_order_serial);
            order_state=itemView.findViewById(R.id.tv_state);
        }
    }
}