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
import com.example.oneclick_attendance.Activities.NewLectureActivity
import com.example.oneclick_attendance.Activities.kotlin.NewSectionActivity
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R
import org.w3c.dom.Text

class SectionActivity : AppCompatActivity() {
    lateinit var NewLec: Button;
    lateinit var userId: String;
    lateinit var courseTitle: TextView;
    lateinit var attendanceLauncher: ActivityResultLauncher<Intent>;
    var TAG = "SectionActivityTest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        courseTitle = findViewById(R.id.courseTitle)

        val section = intent.extras?.get("selected_section") as Section
        val usrId = intent.extras?.get("userID") as String
        attendanceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "onCreate: SectionActivityTest)");
            }
        }




        courseTitle.text = section.courseName
        NewLec = findViewById(R.id.new_lec_btn)
        NewLec.setOnClickListener {
            val newLec = Intent(this, NewLectureActivity::class.java).apply {
                putExtra("userID", usrId)
                putExtra("Section", section)
            }
            attendanceLauncher.launch(newLec)



        }
    }
}