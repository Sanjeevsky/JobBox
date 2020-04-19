package com.devsanjeev.jobbox.employee.newFeeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsanjeev.jobbox.R;

import java.security.spec.ECField;
import java.util.ArrayList;

public class NewFeedAdapter extends RecyclerView.Adapter<NewFeedAdapter.ViewHolder> {

    public NewFeedAdapter(CustomItemClickListener listener, Context context, ArrayList<Application> list) {
        this.listener = listener;
        this.context = context;
        this.list = list;
    }

    CustomItemClickListener listener;
    private Context context;
    private ArrayList<Application> list=new ArrayList<>();
    @NonNull
    @Override
    public NewFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.new_feeds_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewFeedAdapter.ViewHolder holder, final int position) {
        holder.Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
        holder.Experience.setText(list.get(position).getExperienceRequired());
        holder.Location.setText(list.get(position).getLocation());
        holder.Details.setText(list.get(position).getApplicationDetails());
        holder.SkillSets.setText(list.get(position).getSkillsRequired());
        holder.Title.setText(list.get(position).getTitle());
        holder.Industry.setText(list.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Title,Industry,SkillSets,Details,Location,Experience;
        private Button Apply;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.new_feed_title);
            Industry=itemView.findViewById(R.id.new_feed_industry);
            SkillSets=itemView.findViewById(R.id.new_feed_skillset);
            Details=itemView.findViewById(R.id.new_feed_details);
            Location=itemView.findViewById(R.id.new_feed_location);
            Apply=itemView.findViewById(R.id.new_feed_apply_btn);
            Experience=itemView.findViewById(R.id.new_feed_experience);
        }
    }
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }
}
