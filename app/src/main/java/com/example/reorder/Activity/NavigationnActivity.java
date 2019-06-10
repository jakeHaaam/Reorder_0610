package com.example.reorder.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Api.FragmentReplaceable;

import com.example.reorder.Api.LiveOrderStateApi;
import com.example.reorder.Api.PastOrderApi;
import com.example.reorder.Api.RetrofitApi;
import com.example.reorder.Api.SeatApi;
import com.example.reorder.Api.StoreIdApi;
import com.example.reorder.Fragment.CartFragment;
import com.example.reorder.Fragment.HomeFragment;
import com.example.reorder.Fragment.LiveOrderStateFragment;
import com.example.reorder.Fragment.MenuFragment;
import com.example.reorder.Fragment.OrderFragment;
import com.example.reorder.Fragment.PastOrderFragment;
import com.example.reorder.Fragment.SeatReserveFragment;
import com.example.reorder.Fragment.SeeTableFragment;
import com.example.reorder.Fragment.StoreFragment;
import com.example.reorder.R;
import com.example.reorder.Result.CartResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.Result.LiveOrderStateResult;
import com.example.reorder.Result.LoginResult;
import com.example.reorder.Result.PastOrderResult;
import com.example.reorder.Result.SeatResult;
import com.example.reorder.Result.StoreIdResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentLiveOrderStateInfo;
import com.example.reorder.globalVariables.CurrentPastOrderInfo;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.CurrentUsingSeatInfo;
import com.example.reorder.info.RenewPastOrderInfo;
import com.example.reorder.globalVariables.SeatOrderState;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;
import com.example.reorder.info.LiveOrderStateInfo;
import com.example.reorder.info.PastOrderInfo;
import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.StoreMenuInfo;
import com.example.reorder.info.UserInfo;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationnActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable, BeaconConsumer {

    private Vibrator vibrator;
    public static boolean onsu_ddokbokki_seat_check;
    public static boolean isac_seat_check;
    public static int seat_count=0;
    public static int bea_st_id;//온수떡볶이1번 이삭 2번
    public static boolean bool_beacon;
    private BeaconManager beaconManager;
    private List<Beacon> beaconList=new ArrayList<>();
    public Fragment homeFragment= new HomeFragment();
    private Fragment storeFragment;
    private Fragment SeeTableFragment;
    private Fragment CartFragment;
    private Fragment OrderFragment;
    private Fragment SeatReserveFragment;
    private Fragment MenuFragment;
    private Fragment pastOrderFragment=new PastOrderFragment();
    private Fragment LiveOrderStateFragment=new LiveOrderStateFragment();
    private ImageButton bt_cart;
    public static Context mContext;
    private ImageButton bt_refresh;
    String url= serverURL.getUrl();


    public void categoryChanged(List<StoreInfo> storeInfos){
        HomeFragment hf = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        hf.categoryChanged(storeInfos);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        //homeFragment = new HomeFragment();
        storeFragment=new StoreFragment();
        CartFragment=new CartFragment();
        OrderFragment=new OrderFragment();
        SeatReserveFragment=new SeatReserveFragment();
        MenuFragment=new MenuFragment();
        SeeTableFragment=new SeeTableFragment();
        setDefaultFragment();

        if(bool_beacon){
            onBeaconServiceConnect();
        }

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(getApplication());
        //기기에 따라서 setBeaconLayout 안의 내용을 바꿔야 함.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        // 비콘 탐지 시작. 실제로는 서비스를 시작하는것.
        beaconManager.bind(this);

        //구글맵에서 받아온 st_name를 현재 스토어에 저장되어있는 st_name들과 비교해서 해당 store로 이동 구현
        Intent intent=getIntent();
        String map_st_name=intent.getStringExtra("map");
        if(map_st_name!=null){
            for(int i=0;i< CurrentStoreInfo.getStore().getStoreInfoList().size();i++){
                String st_name=CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_name();
                if(map_st_name.equals(st_name)){
                    CurrentSelectStore.setId(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getId());
                    CurrentSelectStore.setSt_id(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_id());
                    CurrentSelectStore.setSt_name(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_name());
                    CurrentSelectStore.setSt_category(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_category());

                    String st_id=String.valueOf(CurrentSelectStore.getSt_id());

                    try{
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        StoreIdApi storeIdApi= retrofit.create(StoreIdApi.class);
                        storeIdApi.getStore_id(st_id).enqueue(new Callback<StoreIdResult>() {
                            @Override
                            public void onResponse(Call<StoreIdResult> call, Response<StoreIdResult> response) {
                                if (response.isSuccessful()){
                                    StoreIdResult storeIdResult=response.body();
                                    if(storeIdResult!=null) {
                                        switch (storeIdResult.getResult()) {
                                            case 1://성공
                                                List<StoreMenuInfo> storeMenuInfo= storeIdResult.getStoreMenuInfo();
                                                CurrentStoreMenuInfo.getStoreMenu().setStoreMenuInfoList(storeMenuInfo);
                                                replaceFragment(2);
                                                break;
                                            case 0://실패
                                                break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<StoreIdResult> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        bt_cart=findViewById(R.id.bt_cart);
        bt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id= CurrentUserInfo.getUser().getUserInfo().getId();
                try {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                    CartSetApi cartSetApi = retrofit.create(CartSetApi.class);
                    cartSetApi.getId(id).enqueue(new Callback<CartResult>() {
                        @Override
                        public void onResponse(Call<CartResult> call, Response<CartResult> response) {
                            if (response.isSuccessful()) {
                                CartResult cartResult = response.body();
                                if (cartResult != null) {
                                    switch (cartResult.getResult()) {
                                        case 1:
                                            List<CartInfo> cartInfo = cartResult.getCartInfo();
                                            CurrentCartInfo.getCart().setCartInfoList(cartInfo);//저장
                                            replaceFragment(4);
                                            break;
                                        case 0:
                                            break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<CartResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mContext=this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view=navigationView.getHeaderView(0);
        TextView tv_nav_nicname=(TextView)nav_header_view.findViewById(R.id.tv_nav_nicname);
        tv_nav_nicname.setText(CurrentUserInfo.getUser().getUserInfo().getClient_id());

        // 마일리지 추가
        final TextView tv_mileage=(TextView)nav_header_view.findViewById(R.id.tv_mileage);
        tv_mileage.setText(CurrentUserInfo.getUser().getUserInfo().getClient_mileage()+"");

        bt_refresh=nav_header_view.findViewById(R.id.bt_refresh);
        bt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("client_id",CurrentUserInfo.getUser().getUserInfo().getClient_id());
                    input.put("client_password", LoginActivity.refresh_password);
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                    retrofitApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            if (response.isSuccessful()) {
                                LoginResult map = response.body();
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case -1:
                                            Toast.makeText(mContext,"잘못된 비밀번호 입니다.",Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(mContext,"가입되지 않은 이메일입니다.",Toast.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            UserInfo userinfo = map.getUser();
                                            CurrentUserInfo.getUser().setUserInfo(userinfo);
                                            tv_mileage.setText(CurrentUserInfo.getUser().getUserInfo().getClient_mileage()+"");
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragment==homeFragment){

            }else if(fragment==OrderFragment){
                Toast.makeText(mContext,"주문시 뒤로가기 버튼을 누를 수 없습니다.",Toast.LENGTH_SHORT).show();
            }else
            {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(1);
        }
        else if(id==R.id.nav_table_out){//테이블 사용 안함 클릭시
            if(CurrentUsingSeatInfo.getSeat_id()!=0) {//좌석 다 이용시 0으로 초기화해야함
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                builder.setTitle("테이블 비움 처리")
                        .setMessage("사용중이신 테이블을 다 사용하셨습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(CurrentUsingSeatInfo.getSeat_id()==0){
                                    Toast.makeText(getApplication(),"사용중이신 좌석이 없습니다.",Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    String seat_id= String.valueOf(CurrentUsingSeatInfo.getSeat_id());//고유번호 아님
                                    HashMap<String,String> input= new HashMap<>();
                                    input.put("client_id",String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                                    input.put("seat_id",seat_id);
                                    input.put("store_id", CurrentUserInfo.getUser().getUserInfo().getClient_store_id());
                                    Log.d("test",""+"/"+input.get("client_id")+"/"+input.get("seat_id")+"/"+input.get("store_id"));
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(url)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    SeatApi seatApi = retrofit.create(SeatApi.class);
                                    seatApi.setUserCancle(input).enqueue(new Callback<SeatResult>() {
                                        @Override
                                        public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                            if (response.isSuccessful()) {
                                                SeatResult seatResult = response.body();
                                                switch (seatResult.getResult()) {
                                                    case 1://성공
                                                        CurrentUsingSeatInfo.setSeat_id(0);
                                                        bool_beacon=false;
                                                        Toast.makeText(getApplication(), "사용중인 테이블을 비움처리 하였습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case 0://실패
                                                        Toast.makeText(getApplication(), "사용중인 테이블 비움처리 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<SeatResult> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("아니오", null);
                builder.show();
            }else {
                Toast.makeText(getApplication(),"사용중이신 테이블이 없습니다.",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_logout) {//로그아웃 버튼 클릭시
            CurrentUserInfo.getUser().getUserInfo().setId(0);
            CurrentUserInfo.getUser().getUserInfo().setClient_id(null);
            CurrentUserInfo.getUser().getUserInfo().setClient_mileage(0);
            CurrentUserInfo.getUser().getUserInfo().setClient_password(null);
            Toast.makeText(this,"로그아웃 되었습니다",Toast.LENGTH_SHORT).show();
            startActivity(new Intent (NavigationnActivity.this,LoginActivity.class));
        }

        else if(id==R.id.nav_live_order_state){//실시간 주문 현황 버튼 클릭시
            try{
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LiveOrderStateApi liveOrderStateApi = retrofit.create(LiveOrderStateApi.class);
                liveOrderStateApi.getLiveOrderState(String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()))
                        .enqueue(new Callback<LiveOrderStateResult>() {
                            @Override
                            public void onResponse(Call<LiveOrderStateResult> call, Response<LiveOrderStateResult> response) {
                                if(response.isSuccessful()){
                                    LiveOrderStateResult liveOrderStateResult=response.body();
                                    switch (liveOrderStateResult.getResult()){
                                        case 1://성공
                                            List<LiveOrderStateInfo> liveOrderStateInfos=liveOrderStateResult.getLiveOrderStateInfoList();
                                            CurrentLiveOrderStateInfo.getLiveOrderState().setLiveOrderStateInfos(liveOrderStateInfos);
                                            replaceFragment(8);
                                            break;
                                        case 0://실패or 내역 없음
                                            Toast.makeText(getApplication(),"현재 준비중인 제품이 없습니다.주문 내역을 확인해 보세요.",Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<LiveOrderStateResult> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(id==R.id.nav_past_order){//과거 주문내역 버튼 클릭시
            //레트로핏-서버에 client_id를 전송->서버는 order테이블에 해당 client_id가 들어있는 data 모두 전송(time,state)
            try{
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PastOrderApi pastOrderApi = retrofit.create(PastOrderApi.class);
                pastOrderApi.getPastOrder(String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()))
                        .enqueue(new Callback<PastOrderResult>() {
                            @Override
                            public void onResponse(Call<PastOrderResult> call, Response<PastOrderResult> response) {
                                if(response.isSuccessful()){
                                    PastOrderResult pastOrderResult=response.body();
                                    switch (pastOrderResult.getResult()){
                                        case 1://성공
                                            List<PastOrderInfo> pastOrderInfos=pastOrderResult.getPastOrderInfos();
                                            CurrentPastOrderInfo.getPastOrder().setPastOrderInfoList(pastOrderInfos);
                                            RenewPastOrderInfo.getRenewPast().setList(pastOrderInfos);
                                            String orTime = "",name = "",count = "",mileage = "",or_serial="",st_name = "";
                                            int allprice=0;
                                            int where=0;
                                            for (int i=0;i<pastOrderInfos.size();i++){
                                                if(i==0) {//인덱스가 0인 곳
                                                    or_serial=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial();//한 order당 1개만 담음
                                                    orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                                                    name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                    count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                    mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                                                    st_name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getStore_name();
                                                    allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                            Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
                                                    if(pastOrderInfos.size()==1){//지금까지 주문 내역이 1개만 있으면 이걸 달아줘야 해
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setOrder_serial(or_serial);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setOrder_date(orTime);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setMenu_name(name);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setMenu_count(count);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setUsed_mileage(mileage);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setStore_name(st_name);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(i).setMenu_price(String.valueOf(allprice));
                                                        //where++;
                                                    }else if(pastOrderInfos.size()!=1&&
                                                            !or_serial.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i+1).getOrder_serial())) {
                                                        //다음 주문번호랑 다르면 뷰에 달고
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_serial(or_serial);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_date(orTime);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_name(name);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_count(count);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setUsed_mileage(mileage);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setStore_name(st_name);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_price(String.valueOf(allprice));
                                                        orTime = "";
                                                        name = "";
                                                        count = "";
                                                        mileage = "";
                                                        or_serial="";
                                                        st_name="";
                                                        allprice = 0;//초기화
                                                        where++;
                                                    }else if(pastOrderInfos.size()!=1&&
                                                            or_serial.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i+1).getOrder_serial())){//뒤에 있는 아이템이랑 같은 주문이다
                                                        where=i;
                                                    }
                                                } else if(0<i&&i<pastOrderInfos.size()-1){//인덱스 1부터 마지막 인덱스 전까지
                                                    if(or_serial.equals(""))//초기화 후 비워져있으면
                                                    {
                                                        or_serial=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial();//한 order당 1개만 담음
                                                        orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                                                        name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                        count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                        mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                                                        st_name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getStore_name();
                                                        allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                                Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
                                                    }
                                                    //현재 담겨있는 오더시리얼이랑 지금보는 item의 오더시리얼이 같으면 같은 곳에 담아라
                                                    else if(or_serial.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial())){
                                                        name+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                        count+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                        allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                                Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());

                                                            //뒤랑 비교했는데 오더 serial이 다르면
                                                            if (!or_serial.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i + 1).getOrder_serial())) {
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_serial(or_serial);
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_date(orTime);
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_name(name);
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_count(count);
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setUsed_mileage(mileage);
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_price(String.valueOf(allprice));
                                                                RenewPastOrderInfo.getRenewPast().getList().get(where).setStore_name(st_name);
                                                                orTime = "";
                                                                name = "";
                                                                count = "";
                                                                mileage = "";
                                                                or_serial="";
                                                                st_name="";
                                                                allprice = 0;//초기화
                                                                where++;
                                                            }
                                                    }else if(!or_serial.equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial())){
                                                        //담겨져있는거랑 현재 오더랑 다른거면
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_serial(or_serial);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_date(orTime);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_name(name);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_count(count);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setUsed_mileage(mileage);
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_price(String.valueOf(allprice));
                                                        RenewPastOrderInfo.getRenewPast().getList().get(where).setStore_name(st_name);
                                                        where++;
                                                        allprice=0;

                                                        or_serial=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial();//한 order당 1개만 담음
                                                        orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                                                        name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                        count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                        mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                                                        st_name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getStore_name();
                                                        allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                                Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());

                                                    }

                                                }else if(i!=0&&i==pastOrderInfos.size()-1){//마지막 순서이면
                                                    if(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial()
                                                            .equals(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i-1).getOrder_serial())){//이전 아이템이랑 같은 주문이면
                                                        name+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                        count+="\n" + CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                        allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                                Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
                                                    }else {//혼자만의 주문이면
                                                        allprice=0;
                                                        or_serial=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_serial();//한 order당 1개만 담음
                                                        orTime=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getOrder_date();//한 order당 1개만 담음
                                                        name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_name();
                                                        count=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count();
                                                        mileage=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getUsed_mileage();//한 order당 1개만 담음
                                                        st_name=CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getStore_name();
                                                        allprice+=Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_count()) *
                                                                Integer.parseInt(CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList().get(i).getMenu_price());
                                                    }
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_serial(or_serial);
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setOrder_date(orTime);
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_name(name);
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_count(count);
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setUsed_mileage(mileage);
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setMenu_price(String.valueOf(allprice));
                                                    RenewPastOrderInfo.getRenewPast().getList().get(where).setStore_name(st_name);
                                                }
                                            }
                                            if(pastOrderInfos.size()>1) {
                                                while (where + 1 != pastOrderInfos.size()) {
                                                    RenewPastOrderInfo.getRenewPast().getList().remove(where + 1);
                                                }
                                            }
                                            replaceFragment(9);
                                            break;
                                        case 0://실패or 내역 없음(1번도 주문 안 했을 때)
                                            Toast.makeText(getApplication(),"주문하신 내역이 없습니다.",Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PastOrderResult> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDefaultFragment(){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,homeFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void replaceFragment(int fragmentId){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if(fragmentId==1){
            transaction.replace(R.id.container,homeFragment);
        }else if (fragmentId==2){
            transaction.replace(R.id.container, storeFragment);
        }else if (fragmentId==3){
            transaction.replace(R.id.container, SeeTableFragment);
        } else if (fragmentId==4){
            transaction.replace(R.id.container, CartFragment);
        }else if (fragmentId==5) {
            transaction.replace(R.id.container, OrderFragment);
        }else if (fragmentId==6) {
            transaction.replace(R.id.container, SeatReserveFragment);
        }else if (fragmentId==7) {
            transaction.replace(R.id.container, MenuFragment);
        }else if (fragmentId==8) {
            transaction.replace(R.id.container, LiveOrderStateFragment);
        }else if (fragmentId==9) {
            transaction.replace(R.id.container, pastOrderFragment);
        }

        transaction.addToBackStack(null);//뒤로가기라는 스텍에 계속 저장중
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            // 비콘이 감지되면 해당 함수가 호출된다. Collection<Beacon> beacons에는 감지된 비콘의 리스트가,
            // region에는 비콘들에 대응하는 Region 객체가 들어온다.
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if(bool_beacon) {
                    if (beacons.size() > 0) {
                        beaconList.clear();
                        for (Beacon beacon : beacons) {
                            beaconList.add(beacon);
                            if(bea_st_id==1) {
                                if (beacon.getId3().toString().equals("24000")) {//온떡
                                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(1000);
                                    try {
                                        String seat_id= String.valueOf(SeatOrderState.getId());
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(url)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        SeatApi seatApi=retrofit.create(SeatApi.class);
                                        seatApi.setSeat(seat_id).enqueue(new Callback<SeatResult>() {
                                            @Override
                                            public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                if(response.isSuccessful()){
                                                    SeatResult seatResult=response.body();
                                                    switch (seatResult.getResult()){
                                                        case 1://성공
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                            builder.setTitle("테이블 확정 완료")
                                                                    .setMessage("예약하신 테이블이 확정 되었습니다. 1시간 이상 자리를 비우시게 되면 테이블이 자동 비움처리됩니다.")
                                                                    .setPositiveButton("확인", null);
                                                            builder.show();
                                                            onsu_ddokbokki_seat_check=true;
                                                            Toast.makeText(getApplication(),"예약하신 테이블이 확정 되었습니다. 1시간 이상 자리를 비우시게 되면 테이블이 자동 비움처리됩니다.",Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 0://실패
                                                            Toast.makeText(getApplication(),"확정오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<SeatResult> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    bool_beacon = false;
                                    break;
                                }
                            }else if(bea_st_id==2){
                                if (beacon.getId3().toString().equals("23999")){//이삭
                                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(1000);
                                    try {
                                        String seat_id= String.valueOf(SeatOrderState.getId());
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(url)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        SeatApi seatApi=retrofit.create(SeatApi.class);
                                        seatApi.setSeat(seat_id).enqueue(new Callback<SeatResult>() {
                                            @Override
                                            public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                if(response.isSuccessful()){
                                                    SeatResult seatResult=response.body();
                                                    switch (seatResult.getResult()){
                                                        case 1://성공
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                            builder.setTitle("테이블 확정 완료")
                                                                    .setMessage("예약하신 테이블이 확정 되었습니다. 1시간 이상 자리를 비우시게 되면 테이블이 자동 비움처리됩니다.")
                                                                    .setPositiveButton("확인", null);
                                                            builder.show();
                                                            isac_seat_check=true;
                                                            Toast.makeText(getApplication(),"예약하신 테이블이 확정 되었습니다. 1시간 이상 자리를 비우시게 되면 테이블이 자동 비움처리됩니다.",Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 0://실패
                                                            Toast.makeText(getApplication(),"확정오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<SeatResult> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    bool_beacon=false;
                                    break;
                                }
                            }
                        }
                    }
                }

                beaconList.clear();
                for (Beacon beacon : beacons) {
                    beaconList.add(beacon);
                }
                if(onsu_ddokbokki_seat_check){//온수떡볶이를 사용중인거면
                    boolean check=false;
                    if(beaconList.size()==0) {
                        seat_count++;
                        if(seat_count>=30) {
                            onsu_ddokbokki_seat_check = false;//좌석 사용이 끝났으니 false
                            try {
                                String seat_id= String.valueOf(SeatOrderState.getId());
                                HashMap<String,String> input= new HashMap<>();
                                input.put("client_id",String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                                input.put("seat_id",seat_id);
                                input.put("store_id", CurrentUserInfo.getUser().getUserInfo().getClient_store_id());
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(url)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                SeatApi seatApi = retrofit.create(SeatApi.class);
                                seatApi.setAutoCancle(input).enqueue(new Callback<SeatResult>() {

                                    @Override
                                    public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                        if (response.isSuccessful()) {
                                            SeatResult seatResult = response.body();
                                            switch (seatResult.getResult()) {
                                                case 1://성공
                                                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                                    vibrator.vibrate(1000);
                                                    bool_beacon=false;
                                                    CurrentUsingSeatInfo.setSeat_id(0);//자리 사용중이지 않게 초기화
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                    builder.setTitle("테이블 비움 처리")
                                                            .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                            .setPositiveButton("확인", null);
                                                    builder.show();
                                                    break;
                                                case 0://실패
                                                    Toast.makeText(getApplication(), "테이블 자동 취소오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SeatResult> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        for (int i = 0; i < beaconList.size(); i++) {
                            if (beaconList.get(i).getId3().toString().equals("24000")) {//감지된 비콘리스트에서 해당매장의 비콘의id3가 있으면
                                check = true;
                                break;
                            } else {//감지된 비콘 리스트에서 해당 매장의 비콘의id3가 없으면
                                check = false;
                            }
                        }
                        if (check) {
                            seat_count = 0;
                        } else {
                            if (seat_count >= 30) {//비콘감지가 안된게 1시간이 지나면
                                onsu_ddokbokki_seat_check = false;//좌석 사용이 끝났으니 false
                                //자리 없애기 retofit
                                try {
                                    String seat_id= String.valueOf(SeatOrderState.getId());
                                    HashMap<String,String> input= new HashMap<>();
                                    input.put("client_id",String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                                    input.put("seat_id",seat_id);
                                    input.put("store_id", CurrentUserInfo.getUser().getUserInfo().getClient_store_id());
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(url)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    SeatApi seatApi = retrofit.create(SeatApi.class);
                                    seatApi.setAutoCancle(input).enqueue(new Callback<SeatResult>() {

                                        @Override
                                        public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                            if (response.isSuccessful()) {
                                                SeatResult seatResult = response.body();
                                                switch (seatResult.getResult()) {
                                                    case 1://성공
                                                        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                                        vibrator.vibrate(1000);
                                                        CurrentUsingSeatInfo.setSeat_id(0);//자리 사용중이지 않게 초기화
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                        builder.setTitle("테이블 비움 처리")
                                                                .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                                .setPositiveButton("확인", null);
                                                        builder.show();
                                                        bool_beacon=false;
                                                        onsu_ddokbokki_seat_check = false;//좌석 사용이 끝났으니 false
                                                        break;
                                                    case 0://실패
                                                        Toast.makeText(getApplication(), "테이블 자동 취소오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SeatResult> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                seat_count++;
                                Log.d("test",""+seat_count);
                            }
                        }
                    }
                }
                if(isac_seat_check){//이삭을 사용중인거면
                    boolean check=false;
                    if(beaconList.size()==0) {
                        seat_count++;
                        if(seat_count>=30) {
                            isac_seat_check = false;//좌석 사용이 끝났으니 false
                            try {
                                String seat_id= String.valueOf(SeatOrderState.getId());
                                HashMap<String,String> input= new HashMap<>();
                                input.put("client_id",String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                                input.put("seat_id",seat_id);
                                input.put("store_id", CurrentUserInfo.getUser().getUserInfo().getClient_store_id());
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(url)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                SeatApi seatApi = retrofit.create(SeatApi.class);
                                seatApi.setAutoCancle(input).enqueue(new Callback<SeatResult>() {

                                    @Override
                                    public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                        if (response.isSuccessful()) {
                                            SeatResult seatResult = response.body();
                                            switch (seatResult.getResult()) {
                                                case 1://성공
                                                    bool_beacon=false;
                                                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                                    vibrator.vibrate(1000);
                                                    CurrentUsingSeatInfo.setSeat_id(0);//자리 사용중이지 않게 초기화
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                    builder.setTitle("테이블 비움 처리")
                                                            .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                            .setPositiveButton("확인", null);
                                                    builder.show();
                                                    break;
                                                case 0://실패
                                                    Toast.makeText(getApplication(), "테이블 자동 취소오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SeatResult> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        for (int i = 0; i < beaconList.size(); i++) {
                            if (beaconList.get(i).getId3().toString().equals("23999")) {
                                check = true;
                                break;
                            } else {//감지된 비콘 리스트에서 해당 매장의 비콘의id3가 없으면
                                check = false;
                            }
                        }
                        if (check) {
                            seat_count = 0;
                        } else {
                            if (seat_count >= 30) {//비콘감지가 안된게 1시간이 지나면
                                isac_seat_check = false;//좌석 사용이 끝났으니 false
                                //자리 없애기 retofit
                                try {
                                    String seat_id= String.valueOf(SeatOrderState.getId());
                                    HashMap<String,String> input= new HashMap<>();
                                    input.put("client_id",String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                                    input.put("seat_id",seat_id);
                                    input.put("store_id", CurrentUserInfo.getUser().getUserInfo().getClient_store_id());
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(url)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    SeatApi seatApi = retrofit.create(SeatApi.class);
                                    seatApi.setAutoCancle(input).enqueue(new Callback<SeatResult>() {

                                        @Override
                                        public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                            if (response.isSuccessful()) {
                                                SeatResult seatResult = response.body();
                                                switch (seatResult.getResult()) {
                                                    case 1://성공
                                                        bool_beacon=false;
                                                        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                                        vibrator.vibrate(1000);
                                                        CurrentUsingSeatInfo.setSeat_id(0);//자리 사용중이지 않게 초기화
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                        builder.setTitle("테이블 비움 처리")
                                                                .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                                .setPositiveButton("확인", null);
                                                        builder.show();
                                                        onsu_ddokbokki_seat_check = false;//좌석 사용이 끝났으니 false
                                                        break;
                                                    case 0://실패
                                                        Toast.makeText(getApplication(), "테이블 자동 취소오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SeatResult> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                seat_count++;
                                Log.d("test",""+seat_count);
                            }
                        }
                    }
                }
            }
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
}