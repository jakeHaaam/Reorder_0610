package com.example.reorder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentPastOrderInfo;
import com.example.reorder.info.PastOrderInfo;

import java.util.List;

public class PastOrderAdapter  extends RecyclerView.Adapter<PastOrderAdapter.ViewHolder> {
    List<PastOrderInfo> pastOrderInfoList;
    Context context;
    String orTime = null,comTime = null,name = null,count = null,mileage = null;
    int allprice=0;

    public PastOrderAdapter(List<PastOrderInfo> pastOrderInfoList, Context context) {
        this.pastOrderInfoList = pastOrderInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderAdapter.ViewHolder viewHolder, int i) {
        //같은 주문 번호인 애들끼리는 한 아이템에 기록

        if(i==0) {//인덱스가 0인 곳
            orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
            comTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getComplate_time();//한 order당 1개만 담음
            name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
            count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
            mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
            allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                    Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
        }
        else {//인덱스 1부터
            if(orTime.isEmpty())//초기화 후 비워져있으면
            {
                orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                comTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getComplate_time();//한 order당 1개만 담음
                name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                        Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
            }
            //현재 담겨있는 오더시간이랑 지금보는 item의 오더시간이 같으면 같은 곳에 담아라
            else if(orTime.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date())){
                name+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                count+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                        Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());

                if(i<pastOrderInfoList.size()-1) {//뒤에 비교할 대상 item이 있으면
                    //뒤랑 비교했는데 오더 시각이 다르면
                    if (!orTime.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i + 1).getOrder_date())) {
                        viewHolder.order_time.setText(orTime);
                        viewHolder.complate_time.setText(comTime);
                        viewHolder.menu_name.setText(name);
                        viewHolder.menu_count.setText(count);
                        viewHolder.used_mileage.setText(mileage);//이제 allprice도 계산 해야해
                        viewHolder.all_price.setText(String.valueOf(allprice));
                        orTime = null;
                        comTime = null;
                        name = null;
                        count = null;
                        mileage = null;
                        allprice = 0;//null로 초기화
                    }
                }
                else {//뒤에 비교할 대상자가 없으면=본인이 마지막이면
                    viewHolder.order_time.setText(orTime);
                    viewHolder.complate_time.setText(comTime);
                    viewHolder.menu_name.setText(name);
                    viewHolder.menu_count.setText(count);
                    viewHolder.used_mileage.setText(mileage);//이제 allprice도 계산 해야해
                    viewHolder.all_price.setText(String.valueOf(allprice));
                }
            }else {//새로운 오더시간이다
                orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                comTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getComplate_time();//한 order당 1개만 담음
                name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                        Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());

                //내가 마지막순서이면
                if(i==pastOrderInfoList.size()-1){
                    viewHolder.order_time.setText(orTime);
                    viewHolder.complate_time.setText(comTime);
                    viewHolder.menu_name.setText(name);
                    viewHolder.menu_count.setText(count);
                    viewHolder.used_mileage.setText(mileage);//이제 allprice도 계산 해야해
                    viewHolder.all_price.setText(String.valueOf(allprice));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return pastOrderInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView order_time,complate_time,menu_name,menu_count
                ,used_mileage,all_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_time=itemView.findViewById(R.id.tv_past_order_time);
            complate_time=itemView.findViewById(R.id.tv_past_complate_time);
            menu_name=itemView.findViewById(R.id.tv_past_menu_name);
            menu_count=itemView.findViewById(R.id.tv_past_menu_count);
            used_mileage=itemView.findViewById(R.id.tv_past_used_mileage);
            all_price=itemView.findViewById(R.id.tv_past_all_price);
        }
    }
}