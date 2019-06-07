package com.example.reorder.Activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.R;
import com.example.reorder.UserInfoUpdate.UserLogin;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id;//[client_id받는 버튼]
    EditText et_login_password;
    Button bt_login_ok;
    TextView registerButton;
    final static int PERMISSION_REQUEST_CODE=1000;
    public static String refresh_password;
    private BluetoothAdapter mBlueAdapter=null;
    private static final int REQUEST_BLUE=3;

    @Override
    protected void onStart() {
        super.onStart();

//        if(!mBlueAdapter.isEnabled()){//켜져있지 않으면
//            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent,REQUEST_BLUE);
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_BLUE:
                if(resultCode== Activity.RESULT_OK){
                    Toast.makeText(getApplication(),"블루투스 활성화 되었습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplication(),"블루투스가 비활성화입니다.",Toast.LENGTH_SHORT).show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mBlueAdapter=BluetoothAdapter.getDefaultAdapter(); // Virtual 로 돌릴때 에러 안나도록 주석처리 핸드폰할때는 풀어야함

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        registerButton=(TextView)findViewById(R.id.tv_login_register);
        et_login_id=(EditText)findViewById(R.id.et_login_id);
        et_login_password=(EditText)findViewById(R.id.et_login_password);
        bt_login_ok=(Button)findViewById(R.id.bt_login_ok);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (LoginActivity.this,RegisterActivity.class));

            }
        });

        bt_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requirePermission();
                boolean coarse=ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED;
                boolean fine=ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED;

                if(coarse&&fine) {
                    if (et_login_id.getText().toString().equals("") || et_login_password.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        UserLogin userLogin = new UserLogin();
                        userLogin.Login(et_login_id.getText().toString(), et_login_password.getText().toString(), new Intent(LoginActivity.this, NavigationnActivity.class), LoginActivity.this);
                    }
                }
                refresh_password=et_login_password.getText().toString();
            }
        });
    }



    void requirePermission(){
        String[] permissions=new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        ArrayList<String> listpermission=new ArrayList<>();

        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                //권한이 허가가 안 되었을 경우 요청할 권한을 모집하는 부분
                listpermission.add(permission);
            }
        }

        if(!listpermission.isEmpty()){
            //권한 요청 하는 부분
            ActivityCompat.requestPermissions(this,listpermission.toArray(new String[listpermission.size()]),1);

        }
    }
}