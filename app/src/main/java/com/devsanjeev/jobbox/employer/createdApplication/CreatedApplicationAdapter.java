package com.devsanjeev.jobbox.employer.createdApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.newFeeds.Application;

import java.util.ArrayList;

public class CreatedApplicationAdapter extends RecyclerView.Adapter<CreatedApplicationAdapter.ViewHolder> {
    public CreatedApplicationAdapter(CustomItemClickListener listener,Context context, ArrayList<CreatedApplicationResponse> list) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    private Context context;
    private ArrayList<CreatedApplicationResponse> list=new ArrayList<>();
    private CustomItemClickListener listener;
    @NonNull
    @Override
    public CreatedApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.created_applications_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedApplicationAdapter.ViewHolder holder, final int position) {
        holder.Experience.setText(list.get(position).getExperienceRequired());
        holder.Location.setText(list.get(position).getLocation());
        holder.Details.setText(list.get(position).getApplicationDetails());
        holder.Status.setText((list.get(position).getSkillsRequired()));
        holder.Title.setText(list.get(position).getTitle());
        holder.Industry.setText(list.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Title,Industry,Status,Details,Location,Experience;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.created_applications_title);
            Industry=itemView.findViewById(R.id.created_applications_industry);
            Status=itemView.findViewById(R.id.created_applications_skillset);
            Details=itemView.findViewById(R.id.created_applications_details);
            Location=itemView.findViewById(R.id.created_applications_location);
            Experience=itemView.findViewById(R.id.created_applications_experience);
        }
    }
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }
}
