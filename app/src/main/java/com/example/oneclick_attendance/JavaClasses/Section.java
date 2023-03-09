package com.example.oneclick_attendance.JavaClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable {

    String Coursename;

    String courseCode;

    public String getStudentsCount() {
        return StudentsCount;
    }

    List<String> RegistredStudents;

    String StudentsCount;
    ArrayList<Attendance> attendances;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public Section() {
    }

    public Section(
            String name,
            String courseCode,
            List<String> RegistredStudents
    ) {
        this.Coursename = name;
        this.courseCode = courseCode;
        this.RegistredStudents = RegistredStudents;
        this.StudentsCount = String.valueOf(RegistredStudents.size());
        this.attendances = new ArrayList<>();
    }



    public Section(String name) {
        this.Coursename = name;
        this.attendances = new ArrayList<>();
    }

    public Section(String name, ArrayList<Attendance> attendances) {
        this.Coursename = name;
        this.attendances = attendances;
    }

    public String getCourseName() {
        return Coursename;
    }

    public void setCourseName(String coursename) {
        Coursename = coursename;
    }

    public List<String> getRegistredStudents() {
        return RegistredStudents;
    }

    public void setRegistredStudents(List<String> registredStudents) {
        RegistredStudents = registredStudents;
    }

    public String getName() {
        return Coursename;
    }

    public void setName(String name) {
        this.Coursename = name;
    }

    public void setAttendances(ArrayList<Attendance> attendances) {
        this.attendances = attendances;
    }

    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }


}
