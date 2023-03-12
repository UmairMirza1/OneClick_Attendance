package com.example.oneclick_attendance.Activities.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.Activities.NewLectureActivity
import com.example.oneclick_attendance.Activities.kotlin.NewSectionActivity
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.CoursesRegisteredAdapter
import com.example.oneclick_attendance.Recycler.kotlin.ListOfAttendanceAdapter
import org.w3c.dom.Text
import java.io.Serializable

class SectionActivity : AppCompatActivity(), ListOfAttendanceAdapter.ItemClickListener {
    lateinit var NewLec: Button;
    lateinit var userId: String;
    var attendanceList: ArrayList<Attendance> = ArrayList<Attendance>()
    lateinit var courseTitle: TextView;
    private lateinit var newRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        courseTitle = findViewById(R.id.courseTitle)

        val section = intent.extras?.get("selected_section") as Section
        var usrId = intent.extras?.get("userID") as String

        if (!section.attendances.isNullOrEmpty()) {
            attendanceList = section.attendances
        }
        else
        {
            attendanceList.add(Attendance("Lecture 1-19Oct2022",null,null))
        }

        courseTitle.text = section.courseName

        NewLec = findViewById(R.id.new_lec_btn)
        NewLec.setOnClickListener {
            val newLec = Intent(this, NewLectureActivity::class.java).apply {
                putExtra("UserId", usrId)
            }
            startActivity(newLec)
        }

        newRecyclerView = findViewById(R.id.attendanceListRecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Log.e("ReclyerSetup: ", attendanceList.size.toString())
        newRecyclerView.adapter = ListOfAttendanceAdapter(attendanceList,this);
    }

    @Override
    override fun onItemClick(position: Int) {
//        val intent = Intent(this, SectionActivity::class.java)
//        intent.putExtra("selected_attendance", attendanceList[position] as Serializable)
//        intent.putExtra("userID",userId)
//        startActivity(intent)
    }
}