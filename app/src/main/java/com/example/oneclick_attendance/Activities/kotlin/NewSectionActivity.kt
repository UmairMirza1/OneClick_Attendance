package com.example.oneclick_attendance.Activities.kotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.Interface.ITeacherDao
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.R

class NewSectionActivity : AppCompatActivity() {

    var courseName: EditText? = null;
    var courseCode: EditText? = null;
    var AddCsv: Button? = null;
    var SaveSection: Button? = null;
    var confirmCsv: ImageView? = null;
    var ITeacherDao: ITeacherDao? = null;

    var userID: String? = null;

    lateinit var Section: Section;
    lateinit var launcher: ActivityResultLauncher<Intent>;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_section)
        ITeacherDao=  TeacherFirebaseDAO(object : TeacherFirebaseDAO.observer {
            override fun update() {
                // TODO("Not yet implemented")
            }
        })
        setViews();
        setListeners();
        IntentResults();
    }

    private fun IntentResults() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
                confirmCsv?.setBackgroundResource(R.drawable.greentick)
            }
            }
        }


    private fun setViews() {
        courseCode = findViewById(R.id.NewSectionName);
        courseName = findViewById(R.id.NewSectionCourseCode);
        confirmCsv = findViewById(R.id.ConfirmCsv);
        SaveSection = findViewById(R.id.saveSection);
        AddCsv = findViewById(R.id.AddCsv);
    }


    private fun setListeners() {

        AddCsv?.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.type = "*/*";
            launcher.launch(intent);
        }

        SaveSection?.setOnClickListener {
            Toast.makeText(this, "Section Saved", Toast.LENGTH_SHORT).show();
            //TODO: Parse csv file and save to firebase
            ParseCsv();

        }
    }

    private fun ParseCsv() {
        TODO("Not yet implemented")
    }


}


