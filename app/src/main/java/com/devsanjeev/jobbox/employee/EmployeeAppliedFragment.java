package com.devsanjeev.jobbox.employee;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.newFeeds.Application;
import com.devsanjeev.jobbox.employee.newFeeds.AppliedApplicationAdapter;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAppliedFragment extends Fragment {
    private APIInterface apiInterface;
    GlobalClass globalClass;
    private RecyclerView recyclerView;
    public EmployeeAppliedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        View view= inflater.inflate(R.layout.fragment_employee_applied, container, false);
        recyclerView=view.findViewById(R.id.fragment_employee_applied_applications_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<ArrayList<Application>> call=apiInterface.appliedApplications(globalClass.getCandidate().getId());
        call.enqueue(new Callback<ArrayList<Application>>() {
            @Override
            public void onResponse(Call<ArrayList<Application>> call, Response<ArrayList<Application>> response) {
                if(response.code()==200){
                    AppliedApplicationAdapter adapter=new AppliedApplicationAdapter(getActivity(),response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Application>> call, Throwable t) {

            }
        });
        return view;
    }
    
}
