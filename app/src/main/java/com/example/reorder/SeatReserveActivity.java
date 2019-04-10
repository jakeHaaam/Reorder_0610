package com.example.reorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeatReserveActivity extends AppCompatActivity {

    Button bt_order_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_reserve);

        bt_order_ok=findViewById(R.id.bt_order_ok);

        bt_order_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NaviIntent=new Intent(SeatReserveActivity.this,NavigationActivity.class);
                SeatReserveActivity.this.startActivity(NaviIntent);
            }
        });
    }
}