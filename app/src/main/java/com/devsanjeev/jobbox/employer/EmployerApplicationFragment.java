package com.devsanjeev.jobbox.employer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.createdApplication.ChangeApplicationResponse;
import com.devsanjeev.jobbox.employer.createdApplication.ChangeApplicationStatus;
import com.devsanjeev.jobbox.employer.createdApplication.CreatedApplicationAdapter;
import com.devsanjeev.jobbox.employer.createdApplication.CreatedApplicationResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerApplicationFragment extends Fragment {
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    private RecyclerView recyclerView;
    public EmployerApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employer_application, container, false);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        apiInterface= APIClient.getClient().create(APIInterface.class);
        recyclerView=view.findViewById(R.id.application_created_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        Call<ArrayList<CreatedApplicationResponse>> call=apiInterface.createdApplications(globalClass.getEmployer().getId());
        call.enqueue(new Callback<ArrayList<CreatedApplicationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CreatedApplicationResponse>> call, final Response<ArrayList<CreatedApplicationResponse>> response) {
                if(response.code()==200){
                    CreatedApplicationAdapter applicationAdapter=new CreatedApplicationAdapter(new CreatedApplicationAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                        closeApplicationAlert(response.body(),position);
                        }
                    },getActivity(),response.body());
                    recyclerView.setAdapter(applicationAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CreatedApplicationResponse>> call, Throwable t) {

            }
        });

        return view;
    }

    public void closeApplicationAlert(final ArrayList<CreatedApplicationResponse> body, final int position) {

        new AlertDialog.Builder(getActivity())
                .setTitle("Close Application")
                .setMessage("Want To Close Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeApplicationStatus changeApplicationStatus=new ChangeApplicationStatus();
                        changeApplicationStatus.setId(body.get(position).getId());
                        changeApplicationStatus.setStatus("Closed");
                        Call<ChangeApplicationResponse> call=apiInterface.changeApplicationStatus(changeApplicationStatus);
                        call.enqueue(new Callback<ChangeApplicationResponse>() {
                            @Override
                            public void onResponse(Call<ChangeApplicationResponse> call, Response<ChangeApplicationResponse> response) {
                                if(response.code()==200){
                                    Toast.makeText(getActivity(), "Application Status Changed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ChangeApplicationResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Failed To Change Status", Toast.LENGTH_SHORT).show();
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

