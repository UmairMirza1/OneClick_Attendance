package com.example.oneclick_attendance.Activities.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO
import com.example.oneclick_attendance.Interface.ITeacherDao
import com.example.oneclick_attendance.JavaClasses.Section
import com.example.oneclick_attendance.JavaClasses.kotlin.CourseCount
import com.example.oneclick_attendance.R
import com.example.oneclick_attendance.Recycler.DashboardAdapter
import com.example.oneclick_attendance.Recycler.kotlin.CoursesRegisteredAdapter
import com.example.oneclick_attendance.ViewModel.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private val DataSet = ArrayList<Section>()
    private val number_of_columns = 2
    var userId: String? = null
    var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var Vm: DashboardViewModel? = null
    var mAdapter: DashboardAdapter? = null
    var dao: ITeacherDao? = null;
    lateinit var NewSection: Button;

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrList: ArrayList<CourseCount>
    private lateinit var coursesNameList: ArrayList<String>
    private lateinit var sectionNameList: ArrayList<String>
    private lateinit var stdCountList: ArrayList<String>
    private lateinit var teacherFirebase: TeacherFirebaseDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        teacherFirebase = TeacherFirebaseDAO(TeacherFirebaseDAO.observer { })
        SetViews()
        SetVmAndRv()


        var details = java.util.ArrayList<java.util.ArrayList<String>>()
        teacherFirebase.loadTeacherSections(
            object : DataCallBack {
                override fun onResponce(listOfCourses: java.util.ArrayList<java.util.ArrayList<String>>) {
//                            Log.e("dashdata", listOfCourses.toString())
                    details = listOfCourses
                    sectionNameList = listOfCourses[0]
                    coursesNameList = listOfCourses[1]
                    stdCountList = listOfCourses[2]

                    // let's refresh data
                    getTeacherCourses()

                    dataFromIntent


                }
            }, intent.getStringExtra("UserId")
        );

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        newRecyclerView = findViewById(R.id.coursesCardsRecycleView)
        newRecyclerView.layoutManager = GridLayoutManager(this, 2)
        newRecyclerView.setHasFixedSize(true)

        //void function
        setListener();

        newArrList = arrayListOf()

    }
    // to deal with asynchronous db calls we need callback function
    interface DataCallBack {
        fun onResponce(listOfCourses: java.util.ArrayList<java.util.ArrayList<String>>)
    }
    private fun setListener() {
        NewSection = findViewById(R.id.newSectionButton)
        NewSection.setOnClickListener {
            val newSection = Intent(this, NewSectionActivity::class.java).apply {
                putExtra("UserId", userId)
            }
            startActivity(newSection)
        }
    }

    private fun SetVmAndRv() {
        dao = TeacherFirebaseDAO { RefreshDataSet() }
        Vm = ViewModelProvider(this@DashboardActivity).get(DashboardViewModel::class.java)
        Vm!!.setDao(dao)
        Vm!!.setUserId(userId)
        layoutManager = GridLayoutManager(this, number_of_columns)
        val rv_main = findViewById<View>(R.id.coursesCardsRecycleView)
    }

    private fun RefreshDataSet() {
           //mAdapter.notifyDataSetChanged();
    }

    private fun SetViews() {}
    private val dataFromIntent: Unit
        private get() {
            val intent = intent
            userId = intent.getStringExtra("UserId")
        }

    private fun getTeacherCourses() {
        for (i in coursesNameList.indices) {
            val coursecount = CourseCount(coursesNameList[i], sectionNameList[i], stdCountList[i].toInt())
            newArrList.add(coursecount)
        }
        newRecyclerView.adapter = CoursesRegisteredAdapter(newArrList)
    }
}