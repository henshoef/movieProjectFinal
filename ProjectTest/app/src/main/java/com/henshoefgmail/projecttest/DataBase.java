package com.henshoefgmail.projecttest;


/**
 * Created by Ss12Dark on 3/9/2018.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;


//this class handles the all database tasks and extends the SQLiteOpenHelper (a build class)
//in this class we must to implement the methods "onCreate" and "onUpgrade" from the SQLiteOpenHelper class
public class DataBase extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movieLab";

    public static final String TABLE_MOVIES = "Movies";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "movieName";
    public static final String KEY_DESCRIPTION= "movieDescription";
    public static final String KEY_URL = "movieURL";
    public static final String KEY_NO = "KeyNo";
    public static final String KEY_WATCH = "watched";

    //We need to pass database information along to superclass because the super class doesn't have any default constructor

    public DataBase(Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //in the first time that we run this app we want to create our table in order to store data
    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_URL + " TEXT, "
                + KEY_NO + " TEXT, "
                + KEY_WATCH + " INTEGER"+")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        // Create tables again
        onCreate(db);
    }

    public void updateWatch(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String str = "UPDATE "+TABLE_MOVIES+" SET "+KEY_WATCH+" = "+KEY_WATCH+" + 1 WHERE "+KEY_ID+" = "+id;
        db.execSQL(str);
    }
    public void resetWatch(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String str = "UPDATE "+TABLE_MOVIES+" SET "+KEY_WATCH+" =  0 WHERE "+KEY_ID+" = "+id;
        db.execSQL(str);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES,null,null);
        db.execSQL("delete from "+ TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        onCreate(db);
    }


    public void addMovie(MovieSample movieSample){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, movieSample.getSubject());
        values.put(KEY_DESCRIPTION, movieSample.getBody());
        values.put(KEY_URL, movieSample.getUrl());
        values.put(KEY_NO, movieSample.getOrderNumber());
        values.put(KEY_WATCH, movieSample.getWatched());


        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }


    public boolean deleteMovie(int delID) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_MOVIES, KEY_ID + "=" + delID, null) > 0;

    }


    public List<MovieSample> getAllMovieList() {

        List<MovieSample> movieSampleList = new ArrayList<MovieSample>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                MovieSample movieSample = new MovieSample();
                movieSample.set_id(Integer.parseInt(cursor.getString(0)));
                movieSample.setSubject(cursor.getString(1));
                movieSample.setBody(cursor.getString(2));
                movieSample.setUrl(cursor.getString(3));
                movieSample.setOrderNumber(Integer.parseInt(cursor.getString(4)));
                movieSample.setWatched(Integer.parseInt(cursor.getString(5)));

                // Adding contact to list
                movieSampleList.add(movieSample);

            } while (cursor.moveToNext());
        }

        // return contact list
        return movieSampleList;
    }

}

