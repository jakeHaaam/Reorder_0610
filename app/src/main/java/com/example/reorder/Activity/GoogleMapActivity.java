package com.example.reorder.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


public class GoogleMapActivity extends FragmentActivity implements
        OnMapReadyCallback,GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Button bt_current;
    GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        Intent intent=getIntent();
        count=intent.getExtras().getInt("count");
        bt_current=(Button)findViewById(R.id.bt_current);

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

    public void startLocationService(){
        long minTime = 3000;
        float minDistance = 0;

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        map.clear();
                        double st_lat=0,st_lng = 0;
                        //store marker
                        for(int i=0;i<count;i++){
                            st_lat=Double.parseDouble(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_lat());
                            st_lng=Double.parseDouble(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_lng());
                            LatLng stPoint=new LatLng(st_lat,st_lng);
                            map.addMarker(new MarkerOptions().position(stPoint)
                                    .title(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_name()));
                        }
                        map.setOnInfoWindowClickListener(infoWindowClickListener);
                        showCurrentLocation(location);
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
                });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double st_lat=0,st_lng = 0;
        //지도 시작위도,경도 설정/초기 카메라 위치 설정
        LatLng start=new LatLng(37.48713123599517 ,126.82648816388149 );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start,17.0f));
        onLastLocationButtonClicked(getCurrentFocus());
        //store marker
        for(int i=0;i<count;i++){
            st_lat=Double.parseDouble(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_lat());
            st_lng=Double.parseDouble(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_lng());
            LatLng stPoint=new LatLng(st_lat,st_lng);
            map.addMarker(new MarkerOptions().position(stPoint)
                    .title(CurrentStoreInfo.getStore().getStoreInfoList().get(i).getStore_name()));
        }
        map.setOnInfoWindowClickListener(infoWindowClickListener);
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

    public void showCurrentLocation(Location location)
    {
        LatLng curPoint=new LatLng(location.getLatitude(),location.getLongitude());
        map.addMarker(new MarkerOptions().position(curPoint).title("현재 위치")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    public void onLastLocationButtonClicked(View view) {
        //사용자 위치 서비스 허가여부
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    startLocationService();
                    final LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(myLocation).title("현재 위치")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
                }
                else {
                    Toast.makeText(getApplicationContext(), "location is null", Toast.LENGTH_SHORT).show();
                }
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
                            }
                            else
                                Toast.makeText(getApplicationContext(),"location is null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //마커 클릭시 구현
        marker.showInfoWindow();
        return true;
    }
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener=new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

            if(!marker.getTitle().equals("현재 위치")) {
                String st_name = marker.getTitle();
                Intent HomeIntent = new Intent(getApplicationContext(), NavigationnActivity.class); //this 대신 getActivity() : 현재의 context받아올 수 있음
                HomeIntent.putExtra("map", st_name);
                startActivity(HomeIntent);
            }
        }
    };
}