package com.example.oneclick_attendance.JavaClasses;

public class StudResObj {

    private String RollNo;
    boolean isPresent;

    public StudResObj(String rollNo, boolean isPresent) {
        RollNo = rollNo;
        this.isPresent = isPresent;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
