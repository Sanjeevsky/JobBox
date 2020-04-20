package com.devsanjeev.jobbox.employer.createdApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.newFeeds.AppliedApplicationAdapter;
import com.devsanjeev.jobbox.employee.newFeeds.EmployeeID;

import java.util.ArrayList;
import java.util.List;

public class AppliedCandidateAdapter extends RecyclerView.Adapter<AppliedCandidateAdapter.ViewHolder> {

    public AppliedCandidateAdapter(CustomItemClickListener listener, List<EmployeeID> list, Context context) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }

    private CustomItemClickListener listener;
    private List<EmployeeID> list;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.applied_candidate_recycler_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.Name.setText(list.get(position).getFirstName());
        holder.Mobile.setText(String.valueOf(list.get(position).getMobile()));
        holder.Email.setText(list.get(position).getEmail());
        holder.Status.setText(list.get(position).getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });


    }
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name,Email,Mobile,Status;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.applied_candidate_name);
            Email=itemView.findViewById(R.id.applied_candidate_email);
            Mobile=itemView.findViewById(R.id.applied_candidate_mobile);
            Status=itemView.findViewById(R.id.applied_candidate_status);
            cardView=itemView.findViewById(R.id.applied_candidate_cardView);
        }
    }

}
