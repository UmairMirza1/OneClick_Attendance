package com.example.oneclick_attendance;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

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
        myRef = database.getReference("OneCLick");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    data = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String,String> obj = new Hashtable<String,String>();
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


    @Override
    public void saveTeacher(Teacher teacher) {
        myRef.child("Teacher").child(teacher.getEmail()).setValue(teacher);
    }

    @Override
    public void saveAttendance(Attendance attendance) {

    }

    @Override
    public void addSection(Section section) {
    }

    @Override
    public void loadTeacher(String email) {
    }
}