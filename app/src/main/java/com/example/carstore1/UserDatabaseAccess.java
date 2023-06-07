package com.example.carstore1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UserDatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static UserDatabaseAccess instance;

    private UserDatabaseAccess(Context context){
        this.openHelper = new myDAtaBase(context);
    }

    public static UserDatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new UserDatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close(){
        if(this.database != null) {
            this.database.close();
        }
    }

    public boolean insertUser(User user){
        ContentValues values = new ContentValues();
        values.put(myDAtaBase.USER_CLN_NAME , user.getName());
        values.put(myDAtaBase.USER_CLN_USER_NAME , user.getUser_name());
        values.put(myDAtaBase.USER_CLN_PASS , user.getPassword());

        long v = database.insert(myDAtaBase.USER_TB_NAME , null , values);
        return v > 0;
    }

    public long userCounts(){
        return DatabaseUtils.queryNumEntries(database , myDAtaBase.USER_TB_NAME);
    }

    public boolean updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(myDAtaBase.USER_CLN_ID , user.getId());
        values.put(myDAtaBase.USER_CLN_NAME , user.getName());
        values.put(myDAtaBase.USER_CLN_USER_NAME , user.getUser_name());
        values.put(myDAtaBase.USER_CLN_PASS , user.getPassword());

        long v = database.update(myDAtaBase.USER_TB_NAME , values ,myDAtaBase.USER_CLN_ID + "= ?" , new String[]{user.getId()+""});
        return v > 0;
    }

    public User getUser(String userName , String password){
        User user = new User();
        String query = "select * from " + myDAtaBase.USER_TB_NAME + " where user_name = ? And password = ?";
        Cursor cursor = database.rawQuery(query  , new String[]{userName , password});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            int idu = cursor.getInt(0);
            String name = cursor.getString(1);
            String user_name = cursor.getString(2);
            String passwordu = cursor.getString(3);
            user.setId(idu);
            user.setUser_name(user_name);
            user.setPassword(password);
            user.setName(name);
             cursor.moveToNext();
        }
        return user;
    }

    public ArrayList<User> getUsers(){
        Cursor cursor = database.rawQuery("select * from " + myDAtaBase.USER_TB_NAME , null) ;
        cursor.moveToFirst();
        User user = null;
        ArrayList<User> list = new ArrayList<>();
        while(cursor.isAfterLast() == false){
            int idu = cursor.getInt(0);
            String name = cursor.getString(1);
            String user_name = cursor.getString(2);
            String passwordu = cursor.getString(3);

            user = new User(idu , name , user_name , passwordu);
            list.add(user);
            cursor.moveToNext();
        }
        return list;
    }

    boolean deleteUser(int id){
        long d = database.delete(myDAtaBase.USER_TB_NAME , myDAtaBase.USER_CLN_ID + "= ?" , new String[]{id+""});
        return d > 0;
    }
}
