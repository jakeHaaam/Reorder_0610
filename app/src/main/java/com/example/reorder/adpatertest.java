package com.example.reorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.info.StoreInfo;

import java.util.List;

public class adpatertest extends RecyclerView.Adapter<adpatertest.ViewHolder> {
    List<StoreInfo> currentStoreInfo;
    Context context;

    public adpatertest(List<StoreInfo> currentStoreInfo, Context context) {
        this.currentStoreInfo = currentStoreInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.near_store,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.id.setText(Integer.toString(currentStoreInfo.get(i).getId()));
        //viewHolder.id.setText(CurrentStoreInfo.getStore().getStoreInfos().get(0).getId());
        //viewHolder.st_id.setText(CurrentStoreInfo.getStore().getStoreInfos().get(1).getStore_id());
        viewHolder.st_id.setText(Integer.toString(currentStoreInfo.get(i).getStore_id()));
        viewHolder.st_name.setText(currentStoreInfo.get(i).getStore_name());
        viewHolder.st_lat.setText(currentStoreInfo.get(i).getStore_lat());
        viewHolder.st_lng.setText(currentStoreInfo.get(i).getStore_lng());
        viewHolder.st_category.setText(currentStoreInfo.get(i).getStore_category());


        // list item click
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return currentStoreInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id,st_id,st_name,st_lat,st_lng,st_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.tv_id);
            st_id=itemView.findViewById(R.id.tv_near_store_id);
            st_name=itemView.findViewById(R.id.tv_near_store_name);
            st_lat=itemView.findViewById(R.id.tv_near_store_lat);
            st_lng=itemView.findViewById(R.id.tv_near_store_lng);
            st_category=itemView.findViewById(R.id.tv_near_store_category);
        }
    }
}
