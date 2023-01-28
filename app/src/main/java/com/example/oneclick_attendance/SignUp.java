package com.example.oneclick_attendance;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                String  email = Email.getText().toString();
                String  pass= Pass.getText().toString();

              // Teacher newT=  new Teacher(Fname,Lname,email,pass);
                 setUpTeacherandcreateAuth(email,pass);


            }



        });

    }

    private void updateUI() {
    }

    private void setViews() {
        fName = findViewById(R.id.FirstName);
        lName  = findViewById(R.id.LastName);
        Email  = findViewById(R.id.email_signup);
        Pass=  findViewById(R.id.Password_signup);
        Signup_btn = findViewById(R.id.button_signUp);
    }

    private void setUpTeacherandcreateAuth(String email, String pass) {
        mAuth= FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d( "signupactivity","createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(SignUp.this , "You've signedup", Toast.LENGTH_SHORT);
                    //  updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signupactivity", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUp.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }
            }
        });
    }
}