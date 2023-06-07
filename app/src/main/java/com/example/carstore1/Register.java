package com.example.carstore1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {
    TextInputEditText fullName , userName , passwordU;
    Button send , backTo;
    UserDatabaseAccess userDb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = findViewById(R.id.full_name_reg);
        userName = findViewById(R.id.user_name_reg);
        passwordU = findViewById(R.id.password_reg);
        send = findViewById(R.id.save);
        backTo = findViewById(R.id.backToLogin);

        send.setOnClickListener(a->{
            String name = fullName.getText().toString();
            String password= passwordU.getText().toString();
            String user_name = userName.getText().toString();
            if(name.equals("")){
                fullName.setError("required");
            }
            else if(password.equals("")){
                passwordU.setError("required");
            }
            else if(user_name.equals("")){
                userName.setError("required");
            }else {
                User user = new User(name , user_name , password);
                userDb = UserDatabaseAccess.getInstance(this);
                userDb.open();
                try{
                if(userDb.insertUser(user)){
                    Intent intent = new Intent(this , MainActivity.class);
                    startActivity(intent);
                }
                }
                catch (Exception exception){
                    Snackbar.make(send , exception.getMessage() , Snackbar.LENGTH_LONG).show();
                }
                userDb.close();
            }

        });

        backTo.setOnClickListener(a->{
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
        });
    }
}