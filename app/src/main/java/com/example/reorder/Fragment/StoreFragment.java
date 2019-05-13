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
import android.widget.Toast;

import com.example.reorder.Adapter.MenuAdapter;
import com.example.reorder.Api.BookMarkApi;
import com.example.reorder.R;
import com.example.reorder.Result.BookMarkResult;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.StoreMenuInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
    String url= serverURL.getUrl();


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

        bt_bookmark=view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = CurrentUserInfo.getUser().getUserInfo().getId();
                int store_id = CurrentSelectStore.getSt_id();
                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("id", String.valueOf(id));
                    input.put("store_id", String.valueOf(store_id));
                    input.put("store_name", CurrentSelectStore.getSt_name());
                    input.put("store_category", CurrentSelectStore.getSt_category());
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                    BookMarkApi retrofitApi = retrofit.create(BookMarkApi.class);
                    retrofitApi.postBookmarkInfo(input).enqueue(new Callback<BookMarkResult>() {
                        @Override
                        public void onResponse(Call<BookMarkResult> call, Response<BookMarkResult> response) {
                            if(response.isSuccessful()) {
                                BookMarkResult map=response.body();
                                if(map!=null) {
                                    switch (map.getResult()) {
                                        case 0://삭제
                                            Toast.makeText(getActivity(),"북마크를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            Toast.makeText(getActivity(),"북마크를 등록했습니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<BookMarkResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }


}