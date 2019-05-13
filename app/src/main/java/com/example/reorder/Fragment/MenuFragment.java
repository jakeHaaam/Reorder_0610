package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.R;
import com.example.reorder.Result.JoinResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.globalVariables.CurrentMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

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
    String url= serverURL.getUrl();

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
        tv_menu_name.setText(CurrentMenuInfo.getMenu_name());

        tv_menu_price=view.findViewById(R.id.menu_price);
        tv_menu_price.setText(Integer.toString(CurrentMenuInfo.getMenu_price()));
        tv_menu_count=view.findViewById(R.id.menu_count);

        tv_total_price=view.findViewById(R.id.tv_total_price);
        tv_total_price.setText(Integer.toString(CurrentMenuInfo.getMenu_price()));

        bt_up=view.findViewById(R.id.bt_up);
        bt_cancle=view.findViewById(R.id.bt_cancle);
        bt_down=view.findViewById(R.id.bt_down);
        bt_ok=view.findViewById(R.id.bt_go_cart);
        count=1;
        total=0;
        //전달 받은 price를 밑에 price에 넣어주고 수량 증가,감소시 반영은 버튼 안에 구현
        price= CurrentMenuInfo.getMenu_price();
        tv_menu_count.setText(String.valueOf(count));

        bt_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<10){
                    count++;
                    tv_menu_count.setText(String.valueOf(count));
                    total=count*price;
                    tv_total_price.setText(String.valueOf(total));
                    CurrentMenuInfo.setMenu_count(count);
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
                    CurrentMenuInfo.setMenu_count(count);
                }
                else
                    Toast.makeText(getContext(),"수량은 최소 1개입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationnActivity)NavigationnActivity.mContext).onBackPressed();
            }
        });


        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("menuadapter",""+CurrentMenuInfo.getMenu_id()+" /"+CurrentMenuInfo.getMenu_name()+" /"+CurrentMenuInfo.getMenu_price()+" /"+CurrentMenuInfo.getMenu_count());

                int id = CurrentUserInfo.getUser().getUserInfo().getId();
                int menu_id=CurrentMenuInfo.getMenu_id();
                final String menu_name=CurrentMenuInfo.getMenu_name();
                int menu_price=CurrentMenuInfo.getMenu_price();
                int menu_count=CurrentMenuInfo.getMenu_count();
                int store_id=CurrentMenuInfo.getStoreinfo_id();

                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("id", String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                    input.put("menu_id", String.valueOf(menu_id));
                    input.put("menu_name", menu_name);
                    input.put("menu_price", String.valueOf(menu_price));
                    input.put("menu_count", String.valueOf(menu_count));
                    input.put("store_id",String.valueOf(store_id+1));

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create()).build();

                    CartSetApi cartSetApi = retrofit.create(CartSetApi.class);

                    cartSetApi.setUserCartInfo(input).enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                            if (response.isSuccessful()) {
                                JoinResult map = response.body();
                                if (map!=null) {
                                    switch (map.getResult()) {
                                        case 1:
                                            Toast.makeText(getContext(),"장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();
                                            ((NavigationnActivity)NavigationnActivity.mContext).onBackPressed();
                                            break;
                                        case 0:
                                            Toast.makeText(getContext(),"실패", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 2://다른 매장의 제품을 장바구니에 넣으려고 할 시.
                                            Toast.makeText(getContext(),
                                                    "장바구니에는 1개의 매장의 제품들만 담을 수 있습니다."
                                                    , Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
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