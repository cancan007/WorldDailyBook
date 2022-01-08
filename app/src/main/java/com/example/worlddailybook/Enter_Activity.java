package com.example.worlddailybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Enter_Activity extends AppCompatActivity {

    public SQLite_Handler handler;  // 他のアクティビティでも使えるように、publicにする

    private EditText loc_v, date_v, title_v, lng_v, lat_v,content_v;
    private Button saveButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        handler = new SQLite_Handler(getApplicationContext());

        Intent intent  = getIntent();
        double d_lat = intent.getDoubleExtra("lat", 0);
        double d_lng = intent.getDoubleExtra("lng", 0);


        float lat = (float)d_lat;
        float lng = (float)d_lng;


        loc_v = findViewById(R.id.location);
        date_v = findViewById(R.id.date);
        title_v = findViewById(R.id.title_d);
        lng_v = findViewById(R.id.e_longitude);
        lat_v = findViewById(R.id.e_latitude);
        content_v = findViewById(R.id.content);

        //lng_v.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        lng_v.setText(Float.toString(lng));
        lat_v.setText(Float.toString(lat));




        backButton = findViewById(R.id.back_b);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        saveButton = findViewById(R.id.save_b);
        saveButton.setOnClickListener(new View.OnClickListener() {  // View.OnClickListener インターフェイスから onClickをオーバーライド
            @Override
            public void onClick(View v) {
                saveData();
                //handler.saveData();
                Intent intent = new Intent(getApplication(), Display_Content.class);
                startActivity(intent);
            }
        });

    }



    public void saveData(){
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();

        String location = loc_v.getText().toString();
        String date = date_v.getText().toString();
        String title = title_v.getText().toString();
        String longitude = lng_v.getText().toString();
        String latitude = lat_v.getText().toString();
        String content = content_v.getText().toString();

        values.put("location", location);
        values.put("date", date);
        values.put("title", title);
        values.put("longitude", longitude);
        values.put("latitude", latitude);
        values.put("content", content);

        db.insert(handler.TABLE_NAME, null, values);
    }
}