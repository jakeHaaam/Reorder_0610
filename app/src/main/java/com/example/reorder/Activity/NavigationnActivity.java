package com.example.reorder.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.reorder.Api.StoreIdApi;
import com.example.reorder.Fragment.CartFragment;
import com.example.reorder.Fragment.HomeFragment;
import com.example.reorder.Fragment.MenuFragment;
import com.example.reorder.Fragment.OrderFragment;
import com.example.reorder.Fragment.SeatReserveFragment;
import com.example.reorder.Fragment.StoreFragment;
import com.example.reorder.R;
import com.example.reorder.Result.CartResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.Result.StoreIdResult;
import com.example.reorder.globalVariables.CurrentCartInfo;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;
import com.example.reorder.info.StoreInfo;
import com.example.reorder.info.StoreMenuInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NavigationnActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable {

    private Fragment homeFragment;
    private Fragment storeFragment;
    private Fragment testFragment;
    private Fragment CartFragment;
    private Fragment OrderFragment;
    private Fragment SeatReserveFragment;
    private Fragment MenuFragment;
    private ImageButton bt_cart;
    public static Context mContext;
    String url= serverURL.getUrl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        homeFragment = new HomeFragment();
        storeFragment=new StoreFragment();
        CartFragment=new CartFragment();
        OrderFragment=new OrderFragment();
        SeatReserveFragment=new SeatReserveFragment();
        MenuFragment=new MenuFragment();
        setDefaultFragment();

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
//                                            Log.d("cart", "카트담겨진 메뉴이름 : "+CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_name());
//                                            Log.d("cart", "카트담겨진 메뉴가격 :"+CurrentCartInfo.getCart().getCartInfoList().get(0).getMenu_price());
//                                            Log.d("cart", "카트담겨진 메 :"+CurrentCartInfo.getCart().getCartInfoList().get(1).getMenu_name());

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        else if (id == R.id.nav_logout) {
            replaceFragment(2);
        }
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

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

}