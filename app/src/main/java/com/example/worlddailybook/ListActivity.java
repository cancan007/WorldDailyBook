package com.example.worlddailybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    public static final String POSITION_DATA = "com.example.worlddailyrecords.MainActivity";
    private SQLite_Handler handler;
    private SQLiteDatabase db;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new SQLite_Handler(getApplicationContext());
        db = handler.getReadableDatabase();
        //handler.onCreate(db);
        //Enter_Activity EA = new Enter_Activity();

        final RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        //LayoutManagerを設定
        //縦方向のリストはLinearLayoutManagerを使う
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //縦方向に設定
        recyclerView.setLayoutManager(layoutManager);
        //ItemDecorationを設定
        //DividerItemDecorationはリストに罫線を引くためのItemDecoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        //DataBaseから表示するデータの配列を取ってくる
        String[] loc_array = readData();


        //アダプタのセット
        SampleRecyclerAdapter adapter = new SampleRecyclerAdapter(loc_array);
        recyclerView.setAdapter(adapter);

        createButton = findViewById(R.id.m_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Enter_Activity.class);
                startActivity(intent);
            }
        });
    }

    /* -------------------------------------------------------
     * RecyclerViewのアダプタ
     * --------------------------------------------------------*/
    class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.SampleViewHolder> {
        //メンバ変数
        String[] itemDatas; //リストのデータを保持する変数

        //コンストラクタ
        SampleRecyclerAdapter(String[] itemDatas) {
            //データをコンストラクタで受け取りメンバ変数に格納
            this.itemDatas = itemDatas;
        }

        @NonNull
        @Override
        public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {  // なぜ `Overrideなのに　関数名が違くてもいいのか
            //onCreateViewHolder()ではリスト一行分のレイアウトViewを生成する
            //作成したViewはViewHolderに格納してViewHolderをreturnで返す

            //レイアウトXMLからViewを生成
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.listitem_recyclerview1, viewGroup, false);
            //ViewHolderを生成してreturn (このSampleViewHolderはインナークラスのもの)
            SampleViewHolder holder = new SampleViewHolder(view);

            //クリックイベントを登録
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Toast.makeText(v.getContext(), itemDatas[position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), Display_Content.class);
                    intent.putExtra(POSITION_DATA, holder.getLayoutPosition());
                    startActivity(intent);
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SampleViewHolder sampleViewHolder, int position) {
            //onBindViewHolder()ではデータとレイアウトの紐づけを行なう
            sampleViewHolder.text01.setText(itemDatas[position]);
        }

        @Override
        public int getItemCount() {
            //データ個数を返す
            return itemDatas.length;
        }

        /* ViewHolder（インナークラス） */
        class SampleViewHolder extends RecyclerView.ViewHolder {
            TextView text01;

            SampleViewHolder(@NonNull View itemView) {
                super(itemView);
                text01 = itemView.findViewById(R.id.text01);
            }
        }
    }

    public String[] readData(){
        List<String> loc_list= new ArrayList<String>(); // 文字列を格納する空の配列を用意
        List<String> date_list = new ArrayList<String>();
        List<String> title_list = new ArrayList<String>();
        List<String> content_list = new ArrayList<String>();

        //SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.query(
                handler.TABLE_NAME,
                new String[]{"location", "date", "title","longitude", "latitude" ,"content"},
                null,  //WHERE句の列名
                null,  //WHERE句の値
                null,  //GROUP BY句の値
                null,  //HAVING句の値
                null  //ORDER BY句の値
        );
        cursor.moveToFirst();  // databaseの最初の行へ移動

        for(int i=0; i<cursor.getCount(); i+=1){
            loc_list.add(cursor.getString(cursor.getColumnIndex("Location"))); // cursor.getColumnincex(カラム名)でそのカラムの列数を渡す
            //loc_list.add(cursor.getString(0));
            //date_list.add(cursor.getString(cursor.getColumnIndex("date")));
            date_list.add(cursor.getString(1));
            //title_list.add(cursor.getString(cursor.getColumnIndex("title")));
            title_list.add(cursor.getString(2));
            //content_list.add(cursor.getString(cursor.getColumnIndex("content")));
            content_list.add(cursor.getString(3));
            cursor.moveToNext();
        }

        cursor.close();
        String[] loc_array = loc_list.toArray(new String[0]);

        return loc_array;
    }
}