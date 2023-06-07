package com.example.carstore1;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
    public static final int ADD_CAR_RESULT_CODE =2 ;
    public static final int PICK_IMAGE_REQ_CODE = 1;

    public static final int EDIT_CAR_RESULT_CODE =3 ;
    private Toolbar toolbar ;
    private TextInputEditText et_model,et_color,et_dpl,et_description ;
    private ImageView iv ;
    private Uri imageUri;
    private int carId = -1 ;
    private DatabaseAccess db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details4);
        toolbar =findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        db=DatabaseAccess.getInstance(this);
        et_model = findViewById(R.id.details);
        et_color =findViewById(R.id.color);
        et_dpl =findViewById(R.id.distancePerLetter);
        et_description =findViewById(R.id.description);
        iv = findViewById(R.id.image_details);

        Intent intent =getIntent();
        db = DatabaseAccess.getInstance(this);
        carId =intent.getIntExtra(home.CAR_KEY,-1);
        if (carId==-1){
            enableFields();
            clearFields();
        }else{
//            show
            disableFields();
            db.open();
            Car car = db.getCar(carId);
            db.close();
            if(car.getImage() != null && !car.getImage().equals("")){
                iv.setImageURI(Uri.parse(car.getImage()));
                et_model.setText(car.getModel());
                et_color.setText(car.getColor());
                et_dpl.setText(car.getDpl() + "");
                et_description.setText(car.getDescription());
            }

        }

        iv.setOnClickListener(a->{
            Intent in = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(in ,PICK_IMAGE_REQ_CODE);
        });

    }

    private void disableFields (){
            iv.setEnabled(false);
            et_model.setEnabled(false);
            et_description.setEnabled(false);
            et_color.setEnabled(false);
            et_dpl.setEnabled(false);

    }
    private void enableFields (){
            iv.setEnabled(true);
            et_model.setEnabled(true);
            et_description.setEnabled(true);
            et_color.setEnabled(true);
            et_dpl.setEnabled(true);

    }
    private void clearFields (){
            iv.setImageURI(null);
            et_model.setText("");
            et_description.setText("");
            et_color.setText("");
            et_dpl.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        MenuItem save=menu.findItem(R.id.details_menu_save);
        MenuItem edit=menu.findItem(R.id.details_menu_edit);
        MenuItem delete=menu.findItem(R.id.details_menu_delete);
        if(carId == -1){
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        }
        else{
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQ_CODE && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.getData();
                iv.setImageURI(imageUri);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.details_menu_save){
            String model = et_model.getText().toString();
            String color = et_color.getText().toString();
            String description = et_description.getText().toString();
            double dpl = Double.parseDouble(et_dpl.getText().toString());
            String image = "";
            if(imageUri != null)
                  image = imageUri.toString();
            Car car = new Car(carId , model , color , description , image , dpl);

            DatabaseAccess db = DatabaseAccess.getInstance(this);
            db.open();

            if(carId == -1){
                 boolean flag = db.insertCar(car);
                if(flag == true) {
                    Toast.makeText(this, "data inserted success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this , home.class);
                    startActivity(intent);
                    setResult(ADD_CAR_RESULT_CODE);
                    finish();
                }
            }
            else{
                boolean flag = db.updateCar(car);
                if(flag == true) {
                    Toast.makeText(this, "data updating success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this , home.class);
                    startActivity(intent);
                    finish();
                }
            }
            db.close();
            return true;
        }
        else if (item.getItemId() == R.id.details_menu_edit) {
                enableFields();
                MenuItem save=toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit=toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete=toolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;

        }
        else {
            String model = "" , color = "" , description = "" ;
            double dpl;
            if(et_model.getText().toString().isEmpty()) {
                model = "";
            }else{
                 model = et_model.getText().toString();
            }

            if(et_color.getText().toString().isEmpty()) {
                color = "";
            }else{
               color = et_color.getText().toString();
            }

            if(et_description.getText().toString().isEmpty()) {
                 description = "";
            }else{
                 description = et_model.getText().toString();
            }

            if(et_dpl.getText().toString().isEmpty()){
                dpl = 0;
            }
            else{
                 dpl = Double.parseDouble(et_dpl.getText().toString());
            }

            String image = "";
            if(imageUri != null)
                image = imageUri.toString();
            Car car = new Car(carId , model , color , description , image , dpl);
//            if(carId == -1){
                 DatabaseAccess db = DatabaseAccess.getInstance(this);
                 db.open();
                 boolean flag = db.deleteCar(car);
                 db.close();
                 if(flag == true){
                     Toast.makeText(this , "car deleted successfully" , Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(this , home.class);
                     startActivity(intent);
                     setResult(ADD_CAR_RESULT_CODE);
                     finish();
                 }
//            }
            return true;
        }
    }


    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        String model,color,desc,image="";
//        Double dbl;
//        db.open();
//        switch (item.getItemId()){
//            case R.id.details_menu_save:
//                imageUri=im
//                model=et_model.getText().toString();
//                color=et_color.getText().toString();
//                desc=et_description.getText().toString();
//                dbl=Double.parseDouble(et_dpl.getText().toString());
//                if ( !=null)
//                    image=imageUri.toString();
//                boolean res;
//                Car c=new Car(carId,model,color,dbl,image,desc);
//                if (carId==-1){
//                    res= db.insert(c);
//                    if (res){
//                        Toast.makeText(this, "car added successfully", Toast.LENGTH_SHORT).show();
//                        setResult(ADD_CAR_RESULT_CODE,null);
//                        finish();
//                    }else{
//                        res=db.updateCar(c);
//                        if (res) {
//                            Toast.makeText(this, "car modified successfully", Toast.LENGTH_SHORT).show();
//                            setResult(ADD_CAR_RESULT_CODE, null);
//                            finish();
//                        }
//                    }
//                }
//                return true;
//            case R.id.details_menu_edit:
//                enableFields();
//                MenuItem save=toolbar.getMenu().findItem(R.id.details_menu_save);
//                MenuItem edit=toolbar.getMenu().findItem(R.id.details_menu_edit);
//                MenuItem delete=toolbar.getMenu().findItem(R.id.details_menu_delete);
//                delete.setVisible(false);
//                edit.setVisible(false);
//                save.setVisible(true);
//                return true;
//            case R.id.details_menu_delete:
//                c=new Car(carId,null,null,0,null,null);
//
//                res= db.deleteCar(c);
//                if (res){
//                    Toast.makeText(this, "car deleted successfully", Toast.LENGTH_SHORT).show();
//                    setResult(EDIT_CAR_RESULT_CODE,null);
//                    finish();
//                }
//                return true;
//
//        }
//        db.close();
//        return false;
//
//    }

}