package com.devsanjeev.jobbox.employee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.LoginActivity;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.employeeProfile.EditEmployeeProfileFragment;
import com.devsanjeev.jobbox.employee.employeeProfile.EmployeeProfileResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EmployeeProfileFragment extends Fragment {
    private APIInterface apiInterface;
    private TextView Name, Mobile, DateOfBirth, GraduationCourse, PassoutYear, GraduationPercentage, GraduationCollege, TenthPercentage, TwelvePercentage, AadharNumber, Email;
    private GlobalClass globalClass;
    private Button EditDetails,LogOut;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
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
        EditDetails=view.findViewById(R.id.employee_profile_edit_details);
        LogOut=view.findViewById(R.id.employee_profile_logout);
        frameLayout = view.findViewById(R.id.pBar_employee_profile);
        loadingImage = view.findViewById(R.id.loading_image_employee_profile);
        frameLayout.setVisibility(View.VISIBLE);
        loadingImage.setVisibility(View.VISIBLE);
        hideView(loadingImage);
        EditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEmployeeProfileFragment fragment=new EditEmployeeProfileFragment();
                addFragment(fragment);
            }
        });

        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass) getActivity().getApplicationContext();
        Call<EmployeeProfileResponse> call=apiInterface.getEmployeeProfile(globalClass.getCandidate().getId());
        call.enqueue(new Callback<EmployeeProfileResponse>() {
            @Override
            public void onResponse(Call<EmployeeProfileResponse> call, Response<EmployeeProfileResponse> response) {
                if(response.code()==200){
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
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

                }else {
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EmployeeProfileResponse> call, Throwable t) {
                frameLayout.setVisibility(View.GONE);
                loadingImage.setVisibility(View.GONE);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("jobBox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("auth_key_employee");
                editor.commit();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.container_employee, fragment);
        ft.commit();
    }
}
