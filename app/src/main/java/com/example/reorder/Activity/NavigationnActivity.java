package com.example.reorder.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Api.FragmentReplaceable;

import com.example.reorder.Api.RetrofitApi;
import com.example.reorder.Api.SeatApi;
import com.example.reorder.Api.StoreIdApi;
import com.example.reorder.Fragment.CartFragment;
import com.example.reorder.Fragment.HomeFragment;
import com.example.reorder.Fragment.MenuFragment;
import com.example.reorder.Fragment.OrderFragment;
import com.example.reorder.Fragment.SeatReserveFragment;
import com.example.reorder.Fragment.SeeTableFragment;
import com.example.reorder.Fragment.StoreFragment;
import com.example.reorder.R;
import com.example.reorder.Result.CartResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.Result.LoginResult;
import com.example.reorder.Result.SeatResult;
import com.example.reorder.Result.StoreIdResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.CurrentUsingSeatInfo;
import com.example.reorder.globalVariables.SeatOrderState;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;
import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.StoreMenuInfo;
import com.example.reorder.info.UserInfo;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationnActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable, BeaconConsumer {

    public static boolean cantu_seat_check;
    public static boolean jad_seat_check;
    public static int seat_count=0;
    public static int bea_st_id;//깐뚜1번 자드 2번
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
    private ImageButton bt_cart;
    public static Context mContext;
    private ImageButton bt_refresh;
    String url= serverURL.getUrl();

    public void categoryChanged(List<StoreInfo> storeInfos){
        //homeFragment.getActivity().
        HomeFragment hf = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        hf.categoryChanged(storeInfos);
        Log.d("category","activity");
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

        Log.d("beacon",""+bool_beacon);

        // 실제로 비콘을 탐지하기 위한 비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(getApplication());
        // 여기가 중요한데, 기기에 따라서 setBeaconLayout 안의 내용을 바꿔줘야 하는듯 싶다.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        // 비콘 탐지를 시작한다. 실제로는 서비스를 시작하는것.
        beaconManager.bind(this);
        Log.d("7","7");

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
                                Log.d("storeadapter",response.message()+"*^^* "+response.toString());
                                if (response.isSuccessful()){
                                    StoreIdResult storeIdResult=response.body();
                                    if(storeIdResult!=null) {
                                        switch (storeIdResult.getResult()) {
                                            case 1://성공
                                                Log.d("storeadapter", "store 받아오기 성공");
                                                List<StoreMenuInfo> storeMenuInfo= storeIdResult.getStoreMenuInfo();
                                                CurrentStoreMenuInfo.getStoreMenu().setStoreMenuInfoList(storeMenuInfo);
                                                replaceFragment(2);
                                                break;
                                            case 0://실패
                                                Log.d("storeadapter", "store 받아오기 실패");
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
                Log.d("cart", "id: "+CurrentUserInfo.getUser().getUserInfo().getId());
                Log.d("cart", "client_id : "+CurrentUserInfo.getUser().getUserInfo().getClient_id());
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
                                            Log.d("cart", "카트 받아오기 성공");
                                            List<CartInfo> cartInfo = cartResult.getCartInfo();
                                            CurrentCartInfo.getCart().setCartInfoList(cartInfo);//저장
                                            Log.d("cart", ""+response.body());
                                            replaceFragment(4);
                                            break;
                                        case 0:
                                            Log.d("cart", "fail");
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
                                Log.d("11111",map.getResult() +"");
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case -1:
                                            Toast.makeText(mContext,"잘못된 비밀번호 입니다.",Toast.LENGTH_SHORT).show();
                                            Log.d("mileage", "잘못된 비밀번호입니다");
                                            break;
                                        case 0:
                                            Toast.makeText(mContext,"가입되지 않은 이메일입니다.",Toast.LENGTH_SHORT).show();
                                            Log.d("mileage", "가입되지 않은 이메일입니다");
                                            break;
                                        case 1:
                                            Log.d("mileage", "마일리지 새로고침 성공");
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
            if(fragment!=homeFragment)
                super.onBackPressed();
            else{
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
        Log.d("11111",item+" ");

        if (id == R.id.nav_home) {
            Log.d("11111","된다 ");
            replaceFragment(1);
            Toast.makeText(this, "홈이다", Toast.LENGTH_SHORT).show();

        }
//            // Handle the camera action
        else if(id==R.id.nav_table_out){//테이블 사용 안함 클릭시
            if(CurrentUsingSeatInfo.getSeat_id()!=0) {//좌석 다 이용시 0으로 초기화해야함
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                builder.setTitle("테이블 비움 처리")
                        .setMessage("사용중이신 테이블을 다 사용하셨습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String seat_id = String.valueOf(SeatOrderState.getId());
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(url)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    SeatApi seatApi = retrofit.create(SeatApi.class);
                                    seatApi.setUserCancle(seat_id).enqueue(new Callback<SeatResult>() {
                                        @Override
                                        public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                            if (response.isSuccessful()) {
                                                SeatResult seatResult = response.body();
                                                switch (seatResult.getResult()) {
                                                    case 1://성공
                                                        CurrentUsingSeatInfo.setSeat_id(0);
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
                Toast.makeText(getApplication(),"사용중이신 좌석이 없습니다.",Toast.LENGTH_SHORT).show();
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
        }
        transaction.addToBackStack(null);//뒤로가기라는 스텍에 계속 저장중
        transaction.commit();
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

                if(bool_beacon) {
                    if (beacons.size() > 0) {
                        Log.d("5", "5");
                        beaconList.clear();
                        for (Beacon beacon : beacons) {
                            beaconList.add(beacon);
                            Log.d("beacon",beacon.getId3().toString());
                            if(bea_st_id==1) {
                                if (beacon.getId3().toString().equals("24000")) {
                                    Log.d("beacon", "깐뚜");
                                    //Toast.makeText(getApplication(), "깐뚜", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder=new AlertDialog.Builder(NavigationnActivity.this);
                                    builder.setTitle("테이블 예약 확정")
                                            .setMessage("깐뚜치오에서 예약하신 테이블을 확정 지으시겠습니까?")
                                            .setPositiveButton("예약 확정", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
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
                                                                            CurrentUsingSeatInfo.setSeat_id(SeatOrderState.getId());
                                                                            Toast.makeText(getApplication(),"테이블을 확정지으셨습니다.",Toast.LENGTH_SHORT).show();
                                                                            cantu_seat_check=true;
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
                                                }
                                            })
                                            .setNegativeButton("예약 취소", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    try {
                                                        String seat_id= String.valueOf(SeatOrderState.getId());
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(url)
                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                .build();
                                                        SeatApi seatApi=retrofit.create(SeatApi.class);
                                                        seatApi.setCancle(seat_id).enqueue(new Callback<SeatResult>() {
                                                            @Override
                                                            public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                                if(response.isSuccessful()){
                                                                    SeatResult seatResult=response.body();
                                                                    switch (seatResult.getResult()){
                                                                        case 1://성공
                                                                            Toast.makeText(getApplication(),"예약을 취소하였습니다.",Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                        case 0://실패
                                                                            Toast.makeText(getApplication(),"취소오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
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
                                                }
                                            });
                                    builder.show();
                                    bool_beacon = false;
                                    break;
                                }
                            }else if(bea_st_id==2){
                                if (beacon.getId3().toString().equals("23999")){
                                    Log.d("beacon","자드");
                                    //Toast.makeText(getApplication(),"자드",Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder=new AlertDialog.Builder(NavigationnActivity.this);
                                    builder.setTitle("테이블 예약 확정")
                                            .setMessage("자연드림에서 예약하신 테이블을 확정 지으시겠습니까?")
                                            .setPositiveButton("예약 확정", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
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
                                                                            CurrentUsingSeatInfo.setSeat_id(SeatOrderState.getId());
                                                                            Toast.makeText(getApplication(),"테이블을 확정지으셨습니다.",Toast.LENGTH_SHORT).show();
                                                                            jad_seat_check=true;
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
                                                }
                                            })
                                            .setNegativeButton("예약 취소", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    try {
                                                        String seat_id= String.valueOf(SeatOrderState.getId());
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(url)
                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                .build();
                                                        SeatApi seatApi=retrofit.create(SeatApi.class);
                                                        seatApi.setCancle(seat_id).enqueue(new Callback<SeatResult>() {
                                                            @Override
                                                            public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                                if(response.isSuccessful()){
                                                                    SeatResult seatResult=response.body();
                                                                    switch (seatResult.getResult()){
                                                                        case 1://성공
                                                                            Toast.makeText(getApplication(),"예약을 취소하였습니다.",Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                        case 0://실패
                                                                            Toast.makeText(getApplication(),"취소오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
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
                                                }
                                            });
                                    builder.show();
                                    bool_beacon=false;
                                    break;
                                }
                            }
                        }
                        Log.d("11", beaconList.toString());
                        //Toast.makeText(getApplication(), "감지가 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                if(cantu_seat_check){//깐뚜를 사용중인거면
                    Timer timer=new Timer();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            for (int i=0;i<beaconList.size();i++){
                                if(beaconList.get(i).getId3().toString().equals("24000")){//감지된 비콘리스트에서 해당매장의 비콘의id3가 있으면
                                    seat_count=0;
                                    Log.d("out","찍힌다 더 가라");
                                }else if(!(beaconList.get(i).getId3().toString().equals("24000"))){//감지된 비콘 리스트에서 해당 매장의 비콘의id3가 없으면
                                    Log.d("out","없네?");
                                    if(seat_count>=10) {//비콘감지가 안된게 1시간이 지나면
                                        Log.d("out","여기는?");
                                        //자리 없애기 retofit
                                        try {
                                            String seat_id = String.valueOf(SeatOrderState.getId());
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(url)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            SeatApi seatApi = retrofit.create(SeatApi.class);
                                            seatApi.setAutoCancle(seat_id).enqueue(new Callback<SeatResult>() {

                                                @Override
                                                public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                    if (response.isSuccessful()) {
                                                        SeatResult seatResult = response.body();
                                                        switch (seatResult.getResult()) {
                                                            case 1://성공
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                                builder.setTitle("테이블 비움 처리")
                                                                        .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                finish();
                                                                            }
                                                                        });
                                                                builder.show();
                                                                cantu_seat_check=false;//좌석 사용이 끝났으니 false
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
                                    }else {
                                        seat_count += 1;
                                        Log.d("out","안보인다 비콘");
                                        Toast.makeText(getApplication(),seat_count,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    };
                    timer.schedule(timerTask,1000);//타이머실행
                    if(seat_count>=10) {
                        timer.cancel();//타이머종료
                        seat_count=0;
                    }
                }

                if(jad_seat_check) {//자드를 사용중인거면
                    Timer timer1=new Timer();
                    TimerTask timerTask1=new TimerTask() {
                        @Override
                        public void run() {
                            for (int i = 0; i < beaconList.size(); i++) {
                                if (beaconList.get(i).getId3().toString().equals("23999")) {//감지된 비콘리스트에서 해당매장의 비콘의id3가 있으면
                                    seat_count = 0;
                                } else {//감지된 비콘 리스트에서 해당 매장의 비콘의id3가 없으면
                                    if (seat_count >= 10) {//비콘감지가 안된게 1시간이 지나면
                                        //자리 없애기 retofit
                                        try {
                                            String seat_id = String.valueOf(SeatOrderState.getId());
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(url)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            SeatApi seatApi = retrofit.create(SeatApi.class);
                                            seatApi.setAutoCancle(seat_id).enqueue(new Callback<SeatResult>() {

                                                @Override
                                                public void onResponse(Call<SeatResult> call, Response<SeatResult> response) {
                                                    if (response.isSuccessful()) {
                                                        SeatResult seatResult = response.body();
                                                        switch (seatResult.getResult()) {
                                                            case 1://성공
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationnActivity.this);
                                                                builder.setTitle("테이블 비움 처리")
                                                                        .setMessage("1시간 동안 감지되지 않아 테이블을 비움처리하였습니다.")
                                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                finish();
                                                                            }
                                                                        });
                                                                builder.show();
                                                                jad_seat_check = false;//좌석 사용이 끝났으니 false
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
                                        seat_count += 1;
                                        Toast.makeText(getApplication(),seat_count,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    };
                    timer1.schedule(timerTask1,1000);//타이머실행
                    if(seat_count>=10) {
                        timer1.cancel();//타이머종료
                        seat_count=0;
                    }
                }
            }
        });
        Log.d("beacon", "test끝");
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