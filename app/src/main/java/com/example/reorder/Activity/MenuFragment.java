package com.example.reorder.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.info.CartInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_menu_name;
    private TextView tv_menu_price;
    private TextView tv_menu_count;
    private Button bt_down;
    private Button bt_up;
    private Button bt_ok;
    private Button bt_cancle;
    private TextView tv_total_price;
    private int count;
    private int price;
    private int total;
    private List<CartInfo> cartInfoList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View view=inflater.inflate(R.layout.fragment_menu, container, false);
        tv_menu_name=view.findViewById(R.id.menu_name);
        tv_menu_price=view.findViewById(R.id.menu_price);
        tv_menu_count=view.findViewById(R.id.menu_count);
        tv_total_price=view.findViewById(R.id.tv_total_price);
        bt_up=view.findViewById(R.id.bt_up);
        bt_down=view.findViewById(R.id.bt_down);
        bt_ok=view.findViewById(R.id.bt_go_cart);
        bt_cancle=view.findViewById(R.id.bt_cancle);
        count=1;
        total=0;
        //전달 받은 price를 밑에 price에 넣어주고 수량 증가,감소시 반영은 버튼 안에 구현
        price=3000;
        tv_menu_count.setText(String.valueOf(count));

        bt_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=10){
                    count++;
                    tv_menu_count.setText(String.valueOf(count));
                    total=count*price;
                    tv_total_price.setText(String.valueOf(total));
                }
                else
                    Toast.makeText(getContext(),"수량은 10개가 최대입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1){
                    count--;
                    tv_menu_count.setText(String.valueOf(count));
                    total=count*price;
                    tv_total_price.setText(String.valueOf(total));
                }
                else
                    Toast.makeText(getContext(),"수량은 최소 1개입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);//취소 버튼 시 이전 storefragment로 이동
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //->storeFragment로 이동 and 입력한 상품과 수량을 장바구니로 보내는 내용 작성
                cartInfoList.add(new CartInfo(1,tv_menu_name.toString(), price,count));
                CurrentCartInfo.getCart().setCartInfoList(cartInfoList);
                System.exit(0);
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