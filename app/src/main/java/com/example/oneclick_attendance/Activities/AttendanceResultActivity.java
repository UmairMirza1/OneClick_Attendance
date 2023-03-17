package com.example.oneclick_attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.oneclick_attendance.R;

public class AttendanceResultActivity extends AppCompatActivity {

    public static final String TAG = "AttendanceResultActivity";

    public Button Save, ReTake;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_result);
        setViews();
        getIntentData();


    }

    private void getIntentData() {
        String result = getIntent().getStringExtra("result");
        Log.d(TAG, "getIntentData: " + result);
    }

    private void setViews() {
        Save = findViewById(R.id.attendance_save_button);
        ReTake = findViewById(R.id.attendance_retake_button);
        recyclerView = findViewById(R.id.attendance_result_recycler_view);
    }
}