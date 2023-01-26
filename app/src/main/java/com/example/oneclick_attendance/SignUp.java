package com.example.oneclick_attendance;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText fName,lName, Email, Pass;
    Button Signup_btn;

    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setViews();
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // call firebase  here
                String  Fname= fName.getText().toString();
                String  Lname= lName.getText().toString();
                String  email = fName.getText().toString();
                String  pass= fName.getText().toString();

               Teacher newT=  new Teacher(Fname,Lname,email,pass);
               setUpTeacherandcreateAuth();




            }

            private void setUpTeacherandcreateAuth() {
            }
        });

    }

    private void setViews() {
        fName = findViewById(R.id.FirstName);
        lName  = findViewById(R.id.LastName);
        Email  = findViewById(R.id.email_signup);
        Pass=  findViewById(R.id.Password_signup);
        Signup_btn = findViewById(R.id.button_signUp);


    }
}