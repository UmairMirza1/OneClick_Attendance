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
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.Activities.NewLectureActivity
import com.example.oneclick_attendance.Activities.kotlin.NewSectionActivity
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class SectionActivity : AppCompatActivity() {
    lateinit var NewLec: Button;
    lateinit var userId: String;
    lateinit var courseTitle: TextView;
    lateinit var attendanceLauncher: ActivityResultLauncher<Intent>;
    var TAG = "SectionActivityTest"
    var Rv : RecyclerView? = null;
    private lateinit var teacherFirebase: TeacherFirebaseDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        courseTitle = findViewById(R.id.courseTitle)




        val section = intent.extras?.get("selected_section") as Section
        userId = intent.extras?.get("userID") as String
        attendanceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "onCreate: SectionActivityTest)");
            }
        }
        teacherFirebase = TeacherFirebaseDAO(TeacherFirebaseDAO.observer { })
        teacherFirebase.loadSectionAttendance(
            object : CB_Attendance {
                override fun onResponse(listofSection: ArrayList<Attendance>){
                    Log.e("lengthOfSections: ",listofSection.size.toString())
                    // print all the sections
//                    for (section in listofSection) {
//                        coursesNameList.add(section.name)
//                        sectionNameList.add(section.courseCode)
//                        if (!Objects.isNull(section.registredStudents))
//                            stdCountList.add(section.registredStudents.size.toString())
//                        sectionsList.add(section)
//                    }

//                    // let's refresh data
//                    getTeacherCourses()
//
//                    //dataFromIntent
//
//                    SetViews()
//                    SetVmAndRv()

                }
            }, section.courseCode , userId
        );



        Rv = findViewById(R.id.attendanceListRecycler);




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
        fun onResponse(listofSection: ArrayList<Attendance>)
    }
}