package com.example.oneclick_attendance.Activities.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R

class show_std_attendance: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_std_attendance)
        val attendance = intent.extras?.get("selected_attendance") as Attendance
        Log.e("Attendance obj: ", attendance.toString())
    }
}