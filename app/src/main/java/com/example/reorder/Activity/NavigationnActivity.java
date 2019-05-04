package com.example.reorder.Activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.FragmentReplaceable;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentUserInfo;

public class NavigationnActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable {

    private Fragment homeFragment;
    private Fragment storeFragment;
    private Fragment testFragment;
    private Fragment CartFragment;
    private Fragment OrderFragment;
    private Fragment SeatReserveFragment;
    private ImageButton bt_cart;
    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        homeFragment = new HomeFragment();
        storeFragment=new StoreFragment();
        testFragment=new TestFragment();
        CartFragment=new CartFragment();
        OrderFragment=new OrderFragment();
        SeatReserveFragment=new SeatReserveFragment();
        setDefaultFragment();

        bt_cart=findViewById(R.id.bt_cart);
        bt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(4);
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
            transaction.replace(R.id.container, testFragment);
        } else if (fragmentId==4){
            transaction.replace(R.id.container, CartFragment);
        }else if (fragmentId==5) {
            transaction.replace(R.id.container, OrderFragment);
        }else if (fragmentId==6) {
            transaction.replace(R.id.container, SeatReserveFragment);
        }


        transaction.addToBackStack(null);
        transaction.commit();
    }
}
