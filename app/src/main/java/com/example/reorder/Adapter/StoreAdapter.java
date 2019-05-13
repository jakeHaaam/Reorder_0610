package com.example.reorder.Adapter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.R;
import com.example.reorder.Result.StoreIdResult;
import com.example.reorder.Api.StoreIdApi;
import com.example.reorder.globalVariables.CurrentLocation;
import com.example.reorder.globalVariables.CurrentSelectCategory;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.StoreMenuInfo;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.getActivity;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> implements Filterable {
    List<StoreInfo> currentStoreInfo;
    Context context;
    public String storeinfo_id;
    String url= serverURL.getUrl();

    public StoreAdapter(List<StoreInfo> currentStoreInfo, Context context) {
        this.currentStoreInfo = currentStoreInfo;
        this.context = context;
    }



    public StoreAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.near_store,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        Location mylocation=new Location("mylocation");
        mylocation.setLatitude(CurrentLocation.getLat());
        mylocation.setLongitude(CurrentLocation.getLng());
        Log.d("my location",""+mylocation.getLatitude()+"//"+mylocation.getLongitude());



//        if(CurrentSelectCategory.getSt_category()=="") {
            viewHolder.card.setVisibility(View.VISIBLE);
            //거리 계산
            Location st_location=new Location("st_location");
            st_location.setLatitude(Double.parseDouble(currentStoreInfo.get(i).getStore_lat()));
            st_location.setLongitude(Double.parseDouble(currentStoreInfo.get(i).getStore_lng()));
            double distance=Math.round((mylocation.distanceTo(st_location)));
            if(distance>1000) {
                viewHolder.st_dis.setText(String.valueOf(Math.round(distance)/1000.0) + "km");
            }else {
                viewHolder.st_dis.setText(String.valueOf(distance) + "m");
            }

            //각 뷰에 연결
            viewHolder.id.setText(Integer.toString(currentStoreInfo.get(i).getId()));
            viewHolder.st_id.setText(Integer.toString(currentStoreInfo.get(i).getStore_id()));
            viewHolder.st_name.setText(currentStoreInfo.get(i).getStore_name());
            viewHolder.st_category.setText(currentStoreInfo.get(i).getStore_category());

//        else if(CurrentSelectCategory.getSt_category()
//                .equals(currentStoreInfo.get(i).getStore_category())){
//            viewHolder.card.setVisibility(View.VISIBLE);
//            Location st_location=new Location("st_location");
//            st_location.setLatitude(Double.parseDouble(currentStoreInfo.get(i).getStore_lat()));
//            st_location.setLongitude(Double.parseDouble(currentStoreInfo.get(i).getStore_lng()));
//            double distance=Math.round((mylocation.distanceTo(st_location)));
//            viewHolder.st_dis.setText(String.valueOf(distance)+"m");
//
//            viewHolder.id.setText(Integer.toString(currentStoreInfo.get(i).getId()));
//            viewHolder.st_id.setText(Integer.toString(currentStoreInfo.get(i).getStore_id()));
//            viewHolder.st_name.setText(currentStoreInfo.get(i).getStore_name());
//            viewHolder.st_category.setText(currentStoreInfo.get(i).getStore_category());
//        }
//        else if(!CurrentSelectCategory.getSt_category()
//                .equals(currentStoreInfo.get(i).getStore_category())){
//            viewHolder.card.setVisibility(View.GONE);
////            currentStoreInfo.remove(i);
////            notifyItemRemoved(i);
////            notifyItemRangeChanged(i,currentStoreInfo.size());
//        }

        // list item click
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectStore.setId(Integer.parseInt(viewHolder.id.getText().toString()));
                CurrentSelectStore.setSt_id(Integer.parseInt(viewHolder.st_id.getText().toString()));
                CurrentSelectStore.setSt_name(viewHolder.st_name.getText().toString());
                CurrentSelectStore.setSt_category(viewHolder.st_category.getText().toString());
                storeinfo_id=viewHolder.st_id.getText().toString();
                Log.d("storeadapter","!@#!#!@#!@ " + storeinfo_id);

                try{
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                StoreIdApi storeIdApi= retrofit.create(StoreIdApi.class);
                storeIdApi.getStore_id(storeinfo_id).enqueue(new Callback<StoreIdResult>() {
                    @Override
                    public void onResponse(Call<StoreIdResult> call, Response<StoreIdResult> response) {
                        Log.d("storeadapter",response.message()+"*^^* "+response.toString());
                        if (response.isSuccessful()){
                            StoreIdResult storeIdResult=response.body();
                            if(storeIdResult!=null) {
                                switch (storeIdResult.getResult()) {
                                    case 1://성공
                                        Log.d("storeadapter", "store 받아오기 성공");
                                        List<StoreMenuInfo> storeMenuInfo= storeIdResult.getStoreMenuInfo();
                                        CurrentStoreMenuInfo.getStoreMenu().setStoreMenuInfoList(storeMenuInfo);
                                        ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(2);
                                        break;
                                    case 0://실패
                                        Log.d("storeadapter", "store 받아오기 실패");
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreIdResult> call, Throwable t) {

                    }
                });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentStoreInfo.size();
    }

    @Override
    public Filter getFilter() {//필터
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView id,st_id,st_name,st_category,st_dis;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            id=itemView.findViewById(R.id.tv_id);
            st_id=itemView.findViewById(R.id.tv_near_store_id);
            st_name=itemView.findViewById(R.id.tv_near_store_name);
            st_category=itemView.findViewById(R.id.tv_near_store_category);
            st_dis=itemView.findViewById(R.id.tv_dis);
        }
    }

    public String getStoreinfo_id(){
        return this.storeinfo_id;
    }
    public void setItems(List<StoreInfo> storeInfos){
        currentStoreInfo=storeInfos;
        notifyDataSetChanged();
    }
}
