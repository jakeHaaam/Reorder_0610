package com.example.reorder.Activity;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.reorder.R;

import java.util.zip.Inflater;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private Button bt_near_store;
    private Button bt_bookmark;
    private Button bt_map;
    private ViewPager pager;
    private ListView lv_bookmark_store;
    private ListView lv_near_store;
    private Button bt_cart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home,container,false);

        lv_bookmark_store=view.findViewById(R.id.lv_bookmark_store);
        lv_near_store=view.findViewById(R.id.lv_near_store);


        bt_near_store = (Button) view.findViewById(R.id.bt_near_store);
        bt_near_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111","왜 안 돼?");
                lv_bookmark_store.setVisibility(View.INVISIBLE);
                lv_near_store.setVisibility(View.VISIBLE);
                Log.d("11111","왜 안 돼");
                //pager.setCurrentItem(0);
            }
        });

        bt_bookmark=(Button)view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_bookmark_store.setVisibility(View.VISIBLE);
                lv_near_store.setVisibility(View.INVISIBLE);
               // pager.setCurrentItem(1);
            }
        });

        bt_map=(Button)view.findViewById(R.id.bt_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MapIntent=new Intent(getActivity(),GoogleMapActivity.class); //this 대신 getActivity() : 현재의 context받아올 수 있음
                startActivity(MapIntent);
               // pager.setCurrentItem(2);
            }
        });


        //pager.setCurrentItem(0);
        return view;
    }



}
