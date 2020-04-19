package com.devsanjeev.jobbox.employer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.employerLogin.Employer;
import com.devsanjeev.jobbox.employer.employerProfile.EmployerProfileResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerProfileFragment extends Fragment {

    private APIInterface apiInterface;
    private TextView Name,Email,Industry,Mobile;
    private GlobalClass globalClass;

    public EmployerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employer_profile, container, false);
        Name=view.findViewById(R.id.employer_profile_name);
        Email =view.findViewById(R.id.employer_profile_email);
        Industry=view.findViewById(R.id.employer_profile_industry);
        Mobile=view.findViewById(R.id.employer_profile_mobile);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        Call<EmployerProfileResponse> call=apiInterface.getEmployerProfile(globalClass.getEmployer().getId());
        call.enqueue(new Callback<EmployerProfileResponse>() {
            @Override
            public void onResponse(Call<EmployerProfileResponse> call, Response<EmployerProfileResponse> response) {
                if(response.code()==200){
                    Name.setText(response.body().getFirstName());
                    Email.setText(response.body().getEmail());
                    Industry.setText(response.body().getIndustry());
                    Mobile.setText(response.body().getMobile());
                }
            }

            @Override
            public void onFailure(Call<EmployerProfileResponse> call, Throwable t) {

            }
        });
        return view;
    }

}
