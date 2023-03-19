package com.example.oneclick_attendance.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.oneclick_attendance.Activities.kotlin.DashboardActivity;
import com.example.oneclick_attendance.Activities.kotlin.SectionActivity;
import com.example.oneclick_attendance.Interface.ITeacherDao;
import com.example.oneclick_attendance.JavaClasses.Attendance;
import com.example.oneclick_attendance.JavaClasses.Section;
import com.example.oneclick_attendance.JavaClasses.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

public class TeacherFirebaseDAO implements ITeacherDao {

    public interface observer{
        void update();
    }

    private final observer observer;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ArrayList<Hashtable<String,String>> data;

    public TeacherFirebaseDAO(observer obs){
        observer = obs;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("OneClick");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    data = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String, String> obj = new Hashtable<String,String>();
                        assert map != null;
                        for(String key : map.keySet()){
                            obj.put(key, Objects.requireNonNull(map.get(key)).toString());
                        }
                        data.add(obj);
                    }

                    observer.update();
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });
    }


  //  @Override
//    public void saveTeacher(Hashtable<String, String> teacher) {
//        myRef.child("Teacher").setValue();
//    }

      @Override
    public void saveTeacher(Teacher teacher) {
        myRef.child("Teachers").child(teacher.getUID()).setValue(teacher);
    }
    @Override
    public void saveAttendance(Attendance attendance, String sectionId, String userId) {

        myRef.child("Teachers").child(userId).child("Sections").child(sectionId).child("Attendance").child(attendance.getDate()).setValue(attendance);

    }

    @Override
    public void addSection( String userId, Section section) {
        myRef.child("Teachers").child(userId).child("Sections").child(section.getCourseCode()).setValue(section);

    }


    @Override
    public void loadTeacherSections(DashboardActivity.DataCallBack callback, String usrId) {
        myRef = database.getReference("OneClick").child("Teachers").child(usrId).child("Sections");

        ArrayList<Section> sections = new ArrayList<Section>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Section section = d.getValue(Section.class);
                        assert section != null;
                        sections.add(section);
                        Log.d("regstudents", "onDataChange: "  + section.getRegistredStudents());
                    }
                    // observer.update();
                    callback.onResponce(sections);
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });
        //Log.e("data", String.valueOf(data.toArray().length));
    }

    @Override
    public void loadSectionAttendance(SectionActivity.CB_Attendance callback, String sectionId, String userId) {

        myRef = database.getReference("OneClick").child("Teachers").child(userId).child("Sections").child(sectionId).child("Attendance");


        ArrayList<Attendance> Attendances = new ArrayList<Attendance>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Attendance Attend = d.getValue(Attendance.class);
                        assert Attend != null;
                        Attendances.add(Attend);
                        Log.d("firebasedb", "onDataChange: "  + Attend.getStudentsList().toString());
                    }
                    // observer.update();
                    callback.onResponse(Attendances);
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });

    }

}
