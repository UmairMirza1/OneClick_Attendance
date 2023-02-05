package com.example.oneclick_attendance.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.oneclick_attendance.Interface.ITeacherDao;
import com.example.oneclick_attendance.JavaClasses.Teacher;
import com.example.oneclick_attendance.R;
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    EditText fName, lName, Email, Pass;
    Button Signup_btn;

    FirebaseAuth mAuth;

    ITeacherDao dao ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setViews();
        setListeners();
        mAuth = FirebaseAuth.getInstance();
        dao = new TeacherFirebaseDAO(new TeacherFirebaseDAO.observer() {
            @Override
            public void update() {


            }
        });

    }



    private void updateUI() {
        Intent Signin = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(Signin);

    }

    private void setViews() {

        fName = findViewById(R.id.FirstName);
        lName = findViewById(R.id.LastName);
        Email = findViewById(R.id.email_signup);
        Pass = findViewById(R.id.Password_signup);
        Signup_btn = findViewById(R.id.button_signUp);
    }

    private void setUpTeacherandcreateAuth(String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("signupactivity", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(SignUpActivity.this, "You've signedup", Toast.LENGTH_SHORT).show();
                    assert user != null;
                    CreateTeacherJsonTree(user.getUid());
                    updateUI();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signupactivity", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }
            }
        });
    }

    private void CreateTeacherJsonTree(String uid) {
        Teacher newTeacher =  new Teacher(uid,Email.getText().toString(),fName.getText().toString(),lName.getText().toString());
//        Hashtable<String, String > Teacher = new Hashtable<String, String>();
//        // add  attribute as key value pair
//
//        Teacher.put("firstName", fName.getText().toString());
//        Teacher.put("lastName", lName.getText().toString());
//        Teacher.put("email", Email.getText().toString());
//        Teacher.put("password", Pass.getText().toString());
        dao.saveTeacher(newTeacher);

    }

    private void setListeners() {
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpTeacherandcreateAuth( Email.getText().toString(), Pass.getText().toString());

            }
        });
    }



}