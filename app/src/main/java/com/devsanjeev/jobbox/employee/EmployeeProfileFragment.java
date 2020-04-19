package com.devsanjeev.jobbox.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.employeeProfile.EmployeeProfileResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeProfileFragment extends Fragment {
    private APIInterface apiInterface;
    private TextView Name, Mobile, DateOfBirth, GraduationCourse, PassoutYear, GraduationPercentage, GraduationCollege, TenthPercentage, TwelvePercentage, AadharNumber, Email;
    private GlobalClass globalClass;
    public EmployeeProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_profile, container, false);
        Name = view.findViewById(R.id.employee_profile_name);
        Mobile = view.findViewById(R.id.employee_profile_mobile);
        DateOfBirth = view.findViewById(R.id.employee_profile_birth);
        GraduationCourse = view.findViewById(R.id.employee_profile_graduation_course);
        PassoutYear = view.findViewById(R.id.employee_profile_passout_year);
        GraduationPercentage = view.findViewById(R.id.employee_profile_graduation_percentage);
        GraduationCollege = view.findViewById(R.id.employee_profile_college);
        TenthPercentage = view.findViewById(R.id.employee_profile_tenth_percentage);
        TwelvePercentage = view.findViewById(R.id.employee_profile_twelveth_percentage);
        AadharNumber = view.findViewById(R.id.employee_profile_aadhar_mumber);
        Email = view.findViewById(R.id.employee_profile_email);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass) getActivity().getApplicationContext();
        Call<EmployeeProfileResponse> call=apiInterface.getEmployeeProfile(globalClass.getCandidate().getId());
        call.enqueue(new Callback<EmployeeProfileResponse>() {
            @Override
            public void onResponse(Call<EmployeeProfileResponse> call, Response<EmployeeProfileResponse> response) {
                if(response.code()==200){
                    Name.setText(response.body().getFirstName());
                    Mobile.setText(String.valueOf(response.body().getMobile()));
                          //  DateOfBirth.setText(String.valueOf(response.body().getDateOfBirth()));
                            GraduationCourse.setText(response.body().getGraduationCourse());
                            PassoutYear.setText(String.valueOf(response.body().getPassoutYear()));
                            GraduationPercentage.setText(String.valueOf(response.body().getGraduationPercentage()));
                            GraduationCollege.setText(response.body().getCollege());
                            TenthPercentage.setText(String.valueOf(response.body().getPercentage10()));
                            TwelvePercentage.setText(String.valueOf(response.body().getPercentage12()));
                            AadharNumber.setText(String.valueOf(response.body().getAadharNumber()));
                            Email.setText(response.body().getEmail());

                }
            }

            @Override
            public void onFailure(Call<EmployeeProfileResponse> call, Throwable t) {

            }
        });
        return view;
    }

}
