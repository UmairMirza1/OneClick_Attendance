package com.example.oneclick_attendance;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// A lot of work to do here
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashBoardViewHolder>{


    public class DashBoardViewHolder extends RecyclerView.ViewHolder {

        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



    @NonNull
    @Override
    public DashboardAdapter.DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.DashBoardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
