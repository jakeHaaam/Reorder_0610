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
import com.example.reorder.Result.CartResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartInfo> currentCartInfo;
    Context context;
    String url= serverURL.getUrl();

    public CartAdapter(List<CartInfo> currentCartInfo, Context context) {
        this.currentCartInfo = currentCartInfo;
        this.context = context;

    }

    public CartAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder viewHolder, int i) {
        Log.d("cart","?"+CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_name());
        Log.d("cart","?"+currentCartInfo.get(i).getMenu_price());
        viewHolder.tv_menu_name.setText(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_name());
        viewHolder.tv_menu_price.setText(Integer.toString(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price()));
        viewHolder.tv_menu_count.setText(Integer.toString(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count()));

    }

    @Override
    public int getItemCount() {
        return currentCartInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu_name;
        private TextView tv_menu_price;
        private TextView tv_menu_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_menu_name = itemView.findViewById(R.id.tv_menu_name);
            tv_menu_price = itemView.findViewById(R.id.tv_menu_price);
            tv_menu_count=itemView.findViewById(R.id.tv_menu_count);
        }
    }
}