package com.example.reorder.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentUserInfo;

public class OrderrActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RadioButton rb_eat_here;
    RadioButton rb_take_out;
    RadioButton rb_seat_yes;
    RadioButton rb_seat_no;
    RadioGroup rg_seat;
    RadioGroup rg_eat;
    LinearLayout ll_seat;

    Button bt_order;
    Button bt_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        rb_eat_here=findViewById(R.id.rb_eat_here);
        rb_take_out=findViewById(R.id.rb_take_out);
        rb_take_out.setChecked(true);
        rb_seat_yes=findViewById(R.id.rb_seat_yes);
        rb_seat_no=findViewById(R.id.rb_seat_no);
        rg_eat=findViewById(R.id.rg_eat);
        rg_seat=findViewById(R.id.rg_seat);
        ll_seat=findViewById(R.id.ll_seat);


        bt_order=findViewById(R.id.bt_order);
        bt_cancle=findViewById(R.id.bt_cancle);

        rg_eat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_eat_here)
                    ll_seat.setVisibility(View.VISIBLE);
                else
                    ll_seat.setVisibility(View.GONE);
            }
        });

        bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_take_out.isChecked()){
                    Intent NaviIntent=new Intent(OrderrActivity.this,NavigationnActivity.class);
                    OrderrActivity.this.startActivity(NaviIntent);
                }
                else if(rb_eat_here.isChecked() && rb_seat_no.isChecked()) {
                    Intent NaviIntent=new Intent(OrderrActivity.this,NavigationnActivity.class);
                    OrderrActivity.this.startActivity(NaviIntent);
                }
                else if(rb_eat_here.isChecked() && rb_seat_yes.isChecked()){
                    Intent SeatIntent=new Intent(OrderrActivity.this,SeattReserveActivity.class);
                    OrderrActivity.this.startActivity(SeatIntent);
                }
            }
        });


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
        getMenuInflater().inflate(R.menu.orderr, menu);
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
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
