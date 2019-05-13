package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.Adapter.CartAdapter;
import com.example.reorder.Api.OrderAndSeatApi;
import com.example.reorder.Api.OrderApi;
import com.example.reorder.Api.RetrofitApi;
import com.example.reorder.Api.StoreSeatApi;
import com.example.reorder.R;
import com.example.reorder.Result.GetBookMarkResult;
import com.example.reorder.Result.OrderAndSeatResult;
import com.example.reorder.Result.OrderResult;
import com.example.reorder.Result.StoreSeatResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSeatInfo;
import com.example.reorder.globalVariables.CurrentStoreSeatInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.OrderState;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;
import com.example.reorder.info.SeatInfo;
import com.example.reorder.info.StoreSeatInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RadioButton rb_eat_here;
    private RadioButton rb_take_out;
    private RadioButton rb_seat_yes;
    private RadioButton rb_seat_no;
    private RadioGroup rg_seat;
    private RadioGroup rg_eat;
    private LinearLayout ll_seat;
    private TextView tv_selected_seat;
    private Button bt_order;
    private Bundle bundle;
    private RecyclerView rv_item;
    private List<CartInfo> currentCartInfo;
    private RecyclerView.Adapter cart_adapter;
    String url= serverURL.getUrl();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_order, container, false);
        rb_eat_here=view.findViewById(R.id.rb_eat_here);
        rb_take_out=view.findViewById(R.id.rb_take_out);
        rb_take_out.setChecked(true);
        rb_seat_yes=view.findViewById(R.id.rb_seat_yes);
        rb_seat_no=view.findViewById(R.id.rb_seat_no);
        rg_eat=view.findViewById(R.id.rg_eat);
        rg_seat=view.findViewById(R.id.rg_seat);
        ll_seat=view.findViewById(R.id.ll_seat);
        bt_order=view.findViewById(R.id.bt_order);
        rv_item=view.findViewById(R.id.rv_order);
        rv_item.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentCartInfo=CurrentCartInfo.getCart().getCartInfoList();
        cart_adapter=new CartAdapter(currentCartInfo,inflater.getContext());
        rv_item.setAdapter(cart_adapter);
        //장바구니에서 선택된 제품만 주문하는게 아니라서 장바구니 아이템/어댑터 사용
        if(bundle!=null) {
            ArrayList<Integer> seat = getActivity().getIntent().getExtras().getIntegerArrayList("bundle");
            if (seat != null) {
                tv_selected_seat.setText(seat.toString());
                tv_selected_seat.setVisibility(View.VISIBLE);
            }
            else
                tv_selected_seat.setVisibility(View.GONE);
        }


        rg_eat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_eat_here)
                    ll_seat.setVisibility(View.VISIBLE);
                else
                    ll_seat.setVisibility(View.GONE);
            }
        });

        bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<JSONObject> list=new ArrayList<>();
                if(rb_take_out.isChecked()||rb_eat_here.isChecked() && rb_seat_no.isChecked()){
                    if(CurrentCartInfo.getCart().getCartInfoList().size()>1) {//주문 갯수 1개 이상 시
                        try {
                            for (int i = 0; i < CurrentCartInfo.getCart().getCartInfoList().size(); i++) {
                                String id = String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                                String store_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getStore_id() + 1);
                                String menu_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_id());
                                String menu_name = CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_name();
                                String menu_price = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price());
                                String menu_count = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());

                                JSONObject object = new JSONObject();
                                object.put("id", id);
                                object.put("store_id", store_id);
                                object.put("menu_id", menu_id);
                                object.put("menu_name", menu_name);
                                object.put("menu_price", menu_price);
                                object.put("menu_count", menu_count);
                                list.add(object);
                                //Log.d("list",list.toString());
                            }
                            try {
                                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                                OrderApi orderApi = retrofit.create(OrderApi.class);
                                orderApi.getResult(list).enqueue(new Callback<OrderResult>() {
                                    @Override
                                    public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                                        Log.d("respone", "respone");
                                        if (response.isSuccessful()) {
                                            Log.d("respone is successful", "respone is successful");
                                            OrderResult map = response.body();
                                            if (map != null) {
                                                Log.d("body is not null", "body is not null");
                                                switch (map.getResult()) {
                                                    case 1://성공
                                                        OrderState.setOrder_id(map.getOrder_id());
                                                        OrderState.setOrder_state(map.getOrder_state());
                                                        Toast.makeText(getContext(), "주문이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                                        ((NavigationnActivity) NavigationnActivity.mContext).replaceFragment(1);
                                                        break;
                                                    case 0:
                                                        Toast.makeText(getContext(), "주문이 전송되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderResult> call, Throwable t) {
                                        t.printStackTrace();
                                        Log.d("order", "fail");
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("order", "catch");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {//주문 갯수 1개 시
                        try {
                                String id = String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                                String store_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getStore_id() + 1);
                                String menu_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_id());
                                String menu_name = CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_name();
                                String menu_price = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_price());
                                String menu_count = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_count());
                            try {
                                HashMap<String, String> input=new HashMap<>();
                                input.put("id",id);
                                input.put("store_id",store_id);
                                input.put("menu_id",menu_id);
                                input.put("menu_name",menu_name);
                                input.put("menu_price",menu_price);
                                input.put("menu_count",menu_count);
                                Log.d("order","input= "+input);
                                Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                                OrderApi orderApi = retrofit.create(OrderApi.class);
                                orderApi.getResult(input).enqueue(new Callback<OrderResult>() {
                                    @Override
                                    public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                                        Log.d("respone", "respone");
                                        if (response.isSuccessful()) {
                                            Log.d("respone is successful", "respone is successful");
                                            OrderResult map = response.body();
                                            if (map != null) {
                                                Log.d("body is not null", "body is not null");
                                                switch (map.getResult()) {
                                                    case 1://성공
                                                        OrderState.setOrder_id(map.getOrder_id());
                                                        OrderState.setOrder_state(map.getOrder_state());
                                                        Toast.makeText(getContext(), "주문이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                                        ((NavigationnActivity) NavigationnActivity.mContext).replaceFragment(1);
                                                        break;
                                                    case 0:
                                                        Toast.makeText(getContext(), "주문이 전송되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderResult> call, Throwable t) {
                                        t.printStackTrace();
                                        Log.d("order", "fail");
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("order", "catch");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                else if(rb_eat_here.isChecked() && rb_seat_yes.isChecked()){//좌석 예약시 페이지 이동

                    try {
                        String st_id=String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getStore_id());
                        //이거는 0해도됨->이유: 장바구니에는 어차피 같은 스토어만 저장 할 것이니깐
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        StoreSeatApi storeSeatApi = retrofit.create(StoreSeatApi.class);
                        storeSeatApi.getResult(st_id)
                                .enqueue(new Callback<StoreSeatResult>() {
                                    @Override
                                    public void onResponse(Call<StoreSeatResult> call, Response<StoreSeatResult> response) {
                                        Log.d("store_seat","respone");
                                        Log.d("store_seat",""+response.body());
                                        if(response.isSuccessful())
                                        {
                                            Log.d("store_seat","respone성공");
                                            StoreSeatResult storeSeatResult=response.body();
                                            switch (storeSeatResult.getResult()){
                                                case 1://성공
                                                    Log.d("store_seat","body성공");
                                                    Log.d("store_seat",storeSeatResult.toString());
                                                    List<SeatInfo> seatInfos=storeSeatResult.getSeatInfos();//좌석 상태받는 애
                                                    StoreSeatInfo storeSeatInfo=storeSeatResult.getStoreSeatInfo();
                                                    CurrentStoreSeatInfo.getStoreSeat().setStoreSeatInfo(storeSeatInfo);
                                                    CurrentSeatInfo.getSeat().setSeatInfoList(seatInfos);
                                                    Log.d("seat",""+seatInfos);
                                                    ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(6);
                                                    break;
                                                case 0://실패
//                                                    Toast.makeText(getContext(),"등록된 매장이 없습니다.",Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<StoreSeatResult> call, Throwable t) {
                                        t.printStackTrace();
                                        Log.d("store_seat", String.valueOf(call));
                                        Log.d("store_seat","fail");
                                    }
                                });
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("bt_bookmark","excep");
                    }
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}