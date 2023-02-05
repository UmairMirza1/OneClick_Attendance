package com.example.oneclick_attendance.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oneclick_attendance.JavaClasses.Section;
import com.example.oneclick_attendance.Recycler.DashboardAdapter;
import com.example.oneclick_attendance.Recycler.DashboardViewModel;
import com.example.oneclick_attendance.Interface.ITeacherDao;
import com.example.oneclick_attendance.R;
import com.example.oneclick_attendance.DB.TeacherFirebaseDAO;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ArrayList<Section> DataSet = new ArrayList<Section>();
    private int number_of_columns = 2;
    String userId;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    DashboardViewModel Vm;
    DashboardAdapter mAdapter;

    ITeacherDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getDataFromIntent();
        SetViews();
        SetVmAndRv();
    }

    private void SetVmAndRv() {
        dao = new TeacherFirebaseDAO(new TeacherFirebaseDAO.observer() {
            @Override
            public void update() {
                RefreshDataSet();
            }
        });
        Vm = new ViewModelProvider(DashboardActivity.this).get(DashboardViewModel.class);

        Vm.setDao(dao);
        Vm.setUserId(userId);

        layoutManager = new GridLayoutManager(this, number_of_columns);
        RecyclerView rv_main = (RecyclerView) findViewById(R.id.SectionsList);

    }

    private void RefreshDataSet() {
        // Call  mAdapter.notifyDataSetChanged();
    }

    private void SetViews() {

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
    }


}
