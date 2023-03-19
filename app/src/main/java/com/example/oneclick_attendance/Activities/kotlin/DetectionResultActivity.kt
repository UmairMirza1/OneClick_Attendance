package com.example.oneclick_attendance.Activities.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.Interface.ITeacherDao
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.StudResObj
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.DetectionResultAdapter
import com.google.firebase.auth.FirebaseAuth


class DetectionResultActivity : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private var saveButton: Button? = null
    var NotPresent: MutableList<String>? = null
    var present: ArrayList<String>? = null
    var DatasetAttendance: ArrayList<StudResObj> = ArrayList()
    val TAG = "DetectionResultActivity"
    var TeacherDAO: ITeacherDao? = null
    var Attendance: Attendance? = null
    var courseCode: String? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection_result)
        saveButton = findViewById(R.id.attendanceResultSubmit_btn)

        val _intent = intent
        NotPresent = _intent.extras?.get("RegisteredStudents") as ArrayList<String>
        present = _intent.extras?.get("StudentsPresent") as ArrayList<String>
        courseCode = _intent.extras?.get("courseCode") as String
        Log.d(TAG, NotPresent.toString())
        Log.d(TAG, present.toString())

        TeacherDAO = TeacherFirebaseDAO(object : TeacherFirebaseDAO.observer {
            override fun update() {
                // TODO("Not yet implemented")
            }
        })

        prepareResult();

        saveButton?.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "saveButton: $present")
//            Attendance?.Students_list ?: present
            val attendance = Attendance(present as ArrayList<String>)
            (TeacherDAO as TeacherFirebaseDAO).saveAttendance(
                attendance,
                courseCode,
                auth.currentUser!!.uid
            )
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        })

        newRecyclerView = findViewById(R.id.attendanceResultRecycler)
        newRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        val adapter = DetectionResultAdapter(
            DatasetAttendance,
        )
        adapter.onItemClick = { StudResObj ->
            Log.d(TAG, "yooo  :" + StudResObj.rollNo)
        }
        adapter.ButtonOnCLick = { StudResObj ->
//            DatasetAttendance.get(DatasetAttendance.indexOf(StudResObj)).isPresent =
//                !DatasetAttendance.get(DatasetAttendance.indexOf(StudResObj)).isPresent
            present?.add(StudResObj.rollNo)
        }

        newRecyclerView.adapter = adapter
    }


    private fun prepareResult() {
        // use to prepare result
        for (student in present as MutableList<*>) {
            val obj = StudResObj(student.toString(), true)
            DatasetAttendance.add(obj)
        }
        for (student in NotPresent as MutableList<*>) {

            val obj = StudResObj(student.toString(), false)
            DatasetAttendance.add(obj)

        }

        val sortedList =
            DatasetAttendance.sortedWith(compareByDescending<StudResObj> { it.isPresent })
        DatasetAttendance = ArrayList(sortedList)
    }


}
