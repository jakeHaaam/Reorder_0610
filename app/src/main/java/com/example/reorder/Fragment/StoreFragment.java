package com.example.reorder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.reorder.Adapter.MenuAdapter;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.info.StoreMenuInfo;

import java.util.List;


public class StoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rv_menu;
    private RecyclerView.Adapter menu_adapter;
    private TextView tv_store_name;
    private List<StoreMenuInfo> currentStoreMenuInfo;
    private Button bt_bookmark;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public StoreFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
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
        View view=inflater.inflate(R.layout.fragment_store, container, false);
        bt_bookmark=view.findViewById(R.id.bt_bookmark);
        tv_store_name=view.findViewById(R.id.tv_store_name);
        tv_store_name.setText(CurrentSelectStore.getSt_name());

        rv_menu=view.findViewById(R.id.rv_menu);
        rv_menu.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentStoreMenuInfo=CurrentStoreMenuInfo.getStoreMenu().getStoreMenuInfoList();
        menu_adapter=new MenuAdapter(currentStoreMenuInfo,inflater.getContext());
        rv_menu.setAdapter(menu_adapter);

        return view;
    }


}