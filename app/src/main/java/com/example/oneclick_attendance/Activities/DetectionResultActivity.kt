package com.example.oneclick_attendance.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.DetectionResultAdapter

class DetectionResultActivity : AppCompatActivity(), DetectionResultAdapter.ItemClickListener {
    private lateinit var newRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection_result)
        newRecyclerView = findViewById(R.id.attendanceResultRecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var stds = ArrayList<String>()
        stds.add("Sajawal ALi")
        stds.add("Shmoon?")
        var dummyObj = Attendance("17/3/2023",stds,null);
        newRecyclerView.adapter = DetectionResultAdapter(dummyObj, this)
    }
    @Override
    override fun onItemClick(position: Int) {
        // use to change absent/present status
    }
}
