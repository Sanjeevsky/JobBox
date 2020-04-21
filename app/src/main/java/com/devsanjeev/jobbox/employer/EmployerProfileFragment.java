package com.devsanjeev.jobbox.employer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.LoginActivity;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.employerLogin.Employer;
import com.devsanjeev.jobbox.employer.employerProfile.EditEmployerProfileFragment;
import com.devsanjeev.jobbox.employer.employerProfile.EmployerProfileResponse;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EmployerProfileFragment extends Fragment {

    private APIInterface apiInterface;
    private TextView Name,Email,Industry,Mobile;
    private Button EditDetails,LogOut;
    private GlobalClass globalClass;
    private FrameLayout frameLayout;
    private ImageView loadingImage;

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
        EditDetails=view.findViewById(R.id.employer_profile_edit_details);
        LogOut=view.findViewById(R.id.employer_profile_logout);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        frameLayout = view.findViewById(R.id.pBar_employer_profile);
        loadingImage = view.findViewById(R.id.loading_image_employer_profile);
        frameLayout.setVisibility(View.VISIBLE);
        loadingImage.setVisibility(View.VISIBLE);
        hideView(loadingImage);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("jobBox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("auth_key_employer");
                editor.commit();

                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        Call<EmployerProfileResponse> call=apiInterface.getEmployerProfile(globalClass.getEmployer().getId());
        call.enqueue(new Callback<EmployerProfileResponse>() {
            @Override
            public void onResponse(Call<EmployerProfileResponse> call, Response<EmployerProfileResponse> response) {
                if(response.code()==200){
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                    Name.setText(response.body().getFirstName());
                    Email.setText(response.body().getEmail());
                    Industry.setText(response.body().getIndustry());
                    Mobile.setText(String.valueOf(response.body().getMobile()));
                }else {
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EmployerProfileResponse> call, Throwable t) {
                frameLayout.setVisibility(View.GONE);
                loadingImage.setVisibility(View.GONE);
            }
        });
        EditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEmployerProfileFragment fragment=new EditEmployerProfileFragment();
                addFragment(fragment);
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
        ft.replace(R.id.container_employer, fragment);
        ft.commit();
    }
    }

