package com.example.carstore1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.openHelper = new myDAtaBase(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
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


    boolean insertCar (Car car){
        ContentValues values =new ContentValues();
        values.put(myDAtaBase.CAR_CLN_MODEL,car.getModel());
        values.put(myDAtaBase.CAR_CLN_COLOR,car.getColor());
        values.put(myDAtaBase.CAR_CLN_IMAGE , car.getImage());
        values.put(myDAtaBase.CAR_CLN_DPL,car.getDpl());
        values.put(myDAtaBase.CAR_CLN_DESCRIPTION , car.getDescription());
        long result = database.insert(myDAtaBase.CAR_TB_NAME,null,values);
        return result != -1;
    }
    //in update we send condition 'where'
    boolean updateCar (Car car){
        ContentValues values =new ContentValues();
        values.put(myDAtaBase.CAR_CLN_MODEL,car.getModel());
        values.put(myDAtaBase.CAR_CLN_COLOR,car.getColor());
        values.put(myDAtaBase.CAR_CLN_IMAGE , car.getImage());
        values.put(myDAtaBase.CAR_CLN_DPL,car.getDpl());
        values.put(myDAtaBase.CAR_CLN_DESCRIPTION , car.getDescription());
        String args [] = {String.valueOf(car.getId())};
//result == 0 ==>  update  failed
        int result = database.update(myDAtaBase.CAR_TB_NAME,values,"id= ?",args);
        return result >0;
    }

    //count of rows
    public  long count (){
        return  DatabaseUtils.queryNumEntries(database,myDAtaBase.CAR_TB_NAME);
    }

    boolean deleteCar (Car car){
        String args [] = {String.valueOf(car.getId())};
        //result == 0 ==>  update  failed
        int result = database.delete(myDAtaBase.CAR_TB_NAME,"id = ?",args);
        return result > 0;
    }

    public ArrayList<Car> getAllCars(){
        ArrayList <Car> cars =new ArrayList<>();
        Cursor cursor= database.rawQuery("SELECT * FROM "+myDAtaBase.CAR_TB_NAME,null);

        if(cursor.moveToFirst()){
            do{
                int id =cursor.getInt(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_ID));
                String model =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_MODEL));
                String color =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_COLOR));
               String description = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DESCRIPTION));
               String image = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_IMAGE));
                double dpl =cursor.getDouble(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DPL));
               Car c = new Car (id,model,color,description , image , dpl);
//                Car c= new Car(id , model , color , dpl);
                cars.add(c);
            }while (cursor.moveToNext());
        }
        return cars ;
    }

    //search element function
    public ArrayList<Car> getCars(String modelSearch){
        ArrayList <Car> cars =new ArrayList<>();
        Cursor cursor= database.rawQuery(" SELECT * FROM "+myDAtaBase.CAR_TB_NAME+" WHERE "+myDAtaBase.CAR_CLN_MODEL+"=?",new String [] {modelSearch+ "%"} );
        //if cursor have data or not
        if(cursor.moveToFirst()){
            do{
                int id =cursor.getInt(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_ID));
                String model =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_MODEL));
                String color =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_COLOR));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_IMAGE));
                double dpl =cursor.getDouble(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DPL));
                Car c = new Car (id,model,color,description , image , dpl);
                //Car c = new Car(id , model , color , dpl);
                cars.add(c);
            }while (cursor.moveToNext());
        }
        return cars ;
    }


    public Car getCar(int id){
        Car car = null;
        Cursor cursor= database.rawQuery(" SELECT * FROM "+myDAtaBase.CAR_TB_NAME+" WHERE "+myDAtaBase.CAR_CLN_ID+"=?",new String [] {id + ""} );
        cursor.moveToNext();
        while(cursor.isAfterLast() == false){
            int id1 =cursor.getInt(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_ID));
            String model =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_MODEL));
            String color =cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_COLOR));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DESCRIPTION));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_IMAGE));
            double dpl =cursor.getDouble(cursor.getColumnIndexOrThrow(myDAtaBase.CAR_CLN_DPL));
            car = new Car (id1,model,color,description , image , dpl);
            cursor.moveToNext();
        }
        return car;
    }


}
