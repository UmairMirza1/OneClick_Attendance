package com.example.oneclick_attendance.Recycler.kotlin

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.StudResObj
import com.example.oneclick_attendance.JavaClasses.kotlin.CourseCount
import com.example.oneclick_attendance.R

class ListOfAttendanceAdapter (private val attendanceList: ArrayList<Attendance>, private val itemClickListener: ItemClickListener): RecyclerView.Adapter<ListOfAttendanceAdapter.MyViewHolder>() {


    interface ItemClickListener {
        fun onItemClick(position: Int, type: Int)
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            var deleteBtn = itemView.findViewById<ImageView>(R.id.delete_click);
            var editBtn = itemView.findViewById<ImageView>(R.id.edit_click);
            deleteBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onItemClick(adapterPosition, 1);
                }
            }
            editBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onItemClick(adapterPosition, 0);
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
        holder.attendanceName.text = currentItem.date + ".csv"
    }
}