package com.example.oneclick_attendance.Recycler.kotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oneclick_attendance.JavaClasses.Attendance
import com.example.oneclick_attendance.JavaClasses.StudResObj
import com.example.oneclick_attendance.R

class DetectionResultAdapter(
    private val AttendanceResults: MutableList<StudResObj>
) : RecyclerView.Adapter<DetectionResultAdapter.MyViewHolder>() {
    var stdList = AttendanceResults

    var onItemClick: ((StudResObj) -> Unit)? = null

    var ButtonOnCLick: ((StudResObj) -> Unit)? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition >= 0) {
                    onItemClick?.invoke(AttendanceResults[adapterPosition])
                }
            }
        }


        val stdRoll: TextView = itemView.findViewById(R.id.std_rollNo)
        val status: Button = itemView.findViewById(R.id.attendance_status)

        fun bind (std: StudResObj) {

            stdRoll.text = std.rollNo
            if (std.isPresent) {
                status.setBackgroundResource(R.color.green)
                status.text = "Present"
            } else {
                status.setBackgroundResource(R.color.Red)
                status.text = "Absent"

            }



            status.setOnClickListener {
                if (adapterPosition >= 0) {
                    ButtonOnCLick?.invoke(AttendanceResults[adapterPosition])
                }
                if (status.text == "Present") {
                    status.setBackgroundResource(R.color.Red)
                    status.text = "Absent"
                } else {
                    status.setBackgroundResource(R.color.green)
                    status.text = "Present"
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.resultcard, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = stdList[position]
        holder.bind(currentItem)

    }
}