package com.example.oneclick_attendance.Activities.kotlin


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.Interface.ITeacherDao
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.JavaClasses.StudResObj
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.DetectionResultAdapter
import org.w3c.dom.Text

class show_std_attendance: AppCompatActivity() {
    lateinit var formulateAttendance: ArrayList<StudResObj>;
    var TeacherDAO: ITeacherDao? = null
    private var saveButton: Button? = null
    lateinit var attendName: TextView
    lateinit var courseName: TextView
    private lateinit var newRecyclerView: RecyclerView;
    var NotPresent: MutableList<String>? = null
    var present: ArrayList<String>? = null
    val TAG = "ShowAttendActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_std_attendance)
        //submit btn
        saveButton = findViewById(R.id.attendanceModifyBtn)

        attendName = findViewById(R.id.attendance_name)

        courseName = findViewById(R.id.courseName)

        TeacherDAO = TeacherFirebaseDAO {
            // TODO("Not yet implemented")
        }

        val attendance = intent.extras?.get("selected_attendance") as Attendance
        val section = intent.extras?.get("section") as Section
        val userID = intent.extras?.get("userID") as String
        Log.e("Section Students Passed: ", section.registredStudents.toString())
        Log.e("Attendance obj: ", attendance.studentsList.toString())

        attendName.text = attendance.getDate()
        courseName.text = section.courseName

        NotPresent = section.registredStudents
        present = attendance.studentsList

        formulateAttendanceFun(attendance.studentsList, section.registredStudents);

        saveButton?.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "saveButton: $present")
//            Attendance?.Students_list ?: present
            attendance.studentsList = present;

            (TeacherDAO as TeacherFirebaseDAO).saveAttendance(
                attendance,
                section.courseCode,
                userID
            )
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        })


        newRecyclerView = findViewById(R.id.stdAttendanceRecycler)
        newRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        val adapter = DetectionResultAdapter(
            formulateAttendance,
        )
        adapter.onItemClick = { StudResObj ->
            Log.d(TAG, "StdObjClicked  :" + StudResObj.rollNo)
        }
        adapter.ButtonOnCLick = { StudResObj ->
//
            var p = (present as ArrayList<String>?)
            if(p!!.contains(StudResObj.rollNo))
            {
                present?.remove(StudResObj.rollNo)
            }
            else {
                present?.add(StudResObj.rollNo)
            }

        }

        newRecyclerView.adapter = adapter

    }

    fun formulateAttendanceFun(presentStds: ArrayList<String>, allStds: List<String>){
        formulateAttendance = ArrayList<StudResObj>()
        allStds.toList().filter {std -> !presentStds.toList().contains(std)}
            .forEach { std -> formulateAttendance.add(StudResObj(std, false)) }

        presentStds.toList().forEach { std -> formulateAttendance.add(StudResObj(std, true)) }
    }
}