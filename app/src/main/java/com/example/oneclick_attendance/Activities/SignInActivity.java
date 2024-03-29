package com.example.oneclick_attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneclick_attendance.Activities.kotlin.DashboardActivity;
import com.example.oneclick_attendance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {


    EditText Email;

    EditText Password;

    Button Login, Signup;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        setViews();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();

            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
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

    void login() {
        auth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(res -> {
            if (res.isSuccessful()) {
                Intent intent = new Intent(this, DashboardActivity.class);
                FirebaseUser user = auth.getCurrentUser();
                assert user != null;
                String userId = user.getUid();
                //Toast.makeText(this, userId, Toast.LENGTH_LONG).show();
                intent.putExtra("UserId", userId);
                startActivity(intent);
                finish();
            } else {
                Exception ex = res.getException();
                String message = "";
                if (ex instanceof FirebaseAuthInvalidUserException) {
                    message = "User with this email does not exist";
                } else if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Incorrect credentials";
                } else {
                    message = "Unknown Error";
                }

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}