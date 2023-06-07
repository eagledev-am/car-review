package com.example.carstore1;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.lang.reflect.Executable;
import java.util.ArrayList;

public class myDAtaBase extends SQLiteAssetHelper {
    //declare name of database
    public  static final String DB_NAME ="car_db.db";
    //declare version
    public  static final int DB_VERSION =2;
    public static final String CAR_TB_NAME= "Car";
    public static final String CAR_CLN_ID= "id";
    public static final String CAR_CLN_MODEL="model";
    public static final String CAR_CLN_COLOR="color";

    public static final String CAR_CLN_DESCRIPTION = "description";

    public static final String CAR_CLN_IMAGE = "image";
    public static final String CAR_CLN_DPL="distanceperlitter";

    // users
    public static final String USER_TB_NAME = "User";
    public static final String USER_CLN_ID = "id";
    public static final String USER_CLN_NAME = "name";
    public static final String USER_CLN_USER_NAME = "user_name";
    public static final String USER_CLN_PASS = "password";


    // Cart
    public static final String CART_TB_NAME = "Cart";
    public static final String CART_USER_ID = "user_id";
    public static final String CART_CAR_ID = "car_id";
    public myDAtaBase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }




}
