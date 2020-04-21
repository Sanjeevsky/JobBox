package com.devsanjeev.jobbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devsanjeev.jobbox.employee.EmployeeActivity;
import com.devsanjeev.jobbox.employee.employeeLogin.Candidate;
import com.devsanjeev.jobbox.employee.employeeLogin.LoginEmployeeFragment;
import com.devsanjeev.jobbox.employee.employeeProfile.EmployeeProfileResponse;
import com.devsanjeev.jobbox.employer.EmployerActivity;
import com.devsanjeev.jobbox.employer.employerLogin.Employer;
import com.devsanjeev.jobbox.employer.employerLogin.LoginEmployerFragment;
import com.devsanjeev.jobbox.employer.employerProfile.EmployerProfileResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SelectCategoryFragment extends Fragment {

    private CardView Employer, Employee;
    private SharedPreferences preferences;
    private String token;
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);
        Employer = view.findViewById(R.id.employer_cardView);
        Employee = view.findViewById(R.id.employee_cardView);
        preferences = getActivity().getSharedPreferences("jobBox", MODE_PRIVATE);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        frameLayout = view.findViewById(R.id.pBar_select);
        loadingImage = view.findViewById(R.id.loading_image_select);
        Employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                loadingImage.setVisibility(View.VISIBLE);
                hideView(loadingImage);
                token = preferences.getString("auth_key_employer", "");
                Call<EmployerProfileResponse> call=apiInterface.getEmployerProfile(token);
                call.enqueue(new Callback<EmployerProfileResponse>() {
                    @Override
                    public void onResponse(Call<EmployerProfileResponse> call, Response<EmployerProfileResponse> response) {
                        if(response.code()==200){
                            com.devsanjeev.jobbox.employer.employerLogin.Employer employer=new Employer();
                            employer.setId(response.body().getId());
                            employer.setEmail(response.body().getEmail());
                            globalClass.setEmployer(employer);
                            Intent intent=new Intent(getActivity(), EmployerActivity.class);
                            startActivity(intent);
//                            frameLayout.setVisibility(View.GONE);
//                            loadingImage.setVisibility(View.GONE);
                            getActivity().finish();
                        }
                        else {
                            LoginEmployerFragment fragment = new LoginEmployerFragment();
                            addFragment(fragment);
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<EmployerProfileResponse> call, Throwable t) {
                        LoginEmployerFragment fragment = new LoginEmployerFragment();
                        addFragment(fragment);
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                    }
                });

            }
        });
        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.VISIBLE);
                loadingImage.setVisibility(View.VISIBLE);
                hideView(loadingImage);
                token = preferences.getString("auth_key_employee", "");
                Call<EmployeeProfileResponse> call=apiInterface.getEmployeeProfile(token);
                call.enqueue(new Callback<EmployeeProfileResponse>() {
                    @Override
                    public void onResponse(Call<EmployeeProfileResponse> call, Response<EmployeeProfileResponse> response) {
                        if(response.code()==200){
                            Candidate candidate=new Candidate();
                            candidate.setId(response.body().getId());
                            candidate.setEmail(response.body().getEmail());
                            candidate.setMobile(response.body().getMobile());
                            candidate.setName(response.body().getFirstName());
                            globalClass.setCandidate(candidate);

                            Intent intent=new Intent(getActivity(), EmployeeActivity.class);
                            startActivity(intent);
//                            frameLayout.setVisibility(View.GONE);
//                            loadingImage.setVisibility(View.GONE);
                            getActivity().finish();
                        }
                        else {
                            LoginEmployeeFragment fragment = new LoginEmployeeFragment();
                            addFragment(fragment);
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeProfileResponse> call, Throwable t) {
                        LoginEmployeeFragment fragment = new LoginEmployeeFragment();
                        addFragment(fragment);
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                    }
                });

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
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

}
