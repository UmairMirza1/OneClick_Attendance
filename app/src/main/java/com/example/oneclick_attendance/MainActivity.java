package com.example.oneclick_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText Email;

    EditText Password;

    Button Login, Signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =  Email.getText().toString();
                String pass =  Password.getText().toString();




            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });


    }

    private void setViews() {
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.button_login);
        Signup = findViewById(R.id.signup);
    }
}