package com.devsanjeev.jobbox.employer.employerProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.EmployerProfileFragment;
import com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEmployerProfileFragment extends Fragment {
    private EditText FirstName,LastName,Email,Industry,Mobile;
    private Button Update;
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    public EditEmployerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_employer, container, false);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        apiInterface= APIClient.getClient().create(APIInterface.class);
        FirstName=view.findViewById(R.id.update_employer_firstName);
        LastName=view.findViewById(R.id.update_employer_lastName);
        Mobile=view.findViewById(R.id.update_employer_mobile);
        Industry=view.findViewById(R.id.update_employer_industry);
        Email=view.findViewById(R.id.update_employer_email);
        Update=view.findViewById(R.id.update_employer_update_btn);

        Call<EmployerProfileResponse> call=apiInterface.getEmployerProfile(globalClass.getEmployer().getId());
        call.enqueue(new Callback<EmployerProfileResponse>() {
            @Override
            public void onResponse(Call<EmployerProfileResponse> call, Response<EmployerProfileResponse> response) {
                if(response.code()==200){
                    FirstName.setText(response.body().getFirstName());
                    LastName.setText(response.body().getLastName());
                    Mobile.setText(String.valueOf(response.body().getMobile()));
                    Email.setText(response.body().getEmail());
                    Industry.setText(response.body().getIndustry());


                }else {

                }
            }

            @Override
            public void onFailure(Call<EmployerProfileResponse> call, Throwable t) {

            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdate();
            }
        });
        return view;
    }

    private void doUpdate() {
        String firstName,lastName,email,industry,mobile;
        firstName=FirstName.getText().toString();
        lastName=LastName.getText().toString();
        email=Email.getText().toString();
        industry=Industry.getText().toString();
        mobile=Mobile.getText().toString();

        if(!firstName.isEmpty()&&!lastName.isEmpty()&&!email.isEmpty()&&!industry.isEmpty()&&!mobile.isEmpty()){
            RequestEmployer employer=new RequestEmployer();
            employer.setFirstName(firstName);
            employer.setLastName(lastName);
            employer.setEmail(email);
            employer.setMobile(mobile);
            employer.setIndustry(industry);

            Call<EmployerProfileResponse> call=apiInterface.updateEmployerData(globalClass.getEmployer().getId(),employer);
            call.enqueue(new Callback<EmployerProfileResponse>() {
                @Override
                public void onResponse(Call<EmployerProfileResponse> call, Response<EmployerProfileResponse> response) {
                    if(response.code()==200){
                        EmployerProfileFragment fragment=new EmployerProfileFragment();
                        addFragment(fragment);
                    }
                }

                @Override
                public void onFailure(Call<EmployerProfileResponse> call, Throwable t) {

                }
            });
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.container_employer, fragment);
        ft.commit();
    }

}
