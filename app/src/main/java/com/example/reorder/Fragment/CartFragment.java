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

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.Adapter.CartAdapter;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.info.CartInfo;

import java.util.List;

public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button bt_cart_order;
    private RecyclerView rv_cart;
    private TextView tv_cart_total_price;
    private List<CartInfo> currentCartInfo;
    private RecyclerView.Adapter cart_adapter;

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
        Log.d("cart","카트에 담은 총 금액:"+sumTotalPrice());
        tv_cart_total_price=view.findViewById(R.id.tv_cart_total_price);
        tv_cart_total_price.setText(Integer.toString(sumTotalPrice()));
        rv_cart=view.findViewById(R.id.rv_cart);
        rv_cart.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentCartInfo=CurrentCartInfo.getCart().getCartInfoList();
        cart_adapter=new CartAdapter(currentCartInfo,inflater.getContext());
        rv_cart.setAdapter(cart_adapter);

        bt_cart_order=view.findViewById(R.id.bt_cart_order);
        bt_cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(5);
            }
        });
        return view;
    }

    public int sumTotalPrice(){
        int totalprice=0;
        for(int i=0;i< CurrentCartInfo.getCart().getCartInfoList().size();++i){
            totalprice+=(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_price())
                    *(CurrentCartInfo.getCart().getCartInfoList().get(i).getMenu_count());
        }
        return totalprice;
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
