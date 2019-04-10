package com.example.reorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CartActivity extends AppCompatActivity {

    Button bt_cart_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bt_cart_order=(Button)findViewById(R.id.bt_cart_order);

        bt_cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OrderIntent=new Intent(CartActivity.this,OrderActivity.class);
                CartActivity.this.startActivity(OrderIntent);
            }
        });
    }
}