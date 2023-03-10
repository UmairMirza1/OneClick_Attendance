package com.example.oneclick_attendance.Recycler.kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.kotlin.CourseCount
import com.example.oneclick_attendance.R


class CoursesRegisteredAdapter (private val coursesList: ArrayList<CourseCount>,private val itemClickListener: ItemClickListener): RecyclerView.Adapter<CoursesRegisteredAdapter.MyViewHolder>() {
    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onItemClick(adapterPosition)
                }
            }
        }

        val courseName: TextView = itemView.findViewById(R.id.courseName)
        val sectionName: TextView = itemView.findViewById(R.id.sectionID)
        val stdCount: TextView = itemView.findViewById(R.id.stdCount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.coursecard,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = coursesList[position]
        holder.courseName.text = currentItem.courseName
        holder.sectionName.text = currentItem.courseSection
        holder.stdCount.text = currentItem.studentCount.toString()
    }
}