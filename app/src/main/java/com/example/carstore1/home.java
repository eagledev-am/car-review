package com.example.carstore1;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class home extends AppCompatActivity {
private RecyclerView rv ;
private FloatingActionButton fab;
private Toolbar toolbar ;
private CarRVAdapter carAdabter;
private DatabaseAccess databaseAccess ;
private static final int ADD_CAR_REQ_CODE =1;
private static final int EDIT_CAR_REQ_CODE =1;
private int cardId =-1 ;
public static final String  CAR_KEY= "car_key";
@SuppressLint("MissingInflatedId")
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        toolbar =findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        rv =findViewById(R.id.main_rv);
        fab=findViewById(R.id.homeAddCar);

        databaseAccess=DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<Car>cars =databaseAccess.getAllCars();
        databaseAccess.close();

        carAdabter =new CarRVAdapter(cars, new OnRecyclerViewOnClickListner() {
            @Override
            public void onItemClick(int carId) {
                Intent intent =new Intent(getBaseContext(),Details.class);
                intent.putExtra(CAR_KEY,carId);
                startActivityForResult(intent,EDIT_CAR_REQ_CODE);
            }
        });

        rv.setAdapter(carAdabter);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this ,2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);


    //check if i need add or view
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getBaseContext(),Details.class);
            startActivityForResult(intent,ADD_CAR_REQ_CODE);
        }
    });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Details.ADD_CAR_RESULT_CODE) {

            databaseAccess.open();
            ArrayList<Car> cars=databaseAccess.getAllCars();
            databaseAccess.close();
            carAdabter.setCars(cars);
            carAdabter.notifyDataSetChanged();

        }
    }


}

