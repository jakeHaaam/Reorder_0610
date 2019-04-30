package com.example.reorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.info.StoreMenu;
import com.example.reorder.info.StoreMenuInfo;

import java.util.ArrayList;
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
        viewHolder.tv_menu_name.setText(currentStoreMenuInfo.get(i).getMenu_name());
        viewHolder.tv_menu_price.setText(currentStoreMenuInfo.get(i).getMenu_price());
        viewHolder.tv_menu_info.setText(currentStoreMenuInfo.get(i).getMenu_info());

        // list item click
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
    /*private ArrayList<MenuData>listData=new ArrayList<>();


    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder viewHolder, int position) {

        viewHolder.tv_menu_name.setText(CurrentStoreMenuInfo.getStoreMenu().getStoreMenuInfo().get);
        viewHolder.tv_menu_price.setText(Integer.toString(MenuData.getMenu_price()));
        viewHolder.tv_menu_info.setText(MenuData.getMenu_info());
        //viewHolder.onBind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_menu_name;
        private TextView tv_menu_price;
        private TextView tv_menu_info;

        ViewHolder(View itemView){
            super(itemView);

            tv_menu_name=itemView.findViewById(R.id.tv_menu_name);
            tv_menu_price=itemView.findViewById(R.id.tv_menu_price);
            tv_menu_info=itemView.findViewById(R.id.tv_menu_info);
        }
//        void onBind(MenuData data){
//            tv_menu_name.setText(data.getMenu_name);
//            tv_menu_price.setText(data.getMenu_price);
//            tv_menu_info.setText(data.getMenu_info);
//        }
    }*/
}
