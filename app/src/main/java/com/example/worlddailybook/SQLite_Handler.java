package com.example.worlddailybook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLite_Handler extends SQLiteOpenHelper {

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 3;

    // データーベース情報を変数に格納
    private static final String DATABASE_NAME = "WorldDailyBook.db";
    public static final String TABLE_NAME = "worlddailybookDB";
    private static final String _ID = "_id";
    private static final String COLUMN_LOCATION = "Location";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_LONGITUDE = "Longitude";
    private static final String COLUMN_LATITUDE = "Latitude";
    private static final String COLUMN_CONTENT = "Content";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + '(' +
                    _ID + " INTEGER PRIMARY KEY,"+   //ここで各々のデータを区別している。
                    COLUMN_LOCATION + " TEXT," +
                    COLUMN_DATE + " DATE," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_LONGITUDE + " FLOAT," +    // East is plus side
                    COLUMN_LATITUDE + " FLOAT," +     // North is plus side
                    COLUMN_CONTENT + " TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    SQLite_Handler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Date date = new Date();

        db.execSQL(
                SQL_CREATE_TABLE
        );
        //saveData(db, "Tokyo", date, "Capital", 34.41f, 139.46f,"First Test");
        //saveData(db, "Germany", date, "Travel", 51.17f, 10.45f ,"Great Travel");
        saveData(db, "Tokyo", date, "Capital", 139.46f,34.41f, "First Test");
        saveData(db, "Germany", date, "Travel", 10.45f ,51.17f, "Great Travel");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                SQL_DELETE_TABLE
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void saveData(SQLiteDatabase db, String location, Date date, String title,Float longitude, Float latitude ,String content){
        ContentValues values = new ContentValues();
        values.put("location", location);
        values.put("date", date.toString());
        values.put("title", title);
        values.put("longitude", longitude);
        values.put("latitude", latitude);
        values.put("content", content);

        db.insert(TABLE_NAME, null, values);
    }

    //public String toString(Date date){
    //String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
    //return str;
    //}

    public Date toDate(String str) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

