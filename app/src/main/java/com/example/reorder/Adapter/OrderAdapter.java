package com.example.reorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentSelectCartInfo;
import com.example.reorder.info.CartInfo;

import org.w3c.dom.Text;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<CartInfo> currentSelectCartInfo;
    Context context;

    public OrderAdapter(List<CartInfo> currentSelectCartInfo, Context context) {
        this.currentSelectCartInfo = currentSelectCartInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_menu_name.setText(CurrentSelectCartInfo.getCart().getCartInfoList().get(i).getMenu_name());
        viewHolder.tv_menu_price.setText(Integer.toString(CurrentSelectCartInfo.getCart().getCartInfoList().get(i).getMenu_price()));
        viewHolder.tv_menu_count.setText(Integer.toString(CurrentSelectCartInfo.getCart().getCartInfoList().get(i).getMenu_count()));



    }

    @Override
    public int getItemCount() {
        return currentSelectCartInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_gae;
        private TextView tv_menu_name;
        private TextView tv_menu_price;
        private TextView tv_menu_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gae=itemView.findViewById(R.id.tv_gae);
            tv_menu_name=itemView.findViewById(R.id.tv_menu_name);
            tv_menu_price=itemView.findViewById(R.id.tv_menu_price);
            tv_menu_count=itemView.findViewById(R.id.tv_menu_count);
        }
    }
}