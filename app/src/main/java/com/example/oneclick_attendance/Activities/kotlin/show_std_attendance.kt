package com.example.oneclick_attendance.Activities.kotlin


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.accessibilityservice.GestureDescription
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.Interface.ITeacherDao
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.JavaClasses.StudResObj
import com.example.oneclick_attendance.JavaClasses.kotlin.CSVConvert
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.kotlin.DetectionResultAdapter
import com.google.android.gms.common.api.GoogleApi
import org.apache.commons.lang3.builder.Builder
import java.io.File


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
    val csvConverter = CSVConvert()
    lateinit var csvString: String;
    private var exportBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_std_attendance)
        //submit btn
        saveButton = findViewById(R.id.attendanceModifyBtn)

        exportBtn = findViewById(R.id.export_csv)

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


        exportBtn?.setOnClickListener {
            val path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val letDirectory = File(path, "attendances")
            letDirectory.mkdirs()
            Log.e("path: ",letDirectory.toString())
            var fileName = section.courseName + "_" + attendance.getDate() + ".csv"
            //formulate CSV File!
            convertIntoCsv();

            val file = File(letDirectory, fileName)
            // write to the created file
            file.appendText(csvString)

        }



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

    fun convertIntoCsv(){
        csvString = csvConverter.csvOf(listOf("RollNo", "Status"), formulateAttendance){
            listOf(it.rollNo.toString(), it.isPresent.toString())
        }
    }




}