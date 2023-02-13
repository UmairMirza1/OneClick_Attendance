package com.example.oneclick_attendance.Activities.kotlin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.*

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
        ITeacherDao = TeacherFirebaseDAO(object : TeacherFirebaseDAO.observer {
            override fun update() {
                // TODO("Not yet implemented")
            }
        })

        userID = intent.getStringExtra("UserId");
        //Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();
        setViews();
        setListeners();
        IntentResults();
    }

    private fun IntentResults() {
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result?.let { intent ->
                        intent.data?.data?.let { readCSV(it).joinToString(separator = "\n") }
                    }
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
            //TODO: save to Firebase
        }
    }

    @Throws(IOException::class)
    fun readCSV(uri: Uri): List<String> {
        val csvFile = contentResolver.openInputStream(uri)
        val isr = InputStreamReader(csvFile)
        val Results = BufferedReader(isr).readLines()
        Log.d("csvresult", "ParseCsv: ${Results.toString()}")
        return Results
    }
//    private fun ParseCsv(uri: Uri) {
////        val file= File(uri.path.toString());
////        Log.d("file path", "ParseCsv: $file");
//
////        val inputStream = FileInputStream(file)
////        val reader = BufferedReader(InputStreamReader(inputStream))
////        val rows = mutableListOf<List<String>>()
////        while (true) {
////            val line = reader.readLine() ?: break
////            rows.add(line.split(","))
////        }
////
////        Log.d("csvresult", "ParseCsv: $rows")
////        inputStream.close()
//
//        val inputStream = contentResolver.openInputStream(uri)
//
//        val csvReader = CSVReaderBuilder(FileReader(uri.path.toString())).build()
//
//        // Read the CSV file into a list of arrays
//        var line: Array<String>? = csvReader.readNext()
//        while (line != null) {
//            // Do something with the data
//            Log.d("csvresult", "ParseCsv: $line")
//
//            line = csvReader.readNext()
//        }
//
//
//    }


}


