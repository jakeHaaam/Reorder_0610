package com.example.reorder.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.Api.OrderApi;
import com.example.reorder.R;
import com.example.reorder.Adapter.StoreAdapter;
import com.example.reorder.Api.OrderAndSeatApi;
import com.example.reorder.Result.OrderAndSeatResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSeatInfo;
import com.example.reorder.globalVariables.CurrentStoreSeatInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.OrderState;
import com.example.reorder.globalVariables.serverURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatReserveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatReserveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatReserveFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button bt_order_ok;
    private GridLayout grid;
    private Context context;
    String url= serverURL.getUrl();
    int checked_count,max_count;
    private static int[] seat_state;
    public static boolean[] seat_checked;
    private int select_id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SeatReserveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeatReserveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeatReserveFragment newInstance(String param1, String param2) {
        SeatReserveFragment fragment = new SeatReserveFragment();
        Bundle args = new Bundle();
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
        View view=inflater.inflate(R.layout.fragment_seat_reserve, container, false);
        bt_order_ok=view.findViewById(R.id.bt_order_ok);
        grid= view.findViewById(R.id.grid);
        context=grid.getContext();
        checked_count=0;
        max_count=1;

        final SeatReserveFragment seatReserveFragment=new SeatReserveFragment();

        Log.d("seat",""+CurrentStoreSeatInfo.getStoreSeat().getStoreSeatInfo().getSeat_num());
        int st_num= CurrentStoreSeatInfo.getStoreSeat().getStoreSeatInfo().getSeat_num();
        int st_row=CurrentStoreSeatInfo.getStoreSeat().getStoreSeatInfo().getSeat_row();
        seat_state=new int[st_num];
        seat_checked=new boolean[st_num+1];//seat갯수 만큼 불린형 생성
        Arrays.fill(seat_checked,false);//불린형 false로 초기화
        grid.setColumnCount(st_row);//<-여기에 받아오는 행렬의 행값 입력 해주면 해당 행까지만 나오고 자동으로 다음 열로 이동해서 생성 됨
        for(int j=0;j<st_num;j++)//버튼 동적 생성 부분
        {
            seat_state[j]= CurrentSeatInfo.getSeat().getSeatInfoList().get(j).getSeat_statement();//db에서 받아온 seat_state를 seat_id에 맞춰 집어넣어줘야 함
            //if()
            final Button bt = new Button(context);
            bt.setId(j + 1);//id=1,2,3,4 이런식으로 할당 받아짐
            bt.setText(String.valueOf(j + 1));
            if(seat_state[j]==0){ //"0"은 사용 가능한 상태
                bt.setBackgroundColor(Color.WHITE);
                bt.setOnClickListener(this);}
            else if(seat_state[j]==1){//"1"은 예약중인 상태-예약 불가
                bt.setBackgroundColor(Color.YELLOW);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"선택한 테이블은 예약중인 좌석입니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                }
            else if(seat_state[j]==2){//"2"는 현재 사용중인 상태-예약 불가
                bt.setBackgroundColor(Color.GREEN);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"선택한 테이블은 사용중인 좌석입니다.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            grid.addView(bt);
        }
        bt_order_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select_id!=-1){
                    if(CurrentCartInfo.getCart().getCartInfoList().size()>1) {//주문 2개이상시
                        try {
                            List<JSONObject> list = new ArrayList<>();

                            for (int i = 0; i < CurrentCartInfo.getCart().getCartInfoList().size(); i++) {
                                String id = String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                                String store_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getStore_id());
                                String menu_id = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_id());
                                String menu_name = CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_name();
                                String menu_price = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price());
                                String menu_count = String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());
                                String seat_id = String.valueOf(select_id);

                                JSONObject object = new JSONObject();
                                object.put("id", id);
                                object.put("store_id", store_id);
                                object.put("menu_id", menu_id);
                                object.put("menu_name", menu_name);
                                object.put("menu_price", menu_price);
                                object.put("menu_count", menu_count);
                                object.put("seat_id", seat_id);
                                list.add(object);
                            }
                            //메뉴아이디, 메뉴 수량 넣어야 해
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            OrderAndSeatApi orderAndSeatApi = retrofit.create(OrderAndSeatApi.class);
                            orderAndSeatApi.getResult(list).enqueue(new Callback<OrderAndSeatResult>() {
                                @Override
                                public void onResponse(Call<OrderAndSeatResult> call, Response<OrderAndSeatResult> response) {
                                    Log.d("respone", "respone");
                                    if (response.isSuccessful()) {
                                        Log.d("respone is successful", "respone is successful");
                                        OrderAndSeatResult orderAndSeatResult = response.body();
                                        switch (orderAndSeatResult.getResult()) {
                                            case 1:
                                                OrderState.setOrder_id(orderAndSeatResult.getOreder_id());
                                                OrderState.setOrder_state(orderAndSeatResult.getOrder_state());
                                                Toast.makeText(getContext(), "주문이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                                ((NavigationnActivity) NavigationnActivity.mContext).replaceFragment(1);
                                                break;
                                            case 0:
                                                Toast.makeText(getContext(), "주문이 전송되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderAndSeatResult> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {//주문 1개시
                        try {
                                String id=String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                                String store_id=String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getStore_id());
                                String menu_id=String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_id());
                                String menu_name=CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_name();
                                String menu_price=String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_price());
                                String menu_count=String.valueOf(CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_count());
                                String seat_id=String.valueOf(select_id);
                                HashMap<String,String> input=new HashMap<>();
                                input.put("id",id);
                                input.put("store_id",store_id);
                                input.put("menu_id",menu_id);
                                input.put("menu_name",menu_name);
                                input.put("menu_price",menu_price);
                                input.put("menu_count",menu_count);
                                input.put("seat_id",seat_id);
                                Log.d("order","input= "+input);

                                Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            OrderAndSeatApi orderAndSeatApi=retrofit.create(OrderAndSeatApi.class);
                            orderAndSeatApi.getResult(input).enqueue(new Callback<OrderAndSeatResult>() {
                                @Override
                                public void onResponse(Call<OrderAndSeatResult> call, Response<OrderAndSeatResult> response) {
                                    Log.d("respone","respone");
                                    if(response.isSuccessful()){
                                        Log.d("respone is successful","respone is successful");
                                        OrderAndSeatResult orderAndSeatResult= response.body();
                                        switch (orderAndSeatResult.getResult()){
                                            case 1:
                                                OrderState.setOrder_id(orderAndSeatResult.getOreder_id());
                                                OrderState.setOrder_state(orderAndSeatResult.getOrder_state());
                                                Toast.makeText(getContext(),"주문이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                                ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(1);
                                                break;
                                            case 0:
                                                Toast.makeText(getContext(),"주문이 전송되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<OrderAndSeatResult> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else
                    Toast.makeText(getContext(),"테이블을 선택 해주세요.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        Log.d("click",""+v.getId());
        int id=v.getId();//현재 1번을 누르면 seat_checked[1]이 true가 되는 상태
        if(checked_count<max_count) {

            if (seat_checked[id] != true) {
                seat_checked[id]=true;
                v.setBackgroundColor(Color.BLUE);
                Toast.makeText(getContext(),id+"번 테이블을 선택 하셨습니다.",Toast.LENGTH_SHORT).show();
                checked_count++;
                select_id=id;
            }
            else{
                seat_checked[id]=false;
                checked_count--;
                v.setBackgroundColor(Color.WHITE);
                Toast.makeText(getContext(),id+"번 테이블 선택을 해제 하셨습니다.",Toast.LENGTH_SHORT).show();
                select_id=-1;
            }
        }
        else {
            if (seat_checked[id] != true)
                Toast.makeText(getContext(), "테이블 예약은 1개가 최대 입니다.", Toast.LENGTH_SHORT).show();
            else{
                checked_count--;
                seat_checked[id]=false;
                v.setBackgroundColor(Color.WHITE);
                select_id=-1;
            }
        }
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
