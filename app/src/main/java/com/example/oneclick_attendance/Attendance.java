package com.example.oneclick_attendance;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Attendance {

    String Date;
    ArrayList<String> Students_list;

    // sedarch this
    Bitmap video;

    public Attendance(String date, ArrayList<String> students_list, Bitmap video) {
        Date = date;
        Students_list = students_list;
        this.video = video;
    }
}
