package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.Adapter.CartAdapter;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.Api.DeleteCartApi;
import com.example.reorder.R;
import com.example.reorder.Result.CartResult;
import com.example.reorder.Result.DeleteCartResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSelectCartInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.reorder.Adapter.CartAdapter.selected;
import static com.example.reorder.Fragment.OrderFragment.used_mileage;

public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TextView tv_cart_total_price;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button bt_delete;
    private Button bt_cart_order;
    private RecyclerView rv_cart;
    //private TextView tv_cart_total_price;
    private List<CartInfo> currentCartInfo;
    private RecyclerView.Adapter cart_adapter;
    private List<CartInfo> currentSelectCartInfo;
    String url= serverURL.getUrl();

    private OnFragmentInteractionListener mListener;


    public CartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

        View view =inflater.inflate(R.layout.fragment_cart, container, false);
        tv_cart_total_price=view.findViewById(R.id.tv_cart_total_price);
        tv_cart_total_price.setText(Integer.toString(0));
        rv_cart=view.findViewById(R.id.rv_cart);
        rv_cart.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentCartInfo=CurrentCartInfo.getCart().getCartInfoList();
        cart_adapter=new CartAdapter(currentCartInfo,inflater.getContext());
        rv_cart.setAdapter(cart_adapter);

        bt_delete=view.findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id= String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                    DeleteCartApi deleteCartApi = retrofit.create(DeleteCartApi.class);
                    deleteCartApi.getid(id).enqueue(new Callback<DeleteCartResult>() {
                        @Override
                        public void onResponse(Call<DeleteCartResult> call, Response<DeleteCartResult> response) {
                            if (response.isSuccessful()) {
                                DeleteCartResult deleteCartResult = response.body();
                                if (deleteCartResult != null) {
                                    switch (deleteCartResult.getResult()) {
                                        case 1:
                                            Log.d("delete", "카트 삭제 성공");
                                            Toast.makeText(getContext(),"장바구니의 모든 제품이 삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                                            ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(1);
                                            break;
                                        case 0:
                                            Log.d("delete", "fail");
                                            break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<DeleteCartResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        bt_cart_order=view.findViewById(R.id.bt_cart_order);
        bt_cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                for(int i=0;i<CurrentCartInfo.getCart().getCartInfoList().size();i++){
                    if(selected[i]){
                        count++;
                    }
                }

                if(CurrentCartInfo.getCart().getCartInfoList().size()==0){
                    Toast.makeText(getContext(), "장바구니에 담은 제품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(count==0) {
                    Toast.makeText(getContext(), "선택된 제품이 없습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    CurrentSelectCartInfo.getCart().setCartInfoList(CartAdapter.cartInfoList);
                    ((NavigationnActivity) NavigationnActivity.mContext).replaceFragment(5);
                    used_mileage=0;
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
            //throw new RuntimeException(context.toString()
            //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}