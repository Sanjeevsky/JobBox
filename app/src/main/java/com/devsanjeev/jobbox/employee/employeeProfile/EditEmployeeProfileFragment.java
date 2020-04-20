package com.devsanjeev.jobbox.employee.employeeProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.EmployeeProfileFragment;
import com.devsanjeev.jobbox.employer.EmployerProfileFragment;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEmployeeProfileFragment extends Fragment {

    private EditText FirstName, LastName, Email, Mobile, GraduationCourse, GraduationPercentage, College, TenthPercentage, TwelvthPercentage, AadharNumber, PassoutYear;
    private Button UpdateButton;
    private APIInterface apiInterface;
    private GlobalClass globalClass;

    public EditEmployeeProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_employee_profile, container, false);
        FirstName = view.findViewById(R.id.update_employee_firstName);
        LastName = view.findViewById(R.id.update_employee_lastName);
        Email = view.findViewById(R.id.update_employee_email);
        Mobile = view.findViewById(R.id.update_employee_mobile);
        GraduationCourse = view.findViewById(R.id.update_employee_graduation_course);
        GraduationPercentage = view.findViewById(R.id.update_employee_graduation_percentage);
        College = view.findViewById(R.id.update_employee_college);
        TenthPercentage = view.findViewById(R.id.update_employee_10th_percentage);
        TwelvthPercentage = view.findViewById(R.id.update_employee12th_percentage);
        AadharNumber = view.findViewById(R.id.update_employee_aadhar_number);
        PassoutYear = view.findViewById(R.id.update_employee_passout);
        UpdateButton = view.findViewById(R.id.update_employee_update_btn);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<EmployeeProfileResponse> call = apiInterface.getEmployeeProfile(globalClass.getCandidate().getId());
        call.enqueue(new Callback<EmployeeProfileResponse>() {
            @Override
            public void onResponse(Call<EmployeeProfileResponse> call, Response<EmployeeProfileResponse> response) {
                if (response.code() == 200) {
                    FirstName.setText(response.body().getFirstName());
                    LastName.setText(response.body().getLastName());
                    Email.setText(response.body().getEmail());
                    Mobile.setText(String.valueOf(response.body().getMobile()));
                    GraduationCourse.setText(response.body().getGraduationCourse());
                    GraduationPercentage.setText(String.valueOf(response.body().getGraduationPercentage()));
                    College.setText(response.body().getCollege());
                    TenthPercentage.setText(String.valueOf(response.body().getPercentage10()));
                    TwelvthPercentage.setText(String.valueOf(response.body().getPercentage12()));
                    AadharNumber.setText(String.valueOf(response.body().getAadharNumber()));
                    PassoutYear.setText(String.valueOf(response.body().getPassoutYear()));
                }
            }

            @Override
            public void onFailure(Call<EmployeeProfileResponse> call, Throwable t) {

            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdate();
            }
        });
        return view;
    }

    private void doUpdate() {
        String firstName, lastName, email, mobile, graduationCourse, graduationPercentage, college, tenthPercentage, twelvthPercentage, aadharNumber, passoutYear;
        firstName =FirstName.getText().toString();
        lastName=LastName.getText().toString();
                email=Email.getText().toString();
                mobile=Mobile.getText().toString();
                graduationCourse=GraduationCourse.getText().toString();
                graduationPercentage=GraduationPercentage.getText().toString();
                college= College.getText().toString();
                tenthPercentage=TenthPercentage.getText().toString();
                twelvthPercentage=TwelvthPercentage.getText().toString();
                aadharNumber=AadharNumber.getText().toString();
                passoutYear=PassoutYear.getText().toString();
    if(!firstName.isEmpty()&&!mobile.isEmpty()&&!email.isEmpty()&&!graduationCourse.isEmpty()&&!college.isEmpty()&&!tenthPercentage.isEmpty()&&!twelvthPercentage.isEmpty()&&!passoutYear.isEmpty()&&!graduationPercentage.isEmpty()&&!lastName.isEmpty()){
        UpdateCandidateRequest request=new UpdateCandidateRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setMobile(Long.parseLong(mobile));
        request.setGraduationCourse(graduationCourse);
        request.setGraduationPercentage(Float.parseFloat(graduationPercentage));
        request.setCollege(college);
        request.setPercentage10(Float.parseFloat(tenthPercentage));
        request.setPercentage12(Float.parseFloat(twelvthPercentage));
        request.setAadharNumber(Long.parseLong(aadharNumber));
        request.setPassoutYear(Integer.parseInt(passoutYear));

        Call<EmployeeProfileResponse> call=apiInterface.updateEmployeeDetails(globalClass.getCandidate().getId(),request);
        call.enqueue(new Callback<EmployeeProfileResponse>() {
            @Override
            public void onResponse(Call<EmployeeProfileResponse> call, Response<EmployeeProfileResponse> response) {
                if(response.code()==200){
                    Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    EmployeeProfileFragment fragment=new EmployeeProfileFragment();
                    addFragment(fragment);
                }
            }

            @Override
            public void onFailure(Call<EmployeeProfileResponse> call, Throwable t) {

            }
        });
    }else {
        Toast.makeText(getActivity(), "Fill All Credentials", Toast.LENGTH_SHORT).show();
    }
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.container_employee, fragment);
        ft.commit();
    }
}
