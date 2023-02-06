package com.example.oneclick_attendance.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.oneclick_attendance.Interface.ITeacherDao;
import com.example.oneclick_attendance.JavaClasses.Section;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private ArrayList<Section> Sections;

    ITeacherDao dao;

    private String userID;


    // TODO: Save a new section
    public void addSection(Section section){
        if (dao != null){
            dao.addSection(section);
        }
        else{
            Sections.add(section);
        }
    }


    public void setDao(ITeacherDao d){
        dao = d;
    }

    public void setUserId(String userId) {
        this.userID = userId;
    }
    public String getUserID() {
        return userID;
    }


//    public ArrayList<Section>  update(){
//        if (dao != null){
//            sections = Section.load(dao);
//        }
//        else sections = new ArrayList<Section>();
//        return sections;
//    }


//    public ArrayList<Section> getSections(String email){
//        if (sections == null){
//            if (dao != null){
//                sections = Section.load(dao,email);
//            }
//            else sections = new ArrayList<Section>();
//        }
//        else{
//            //sections = (ArrayList<Section>) savedInstanceState.get(key);
//            sections = new ArrayList<Section>();
//        }
//        return sections;
//    }

}
