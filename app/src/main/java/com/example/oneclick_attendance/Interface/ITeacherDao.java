package com.example.oneclick_attendance.Interface;

import com.example.oneclick_attendance.JavaClasses.Attendance;
import com.example.oneclick_attendance.JavaClasses.Section;
import com.example.oneclick_attendance.JavaClasses.Teacher;

public interface ITeacherDao {

    //void saveTeacher(Hashtable<String, String> teacher);

    void saveTeacher(Teacher teacher);
    void saveAttendance(Attendance attendance);

    void addSection(Section section);

    void loadTeacherSections(String email);
}
