package com.example.oneclick_attendance;


import java.util.ArrayList;

public class Teacher {

    String Email;
    String FirstName;
    String LastName;



    ArrayList<Section> sections;
    public Teacher(String email, String firstName, String lastName) {

        Email = email;
        FirstName = firstName;
        LastName = lastName;
        sections = new ArrayList<Section>() ;

    }

    public Teacher(String email, String firstName, String lastName,ArrayList<Section> section) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        sections = section;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }





    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}
