package com.example.oneclick_attendance.Activities.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.Activities.NewLectureActivity
import com.example.oneclick_attendance.Activities.kotlin.NewSectionActivity
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.ListOfAttendanceAdapter
import org.w3c.dom.Text
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class SectionActivity : AppCompatActivity(),ListOfAttendanceAdapter.ItemClickListener {
    lateinit var NewLec: Button;
    lateinit var userId: String;
    lateinit var courseTitle: TextView;
    lateinit var attendanceLauncher: ActivityResultLauncher<Intent>;
    var TAG = "SectionActivityTest"
    lateinit var attendanceListRecycler:RecyclerView;
    private lateinit var teacherFirebase: TeacherFirebaseDAO
    var AttendanceList: ArrayList<Attendance> = ArrayList<Attendance>()
    lateinit var section: Section;

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        courseTitle = findViewById(R.id.courseTitle)

        section = intent.extras?.get("selected_section") as Section
        userId = intent.extras?.get("userID") as String
        attendanceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "onCreate: SectionActivityTest)");
            }
        }

        teacherFirebase = TeacherFirebaseDAO(TeacherFirebaseDAO.observer { })
        teacherFirebase.loadSectionAttendance(
            object : CB_Attendance {
                override fun onResponse(listOfAttendance: ArrayList<Attendance>){
                    Log.e("NumberOfAttendances: ",listOfAttendance.size.toString())
               //      print all the sections
                    for (attendance in listOfAttendance) {
                        AttendanceList.add(attendance)
                        Log.e("Attendance: ",attendance.toString())

                    }
                }
            }, section.courseCode , userId
        );

        attendanceListRecycler = findViewById(R.id.attendanceListRecycler);
        attendanceListRecycler.layoutManager = LinearLayoutManager(this)
        attendanceListRecycler.adapter = ListOfAttendanceAdapter(AttendanceList,this);

        courseTitle.text = section.courseName
        NewLec = findViewById(R.id.new_lec_btn)
        NewLec.setOnClickListener {
            val newLec = Intent(this, NewLectureActivity::class.java).apply {
                putExtra("userID", userId)
                putExtra("Section", section)
            }
            attendanceLauncher.launch(newLec)



        }

    }

    interface CB_Attendance{
        //        fun onResponce(listOfCourses: java.util.ArrayList<java.util.ArrayList<String>>)
        fun onResponse(listOfAttendance: ArrayList<Attendance>)
    }
    @Override
    override fun onItemClick(position: Int) {
        val intent = Intent(this, show_std_attendance::class.java)
        intent.putExtra("selected_attendance", AttendanceList[position] as Serializable)
        intent.putExtra("section", section as Section)
        intent.putExtra("userID",userId)
        startActivity(intent)
    }

}