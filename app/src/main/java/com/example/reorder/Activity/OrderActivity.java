package com.example.reorder.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.reorder.R;

public class OrderActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_order);

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
                    Intent NaviIntent=new Intent(OrderActivity.this,NavigationActivity.class);
                    OrderActivity.this.startActivity(NaviIntent);
                }
                else if(rb_eat_here.isChecked() && rb_seat_no.isChecked()) {
                    Intent NaviIntent=new Intent(OrderActivity.this,NavigationActivity.class);
                    OrderActivity.this.startActivity(NaviIntent);
                }
                else if(rb_eat_here.isChecked() && rb_seat_yes.isChecked()){
                    Intent SeatIntent=new Intent(OrderActivity.this,SeatReserveActivity.class);
                    OrderActivity.this.startActivity(SeatIntent);
                }
            }
        });
    }
}