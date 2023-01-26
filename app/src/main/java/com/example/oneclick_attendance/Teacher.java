package com.example.oneclick_attendance;


import java.util.ArrayList;

public class Teacher {

    String Email;
    String FirstName;
    String LastName;
    String Password;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    ArrayList<Section> sections;
    public Teacher(String email, String firstName, String lastName, String password) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        sections = new ArrayList<Section>() ;
    }

    public Teacher(String email, String firstName, String lastName, String password,ArrayList<Section> section) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        sections = section;
    }
}
