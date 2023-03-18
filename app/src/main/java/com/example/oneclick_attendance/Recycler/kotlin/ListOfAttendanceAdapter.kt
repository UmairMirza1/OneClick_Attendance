package com.example.oneclick_attendance.Recycler.kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.kotlin.CourseCount
import com.example.oneclick_attendance.R

class ListOfAttendanceAdapter (private val attendanceList: ArrayList<Attendance>, private val itemClickListener: ItemClickListener): RecyclerView.Adapter<ListOfAttendanceAdapter.MyViewHolder>() {
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

        val attendanceName: TextView = itemView.findViewById(R.id.attendance_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.attendancecard,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = attendanceList[position]
        holder.attendanceName.text = currentItem.Date + ".csv"
    }
}