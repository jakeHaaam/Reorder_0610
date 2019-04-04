package com.example.reorder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner birth_year;
    private Spinner birth_month;
    private Spinner birth_day;
    Button bt_sign_up_ok;
    Button bt_sign_up_id_check;
    EditText et_sign_up_id;
    EditText et_sign_up_password;
    EditText et_sign_up_password_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        birth_year=(Spinner)findViewById(R.id.cb_sign_up_birth_year);
        birth_month=(Spinner)findViewById(R.id.cb_sign_up_birth_month);
        birth_day=(Spinner)findViewById(R.id.cb_sign_up_birth_day);
        bt_sign_up_ok=(Button)findViewById(R.id.bt_sign_up_ok);
        et_sign_up_id=(EditText)findViewById(R.id.et_sign_up_id);
        et_sign_up_password=(EditText)findViewById(R.id.et_sign_up_password);
        et_sign_up_password_check=(EditText)findViewById(R.id.et_sign_up_password_check);
        bt_sign_up_id_check=(Button)findViewById(R.id.bt_sign_up_id_check);

        adapter=ArrayAdapter.createFromResource(this,R.array.birth_year,android.R.layout.simple_spinner_dropdown_item);
        birth_year.setAdapter(adapter);

        adapter=ArrayAdapter.createFromResource(this,R.array.birth_month,android.R.layout.simple_spinner_dropdown_item);
        birth_month.setAdapter(adapter);

        adapter=ArrayAdapter.createFromResource(this,R.array.birth_day,android.R.layout.simple_spinner_dropdown_item);
        birth_day.setAdapter(adapter);

        //아이디 중복 체크
        bt_sign_up_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(et_sign_up_id.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"ID를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    bt_sign_up_ok.setClickable(false);
                    bt_sign_up_ok.setTextColor(Color.GRAY);
                }

                else if(et_sign_up_id.getText().toString().equals("xodn0996"))
                {
                    Toast.makeText(getApplicationContext(),"이미 가입된 ID입니다.",Toast.LENGTH_SHORT).show();
                    bt_sign_up_ok.setClickable(false);
                    bt_sign_up_ok.setTextColor(Color.GRAY);
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"사용 가능한 ID입니다.",Toast.LENGTH_SHORT).show();
                    bt_sign_up_ok.setClickable(true);
                    bt_sign_up_ok.setTextColor(Color.BLACK);
                }
            }
        });

        //비밀번호 체크
        bt_sign_up_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_sign_up_password.getText().toString().equals("")||et_sign_up_password_check.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"패스워드를 입력하세요.",Toast.LENGTH_SHORT).show();
                }

                else if(et_sign_up_password.getText().toString().equals(et_sign_up_password_check.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"가입 완료 되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(LoginIntent);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
