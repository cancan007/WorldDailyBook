package com.example.worlddailybook;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.worlddailybook.databinding.ActivityMapsBinding;

import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng location;
    private SQLiteDatabase db;
    private SQLite_Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        handler = new SQLite_Handler(getApplicationContext());
        db = handler.getReadableDatabase();
        Cursor c = db.query(
                handler.TABLE_NAME,
                new String[]{"location", "date", "title", "longitude", "latitude", "content"},
                //_id,
                null,
                null,
                null,
                null,
                null
        );
        c.moveToFirst();


        mMap = googleMap;

        int num_datas = ListActivity.num_datas;
        for (int i=0; i< num_datas; i++){
            float f_lng = c.getFloat(c.getColumnIndex("longitude"));
            float f_lat = c.getFloat(c.getColumnIndex("latitude"));
            String c_date = c.getString(c.getColumnIndex("date"));

            double lng = (double)f_lng;
            double lat = (double)f_lat;

            LatLng loc = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(loc).title(c_date));
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng tapLocation){
                location = new LatLng(tapLocation.latitude, tapLocation.longitude);
                Date date = new Date();
                String s_date = date.toString();
                mMap.addMarker(new MarkerOptions().position(location).title(s_date));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

                Intent intent = new Intent(getApplication(), Enter_Activity.class);
                intent.putExtra("lat", location.latitude);
                intent.putExtra("lng", location.longitude);
                //intent.putExtra("date", date);
                startActivity(intent);
            }

        });
    }
}