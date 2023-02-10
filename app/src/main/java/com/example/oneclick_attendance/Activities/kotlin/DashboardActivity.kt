package com.example.oneclick_attendance.Activities.kotlin

import android.os.Bundle
import android.view.View
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
    var dao: ITeacherDao? = null


    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrList: ArrayList<CourseCount>
    private lateinit var coursesNameList : Array<String>
    private lateinit var sectionNameList : Array<String>
    private lateinit var stdCountList : Array<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        newRecyclerView = findViewById(R.id.coursesCardsRecycleView)
        newRecyclerView.layoutManager = GridLayoutManager(this,2)
        newRecyclerView.setHasFixedSize(true)

        newArrList = arrayListOf<CourseCount>()

        // test data
        coursesNameList = arrayOf(
            "American History", "Data Science", "Data Warehouse", "Probability & Statistics"
        )
        sectionNameList = arrayOf(
            "BCS-8A", "BCS-8A", "BCS-7A", "BCS-1A"
        )
        stdCountList = arrayOf(
            50,47,40,30
        )

        getTeacherCourses()

        dataFromIntent
        SetViews()
        SetVmAndRv()


    }

    private fun SetVmAndRv() {
        dao = TeacherFirebaseDAO { RefreshDataSet() }
        Vm = ViewModelProvider(this@DashboardActivity).get(DashboardViewModel::class.java)
        Vm!!.setDao(dao)
        Vm!!.setUserId(userId)
        layoutManager = GridLayoutManager(this, number_of_columns)
        val rv_main = findViewById<View>(R.id.coursesCardsRecycleView) as RecyclerView
    }

    private fun RefreshDataSet() {
        // Call  mAdapter.notifyDataSetChanged();
    }

    private fun SetViews() {}
    private val dataFromIntent: Unit
        private get() {
            val intent = intent
            userId = intent.getStringExtra("UserId")
        }

    private fun getTeacherCourses()
    {
        for(i in coursesNameList.indices){
            val coursecount = CourseCount(coursesNameList[i],sectionNameList[i],stdCountList[i])
            newArrList.add(coursecount)
        }
        newRecyclerView.adapter = CoursesRegisteredAdapter(newArrList)
    }
}