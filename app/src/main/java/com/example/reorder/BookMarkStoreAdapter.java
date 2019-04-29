package com.example.reorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.info.BookMarkStoreInfo;

import java.util.List;

public class BookMarkStoreAdapter extends RecyclerView.Adapter<BookMarkStoreAdapter.ViewHolder>{
    List<BookMarkStoreInfo> bookMarkStoreInfoList;
    Context context;

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
    public void onBindViewHolder(@NonNull BookMarkStoreAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText("id: " + Integer.toString(bookMarkStoreInfoList.get(i).getId()));
        viewHolder.st_id.setText("st_id: " + Integer.toString(bookMarkStoreInfoList.get(i).getStore_id()));
        viewHolder.st_name.setText("가게이름: " + bookMarkStoreInfoList.get(i).getStore_name());
        viewHolder.st_lat.setText("경도: " + bookMarkStoreInfoList.get(i).getStore_lat());
        viewHolder.st_lng.setText("위도: " + bookMarkStoreInfoList.get(i).getStore_lng());
        viewHolder.st_category.setText("카테고리: " + bookMarkStoreInfoList.get(i).getStore_category());
    }

    @Override
    public int getItemCount() {
        return bookMarkStoreInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id,st_id,st_name,st_lat,st_lng,st_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.tv_bm_id);
            st_id=itemView.findViewById(R.id.tv_bm_store_id);
            st_name=itemView.findViewById(R.id.tv_bm_store_name);
            st_lat=itemView.findViewById(R.id.tv_bm_store_lat);
            st_lng=itemView.findViewById(R.id.tv_bm_store_lng);
            st_category=itemView.findViewById(R.id.tv_bm_store_category);
        }
    }

}
