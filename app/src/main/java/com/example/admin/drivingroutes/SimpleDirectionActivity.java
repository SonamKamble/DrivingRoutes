package com.example.admin.drivingroutes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by ADMIN on 30/05/2016.
 */
public class SimpleDirectionActivity  extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, DirectionCallback {
    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private String serverKey="AIzaSyDWWiysvt0gD4gw5oLLcN98ZTl3wGVvoyM";
    private LatLng camera = new LatLng(19.131689, 72.935193);
    private LatLng origin = new LatLng(19.131689, 72.935193);
    private LatLng destination = new LatLng(19.218331, 72.978090);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_direction);

        btnRequestDirection=(Button)findViewById(R.id.btn_request_direction);
        btnRequestDirection.setOnClickListener(this);
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_request_direction){
            requestDirection();
        }
    }

    private void requestDirection() {
        Snackbar.make(btnRequestDirection,"Direction Requesting...!",Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transitMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin,15));
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make(btnRequestDirection,"Success with Status:"+direction.getStatus(),Snackbar.LENGTH_SHORT).show();
        if(direction.isOK()){
            googleMap.addMarker(new MarkerOptions().position(origin));
            googleMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList=direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this,directionPositionList,5, Color.RED));

            btnRequestDirection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }
}