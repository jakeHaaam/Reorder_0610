package com.example.reorder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    int camera_checked = 0;
    GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CODE_PERMISSIONS=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        //현재위치 설정
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mFusedLocationClient  = LocationServices.getFusedLocationProviderClient(this);
        //
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //지도 시작위도,경도 설정
        LatLng start=new LatLng(37.48713123599517 ,126.82648816388149 );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start,17.0f));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onLastLocationButtonClicked(View view) {
        //사용자 위치 서비스 허가여부
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    final LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(myLocation).title("현재 위치"));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

                    //3d효과
                    final Button bt_map_3d = (Button) findViewById(R.id.bt_map_3d);

                    bt_map_3d.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            camera_checked++;
                            if (camera_checked == 1) {
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(myLocation)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        .bearing(0)                // Sets the orientation of the camera to east
                                        .tilt(70)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                bt_map_3d.setText("돌아가기");
                            } else {
                                camera_checked = 0;
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(myLocation)      // Sets the center of the map to Mountain View
                                        .zoom(17)                   // Sets the zoom
                                        .bearing(0)                // Sets the orientation of the camera to east
                                        .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                bt_map_3d.setText("3d로 보기");
                            }
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"location is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            //위치 서비스 권한 여부 한번더
            case REQUEST_CODE_PERMISSIONS:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null)
                            {
                                final LatLng myLocation= new LatLng(location.getLatitude(),location.getLongitude());
                                map.addMarker(new MarkerOptions().position(myLocation).title("현재 위치"));
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,17));

                                //3d효과
                                final Button bt_map_3d=(Button)findViewById(R.id.bt_map_3d);

                                bt_map_3d.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        camera_checked++;
                                        if(camera_checked==1) {
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(myLocation)      // Sets the center of the map to Mountain View
                                                    .zoom(19)                   // Sets the zoom
                                                    .bearing(0)                // Sets the orientation of the camera to east
                                                    .tilt(70)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                            bt_map_3d.setText("돌아가기");
                                        }
                                        else{
                                            camera_checked=0;
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(myLocation)      // Sets the center of the map to Mountain View
                                                    .zoom(17)                   // Sets the zoom
                                                    .bearing(0)                // Sets the orientation of the camera to east
                                                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                            bt_map_3d.setText("3d로 보기");
                                        }
                                    }
                                });
                            }
                            else
                                Toast.makeText(getApplicationContext(),"location is null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }
    }
}