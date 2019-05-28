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
import com.example.reorder.Api.StoreIdApi;
import com.example.reorder.R;
import com.example.reorder.Result.StoreIdResult;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.BookMarkStoreInfo;
import com.example.reorder.info.StoreMenuInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookMarkStoreAdapter extends RecyclerView.Adapter<BookMarkStoreAdapter.ViewHolder>{
    List<BookMarkStoreInfo> bookMarkStoreInfoList;
    Context context;
    public String storeinfo_id;
    String url= serverURL.getUrl();

    public BookMarkStoreAdapter(List<BookMarkStoreInfo> bookMarkStoreInfoList, Context context) {
        this.bookMarkStoreInfoList = bookMarkStoreInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰를 생성해 주는 부분
    public BookMarkStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_store,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    //getItemCount해서 해당 갯수 만큼 반복 실행 and 지정된 위치에 데이터를 표시하기 위해 호출
    public void onBindViewHolder(@NonNull final BookMarkStoreAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(Integer.toString(bookMarkStoreInfoList.get(i).getId()));
        viewHolder.st_id.setText(Integer.toString(bookMarkStoreInfoList.get(i).getStore_id()));
        viewHolder.st_name.setText(bookMarkStoreInfoList.get(i).getStore_name());
        viewHolder.st_category.setText(bookMarkStoreInfoList.get(i).getStore_category());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectStore.setId(Integer.parseInt(viewHolder.id.getText().toString()));
                CurrentSelectStore.setSt_id(Integer.parseInt(viewHolder.st_id.getText().toString()));
                CurrentSelectStore.setSt_name(viewHolder.st_name.getText().toString());
                CurrentSelectStore.setSt_category(viewHolder.st_category.getText().toString());
                storeinfo_id=viewHolder.st_id.getText().toString();

                try{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    StoreIdApi storeIdApi= retrofit.create(StoreIdApi.class);
                    storeIdApi.getStore_id(storeinfo_id).enqueue(new Callback<StoreIdResult>() {
                        @Override
                        public void onResponse(Call<StoreIdResult> call, Response<StoreIdResult> response) {
                            if (response.isSuccessful()){
                                StoreIdResult storeIdResult=response.body();
                                if(storeIdResult!=null) {
                                    switch (storeIdResult.getResult()) {
                                        case 1://성공
                                            String store_name=viewHolder.st_name.getText().toString();
                                            List<StoreMenuInfo> storeMenuInfo= storeIdResult.getStoreMenuInfo();
                                            CurrentStoreMenuInfo.getStoreMenu().setStoreMenuInfoList(storeMenuInfo);
                                            ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(2);
                                            break;
                                        case 0://실패
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<StoreIdResult> call, Throwable t) {
                            t.printStackTrace();
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
        return bookMarkStoreInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id,st_id,st_name,st_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.tv_bm_id);
            st_id=itemView.findViewById(R.id.tv_bm_store_id);
            st_name=itemView.findViewById(R.id.tv_bm_store_name);
            st_category=itemView.findViewById(R.id.tv_bm_store_category);
        }
    }

}