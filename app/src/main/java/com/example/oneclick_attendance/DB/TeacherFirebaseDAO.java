package com.example.oneclick_attendance.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.oneclick_attendance.Activities.kotlin.DashboardActivity;
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
    public void saveAttendance(Attendance attendance) {

    }

    @Override
    public void addSection( String userId, Section section) {
        myRef.child("Teachers").child(userId).child("Sections").child(section.getCourseCode()).setValue(section);

    }







    @Override
    public void loadTeacherSections(DashboardActivity.DataCallBack callback, String usrId) {
        myRef = database.getReference("OneClick").child("Teachers").child(usrId).child("Sections");
        ArrayList<String> coursesName = new ArrayList<String>();
        ArrayList<String> coursesCode = new ArrayList<String>();
        ArrayList<String> studentsCount = new ArrayList<String>();
        ArrayList<ArrayList<String>> details = new ArrayList<ArrayList<String>>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        coursesName.add(d.getKey());
                        coursesCode.add(d.child("coursename").getValue(String.class));
                        DatabaseReference rs;
                        int stdCount = (int) d.child("registredStudents").getChildrenCount();
                        studentsCount.add(Integer.toString(stdCount));
                    }

                    observer.update();
                    details.add(coursesName);
                    details.add(coursesCode);
                    details.add(studentsCount);
                    callback.onResponce(details);
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

}
