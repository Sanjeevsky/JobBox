package com.devsanjeev.jobbox.employer.createdApplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.newFeeds.Application;
import com.devsanjeev.jobbox.employee.newFeeds.AppliedApplicationAdapter;
import com.devsanjeev.jobbox.employee.newFeeds.EmployeeID;
import com.devsanjeev.jobbox.employer.newApplication.ResponseApplication;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.Status.setText((list.get(position).getStatus()));
        holder.SkillSets.setText(list.get(position).getSkillsRequired());
        holder.Title.setText(list.get(position).getTitle());
        holder.Industry.setText(list.get(position).getCompanyName());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setHasFixedSize(true);
        AppliedCandidateAdapter2 applicationAdapter=new AppliedCandidateAdapter2(list.get(position).getEmployeeID(), context);
        holder.recyclerView.setAdapter(applicationAdapter);
        holder.ChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Title,Industry,Status,SkillSets,Details,Location,Experience;
        private RecyclerView recyclerView;
        private CardView cardView;
        private Button ChangeStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.created_applications_title);
            Industry=itemView.findViewById(R.id.created_applications_industry);
            SkillSets=itemView.findViewById(R.id.created_applications_skillset);
            Status=itemView.findViewById(R.id.created_applications_status);
            Details=itemView.findViewById(R.id.created_applications_details);
            Location=itemView.findViewById(R.id.created_applications_location);
            Experience=itemView.findViewById(R.id.created_applications_experience);
            recyclerView=itemView.findViewById(R.id.applied_candidate_recycler);
            ChangeStatus=itemView.findViewById(R.id.created_applications_change_status_btn);
            cardView=itemView.findViewById(R.id.application_cardView);
        }
    }
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    public void changeCandidateStatusDialog(int position,List<EmployeeID> employeeID) {
        final EmployeeID employeeID1=employeeID.get(position);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)context.getApplicationContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.change_candidate_status,
                null, false);

        final RadioGroup genderRadioGroup = (RadioGroup) formElementsView
                .findViewById(R.id.genderRadioGroup);
        RadioButton radioButton=formElementsView.findViewById(R.id.appliedRadioButton);
        radioButton.setChecked(true);
        // the alert dialog
        new AlertDialog.Builder(context).setView(formElementsView)
                .setTitle("Change Application Status")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String toastString = "";
                        int selectedId = genderRadioGroup
                                .getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        RadioButton selectedRadioButton = (RadioButton) formElementsView
                                .findViewById(selectedId);

                        toastString=selectedRadioButton.getText().toString();
                        ChangeCandidateRequest request=new ChangeCandidateRequest();
                        request.setId(globalClass.getEmployer().getId());
                        request.setEmployeeId(employeeID1.getapplicantId());
                        request.setStatus(toastString);
                        Call<ResponseApplication> call=apiInterface.updateCandidateStatus(request);
                        call.enqueue(new Callback<ResponseApplication>() {
                            @Override
                            public void onResponse(Call<ResponseApplication> call, Response<ResponseApplication> response) {
                                if(response.code()==200){
                                    Toast.makeText(context, "Successfully Changed Status", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseApplication> call, Throwable t) {
                                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.cancel();
                    }

                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();

    }

}
