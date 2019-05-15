package com.example.reorder.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.GoogleMapActivity;
import com.example.reorder.Adapter.BookMarkStoreAdapter;
import com.example.reorder.R;
import com.example.reorder.Result.GetBookMarkResult;
import com.example.reorder.Adapter.StoreAdapter;
import com.example.reorder.Api.GetBookMarkApi;
import com.example.reorder.globalVariables.CurrentBookMarkStoreInfo;
import com.example.reorder.globalVariables.CurrentLocation;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.BookMarkStoreInfo;
import com.example.reorder.info.StoreInfo;
import com.google.android.gms.maps.model.LatLng;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements LocationListener, BeaconConsumer {
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

    private BeaconManager beaconManager;
    private Button bt_beacon;
    private TextView tv_beacon;
    private List<Beacon> beaconList=new ArrayList<>();
    private Button bt_near_store;
    private Button bt_bookmark;
    private Button bt_map;
    private ViewPager pager;
    private RecyclerView lv_bookmark_store;
    private RecyclerView lv_near_store;
    public RecyclerView.Adapter store_adapter;
    public RecyclerView.Adapter bookmark_store_adapter;
    private List<StoreInfo> currentStoreInfos;
    private List<BookMarkStoreInfo> currentBookMarkStoreList;
    private ImageButton bt_filter;
    public LocationListener locationListener;
    private static final int REQUEST_CODE_LOCATION=2;
    public LocationManager locationManager;
    String url= serverURL.getUrl();

    @SuppressLint("ServiceCast")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        bt_filter=view.findViewById(R.id.bt_filter);
        lv_bookmark_store=view.findViewById(R.id.lv_bookmark_store);
        lv_near_store=view.findViewById(R.id.lv_near_store);
        tv_beacon=view.findViewById(R.id.tv_beacon);
        bt_beacon=view.findViewById(R.id.bt_beacon);
        // 버튼이 클릭되면 textView 에 비콘들의 정보를 뿌린다.
        bt_beacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아래에 있는 handleMessage를 부르는 함수. 맨 처음에는 0초간격이지만 한번 호출되고 나면
                // 1초마다 불러온다.
                Log.d("2","2");
                handler.sendEmptyMessage(0);
            }
        });

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(getContext());
        // 여기가 중요한데, 기기에 따라서 setBeaconLayout 안의 내용을 바꿔줘야 하는듯 싶다.
        // 필자의 경우에는 아래처럼 하니 잘 동작했음.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        ///ibeacon 숫자도 해봤지만 작동 안됨 m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24

        // 비콘 탐지를 시작한다. 실제로는 서비스를 시작하는것.
        beaconManager.bind(this);
        Log.d("7","7");


        locationManager= (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {//위치 정보가 업데이트 시 동작
            @Override
            public void onLocationChanged(Location location) {
                double lat=location.getLatitude();
                double lng=location.getLongitude();
                Log.d("location",""+lat);
                Log.d("location",""+lng);
                Toast.makeText(getContext(),"위치: " +lat+"/"+lng,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        Location userlocation=getMyLocation();
        Log.d("my location",""+userlocation);
        if(userlocation!=null){
            double lat=userlocation.getLatitude();
            double lng=userlocation.getLongitude();
            LatLng mylocation=new LatLng(lat,lng);
        }

        //전체 store 리스트뷰 연결
        lv_near_store.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentStoreInfos = CurrentStoreInfo.getStore().getStoreInfoList();
        store_adapter = new StoreAdapter(currentStoreInfos,inflater.getContext());
        lv_near_store.setAdapter(store_adapter);




        bt_near_store = (Button) view.findViewById(R.id.bt_near_store);
        bt_near_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111","왜 안 돼?");
                lv_bookmark_store.setVisibility(View.INVISIBLE);
                lv_near_store.setVisibility(View.VISIBLE);
                Log.d("11111","왜 안 돼");

            }
        });

        bt_bookmark=(Button)view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    GetBookMarkApi getBookMarkApi = retrofit.create(GetBookMarkApi.class);
                    getBookMarkApi.getid(String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()))
                            .enqueue(new Callback<GetBookMarkResult>() {
                                @Override
                                public void onResponse(Call<GetBookMarkResult> call, Response<GetBookMarkResult> response) {
                                    if(response.isSuccessful())
                                    {
                                        GetBookMarkResult getBookMarkResult=response.body();
                                        switch (getBookMarkResult.getResult()){
                                            case 1://성공
                                                lv_bookmark_store.setVisibility(View.VISIBLE);
                                                lv_near_store.setVisibility(View.INVISIBLE);
                                                List<BookMarkStoreInfo> bookMarkStoreInfoList=getBookMarkResult.getBookMarkStoreInfoList();
                                                CurrentBookMarkStoreInfo.getBookMarkStore().setBookMarkStoreInfoList(bookMarkStoreInfoList);
                                                break;
                                            case 0://실패
                                                break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<GetBookMarkResult> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        bt_bookmark=(Button)view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final String id=String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                    Log.d("bt_bookmark",id);
                    GetBookMarkApi getBookMarkApi = retrofit.create(GetBookMarkApi.class);
                    getBookMarkApi.getid(id)
                            .enqueue(new Callback<GetBookMarkResult>() {
                                @Override
                                public void onResponse(Call<GetBookMarkResult> call, Response<GetBookMarkResult> response) {
                                    Log.d("bt_bookmark","respone");
                                    Log.d("bt_bookmark",""+response.body());
                                    if(response.isSuccessful())
                                    {
                                        Log.d("bt_bookmark","respone성공");
                                        GetBookMarkResult getBookMarkResult=response.body();
                                        switch (getBookMarkResult.getResult()){
                                            case 1://성공
                                                Log.d("bt_bookmark","body성공");
                                                lv_bookmark_store.setVisibility(View.VISIBLE);
                                                lv_near_store.setVisibility(View.INVISIBLE);
                                                Log.d("bt_bookmark","2");
                                                List<BookMarkStoreInfo> bookMarkStoreInfoList=getBookMarkResult.getBookMarkStoreInfoList();
                                                CurrentBookMarkStoreInfo.getBookMarkStore().setBookMarkStoreInfoList(bookMarkStoreInfoList);
                                                lv_bookmark_store.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
                                                currentBookMarkStoreList= CurrentBookMarkStoreInfo.getBookMarkStore().getBookMarkStoreInfoList();
                                                bookmark_store_adapter= new BookMarkStoreAdapter(currentBookMarkStoreList,inflater.getContext());
                                                lv_bookmark_store.setAdapter(bookmark_store_adapter);
                                                break;
                                            case 0://실패
                                                break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<GetBookMarkResult> call, Throwable t) {
                                    t.printStackTrace();
                                    Log.d("bt_bookmark", String.valueOf(call));
                                    Log.d("bt_bookmark","fail");
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("bt_bookmark","excep");
                }

            }
        });

        bt_map=(Button)view.findViewById(R.id.bt_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=store_adapter.getItemCount();
                Intent MapIntent=new Intent(getActivity(), GoogleMapActivity.class); //this 대신 getActivity() : 현재의 context받아올 수 있음
                MapIntent.putExtra("count",count);
                startActivity(MapIntent);

            }
        });

        bt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return view;
    }
    public void openDialog(){
        Dialog dialog=new Dialog();
        dialog.show(getFragmentManager(),"dialog");//
    }

    private Location getMyLocation(){
        Location currentlocation=null;
        if(ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(getContext()
                        ,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(),"권한을 허가해야 합니다.",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},this.REQUEST_CODE_LOCATION);
            getMyLocation();
        }
        else {
            String locationProvider=LocationManager.GPS_PROVIDER;
            //Toast.makeText(getContext(),"권한 요청 안 해도 돼.",Toast.LENGTH_SHORT).show();
            currentlocation=locationManager.getLastKnownLocation(locationProvider);
            if(currentlocation!=null){
                double lat=currentlocation.getLatitude();
                Log.d("location_lat",""+lat);
                double lng=currentlocation.getLongitude();
                Log.d("location_lng",""+lng);
                CurrentLocation.setLat(lat);
                CurrentLocation.setLng(lng);
                //Toast.makeText(getContext(),"권한 요청"+lat+"/"+lng,Toast.LENGTH_SHORT).show();
            }
        }
        return currentlocation;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void categoryChanged(List<StoreInfo> storeInfos){
        store_adapter = new StoreAdapter(storeInfos,getLayoutInflater().getContext());
        lv_near_store.setAdapter(store_adapter);
        Log.d("category","fm");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
        Log.d("6","6");
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            // 비콘이 감지되면 해당 함수가 호출된다. Collection<Beacon> beacons에는 감지된 비콘의 리스트가,
            // region에는 비콘들에 대응하는 Region 객체가 들어온다.
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.d("5","5");
                    beaconList.clear();
                    for (Beacon beacon : beacons) {
                        beaconList.add(beacon);
//                        if(beacon.getId3().equals(Identifier.parse("24000"))){
//                            Toast.makeText(getApplicationContext(),"깐뚜",Toast.LENGTH_SHORT).show();
//                        }else if(beacon.getId3().equals(Identifier.parse("23999"))) {
//                            Toast.makeText(getApplicationContext(),"자드",Toast.LENGTH_SHORT).show();
//                        }
//                        Log.d("beacon",beacon.getId3() +"");
//                        Log.d("beacon",beacon.getManufacturer()+"/" + beacon.getMeasurementCount() + "/" + beacon.getPacketCount() +"/" + beacon.getRssi() +"/" + beacon.getServiceUuid() +"/" + beacon.getTxPower());
                    }
                    Log.d("11",beaconList.toString());
                }
            }
            //if(beaconList.get(2).equals(beacons.getId3()))
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            tv_beacon.setText("");
            Log.d("1","1");
            // 비콘의 아이디와 거리를 측정하여 textView에 넣는다.
            for(Beacon beacon : beaconList){
                tv_beacon.append("ID : " + beacon.getId3() + " / " + "Distance : " + Double.parseDouble(String.format("%.3f", beacon.getDistance())) + "m\n");
                Log.d("3","3");
            }

            // 자기 자신을 1초마다 호출
            handler.sendEmptyMessageDelayed(0, 1000);
            Log.d("4","4");
        }
    };
}