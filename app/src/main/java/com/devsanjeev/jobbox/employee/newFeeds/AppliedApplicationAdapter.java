package com.devsanjeev.jobbox.employee.newFeeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsanjeev.jobbox.R;

import java.util.ArrayList;

public class AppliedApplicationAdapter extends RecyclerView.Adapter<AppliedApplicationAdapter.ViewHolder> {
    public AppliedApplicationAdapter(Context context, ArrayList<Application> list) {
        this.context = context;
        this.list = list;
    }

    private Context context;
    private ArrayList<Application> list=new ArrayList<>();
    @NonNull
    @Override
    public AppliedApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.applied_application_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedApplicationAdapter.ViewHolder holder, final int position) {
        holder.Experience.setText(list.get(position).getExperienceRequired());
        holder.Location.setText(list.get(position).getLocation());
        holder.Details.setText(list.get(position).getApplicationDetails());
        holder.Status.setText((list.get(position).getEmployeeID().get(0).getStatus()).toUpperCase());
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
            Title=itemView.findViewById(R.id.applied_application_title);
            Industry=itemView.findViewById(R.id.applied_application_industry);
            Status=itemView.findViewById(R.id.applied_application_status);
            Details=itemView.findViewById(R.id.applied_application_details);
            Location=itemView.findViewById(R.id.applied_application_location);
            Experience=itemView.findViewById(R.id.applied_application_experience);
        }
    }
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }
}
