package com.example.worlddailybook;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //リストに表示するデータ
    private Button map_b, list_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map_b = findViewById(R.id.m_map_button);
        list_b = findViewById(R.id.m_list_button);

        map_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                startActivity(intent);
            }
        });

        list_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ListActivity.class);
                startActivity(intent);
            }
        });
    }
}