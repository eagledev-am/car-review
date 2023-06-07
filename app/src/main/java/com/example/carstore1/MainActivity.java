package com.example.carstore1;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextInputEditText userName , passwordI;
    Button login , register;

    UserDatabaseAccess userDb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.user_name_input);
        passwordI = findViewById(R.id.password_input);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);



        // login
        login.setOnClickListener(a->{
            String user_name = userName.getText().toString();
            String password = passwordI.getText().toString();
            if(user_name.equals("")){
                userName.setError("required");
            }
            else if(password.equals("")){
                passwordI.setError("required");
            }
            else {
                userDb = UserDatabaseAccess.getInstance(this);
                userDb.open();
                User user = userDb.getUser(user_name, password);
                userDb.close();
                Log.d("User", user.getName());
                if (user.getId() != -1) {
                    // where code of next intent should be
                    Intent intent = new Intent(this, home.class);
                    startActivity(intent);
                } else {
                    Snackbar bar = Snackbar.make(login, "username or password is incorrect", Snackbar.LENGTH_LONG);
                    bar.setAction("ok", ac -> {

                    });
                    bar.show();
                }
            }
            Log.d("after button" , "1");
        });

        register.setOnClickListener( a ->{
            Intent intent = new Intent(this , Register.class);
            startActivity(intent);
                });


    }
}
// content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F15/ORIGINAL/NONE/1117463303
