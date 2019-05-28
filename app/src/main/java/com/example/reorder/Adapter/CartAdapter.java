package com.example.reorder.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.reorder.Fragment.CartFragment;
import com.example.reorder.R;
import com.example.reorder.Result.CartResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSelectCartInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartInfo> currentCartInfo;
    public static List<CartInfo> cartInfoList;
    Context context;
    public static boolean [] selected;
    String url= serverURL.getUrl();
    public static int totalprice;

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
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_menu_name.setText(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_name());
        viewHolder.tv_menu_price.setText(Integer.toString(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price()));
        viewHolder.tv_menu_count.setText(Integer.toString(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count()));
        totalprice=0;
        cartInfoList= new ArrayList<CartInfo>();

        selected= new boolean[CurrentCartInfo.getCart().getCartInfoList().size()];
        for(int j=0;j<CurrentCartInfo.getCart().getCartInfoList().size();++j){
            selected[j]=false;
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!selected[i]) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                    viewHolder.tv_menu_name.setTextColor(Color.WHITE);
                    viewHolder.tv_menu_count.setTextColor(Color.WHITE);
                    viewHolder.tv_menu_price.setTextColor(Color.WHITE);
                    viewHolder.tv_gae.setTextColor(Color.WHITE);
                    cartInfoList.add(CurrentCartInfo.getCart().getCartInfoList().get(i));
                    //tv_cart_total_price.setText(Integer.toString(0));
                    totalprice+=(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price())*(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());
                    CartFragment.tv_cart_total_price.setText(Integer.toString(totalprice));

                    selected[i]=true;
                }
                else {
                    viewHolder.itemView.setBackgroundColor(Color.WHITE);
                    viewHolder.tv_menu_name.setTextColor(Color.rgb(113,92,75));
                    viewHolder.tv_menu_count.setTextColor(Color.rgb(170,136,109));
                    viewHolder.tv_menu_price.setTextColor(Color.rgb(113,92,75));
                    viewHolder.tv_gae.setTextColor(Color.rgb(170,136,109));
                    cartInfoList.remove(CurrentCartInfo.getCart().getCartInfoList().get(i));
                    totalprice-=(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price())*(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());
                    CartFragment.tv_cart_total_price.setText(Integer.toString(totalprice));
                    selected[i]=false;
                }
            }
        });
    }
    /*
        public int sumTotalPrice(){
            int totalprice=0;
            for(int i=0;i< CurrentCartInfo.getCart().getCartInfoList().size();++i){
                totalprice+=(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price())
                        *(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());
            }
            return totalprice;
        }
        */
    @Override
    public int getItemCount() {
        return currentCartInfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_gae;
        private TextView tv_menu_name;
        private TextView tv_menu_price;
        private TextView tv_menu_count;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gae=itemView.findViewById(R.id.tv_gae);
            tv_menu_name = itemView.findViewById(R.id.tv_menu_name);
            tv_menu_price = itemView.findViewById(R.id.tv_menu_price);
            tv_menu_count=itemView.findViewById(R.id.tv_menu_count);
            //cb_cart=itemView.findViewById(R.id.cb_cart);
        }
    }
}