package com.example.oneclick_attendance.JavaClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable {

    String name;
    ArrayList<Attendance> attendances;

    public Section(){}

    public Section(String name){
        this.name = name ;
        this.attendances=new ArrayList<Attendance>();
    }

    public Section(String name,  ArrayList<Attendance> attendances) {
        this.name = name;
        this.attendances = attendances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttendances( ArrayList<Attendance> attendances) {
        this.attendances = attendances;
    }

    public  ArrayList<Attendance> getAttendances() {
        return attendances;
    }



}
