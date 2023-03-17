package com.example.oneclick_attendance.JavaClasses;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Attendance {

    public String Date;
    public ArrayList<String> Students_list;

    // search this
    Bitmap video;

    public Attendance(String date, ArrayList<String> students_list, Bitmap video) {
        Date = date;
        Students_list = students_list;
        this.video = video;
    }

}
