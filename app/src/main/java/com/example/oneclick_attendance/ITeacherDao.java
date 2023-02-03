package com.example.oneclick_attendance;

public interface ITeacherDao {

    void saveTeacher(Teacher teacher);

    void saveAttendance(Attendance attendance);

    void addSection(Section section);

}
