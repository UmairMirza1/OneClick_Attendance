package com.example.oneclick_attendance.JavaClasses;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Attendance implements Serializable {

    public String date;
    public ArrayList<String> studentsList;

    // search this
   //Bitmap video;

    public Attendance() {
    }

    public Attendance(ArrayList<String> students_list) {
        //set date to todays date
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(System.currentTimeMillis());

        studentsList = students_list;
        //this.video = video;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<String> studentsList) {
        this.studentsList = studentsList;
    }
}
