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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable
import java.util.Objects.isNull

class DashboardActivity : AppCompatActivity(), CoursesRegisteredAdapter.ItemClickListener {
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
    private var coursesNameList: ArrayList<String> = ArrayList<String>()
    private var sectionNameList: ArrayList<String> = ArrayList<String>()
    private var stdCountList: ArrayList<String> =  ArrayList<String>()
    private var sectionsList: ArrayList<Section> = ArrayList<Section>()
    private lateinit var teacherFirebase: TeacherFirebaseDAO

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        teacherFirebase = TeacherFirebaseDAO(TeacherFirebaseDAO.observer { })


        var details = java.util.ArrayList<java.util.ArrayList<String>>()
        //userId =intent.getStringExtra("UserId")

        val user: FirebaseUser = auth.getCurrentUser()!!
        userId = user!!.uid
        teacherFirebase.loadTeacherSections(
            object : DataCallBack {
                override fun onResponce(listofSection: ArrayList<Section>){
                    Log.e("lengthOfSections: ",listofSection.size.toString())
                    // print all the sections
                    for (section in listofSection) {
                        coursesNameList.add(section.name)
                        sectionNameList.add(section.courseCode)
                        if (!isNull(section.registredStudents))
                            stdCountList.add(section.registredStudents.size.toString())
                        sectionsList.add(section)
                    }

                    // let's refresh data
                    getTeacherCourses()

                    //dataFromIntent
                    SetViews()
                    SetVmAndRv()


                }
            }, userId
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

    @Override
    override fun onItemClick(position: Int) {
        val intent = Intent(this, SectionActivity::class.java)
        intent.putExtra("selected_section", sectionsList[position] as Serializable)
        intent.putExtra("userID",userId)
        startActivity(intent)
    }
    // to deal with asynchronous db calls we need callback function
    interface DataCallBack {
        //        fun onResponce(listOfCourses: java.util.ArrayList<java.util.ArrayList<String>>)
        fun onResponce(listofSection: ArrayList<Section>)
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
        for (i in stdCountList.indices) {
            val coursecount =
                CourseCount(coursesNameList[i], sectionNameList[i], stdCountList[i].toInt())
            newArrList.add(coursecount)
        }
        newRecyclerView.adapter = CoursesRegisteredAdapter(newArrList,this)
    }
}