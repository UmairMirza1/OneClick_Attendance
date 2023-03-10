package com.example.oneclick_attendance.Activities.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section)

        courseTitle = findViewById(R.id.courseTitle)

        val section = intent.extras?.get("selected_section") as Section
        var usrId = intent.extras?.get("userID") as String


        courseTitle.text = section.courseName

        NewLec = findViewById(R.id.new_lec_btn)
        NewLec.setOnClickListener {
            val newLec = Intent(this, NewLectureActivity::class.java).apply {
                putExtra("UserId", usrId)
            }
            startActivity(newLec)
        }
    }
}