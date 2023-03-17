package com.example.oneclick_attendance.Recycler.kotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.R

class DetectionResultAdapter (private val attendance: Attendance, private val itemClickListener: ItemClickListener): RecyclerView.Adapter<DetectionResultAdapter.MyViewHolder>() {
    var stdList = attendance.Students_list
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

        val stdName: TextView = itemView.findViewById(R.id.std_name)
        val stdRoll: TextView = itemView.findViewById(R.id.std_rollNo)
        val status: Button = itemView.findViewById(R.id.attendance_status)
        val present = true

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.resultcard,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = stdList[position]
        // Please update this as soon as you change the StudentList from String to Object
        holder.stdName.text = "Sajawal Ali";
        holder.stdRoll.text = "19L-2346";
        var studentStatus = true
        if (studentStatus == true){
            holder.status.setBackgroundColor(Color.GREEN)
        }
        else{
            holder.status.setBackgroundColor(Color.RED)
        }
    }
}