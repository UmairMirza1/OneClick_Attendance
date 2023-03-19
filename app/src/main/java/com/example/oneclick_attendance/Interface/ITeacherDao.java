package com.example.oneclick_attendance.Interface;

import com.example.oneclick_attendance.Activities.kotlin.DashboardActivity;
import com.example.oneclick_attendance.JavaClasses.Attendance;
import com.example.oneclick_attendance.JavaClasses.Section;
import com.example.oneclick_attendance.JavaClasses.Teacher;

import java.util.ArrayList;

public interface ITeacherDao {

    //void saveTeacher(Hashtable<String, String> teacher);

    void saveTeacher(Teacher teacher);
    void saveAttendance(Attendance attendance, String sectionId, String userId);

    void addSection(String userId, Section section);

    void loadTeacherSections(DashboardActivity.DataCallBack callback, String usrId);
}
