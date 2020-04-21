package com.devsanjeev.jobbox.employer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.newApplication.RequestApplication;
import com.devsanjeev.jobbox.employer.newApplication.ResponseApplication;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerNewApplicationFragment extends Fragment {

    private EditText Title, CompanyName, Skill, Experience, LocationText, Details;
    private Button CreateApplication;
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
    public EmployerNewApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employer_new_application, container, false);

        Title = view.findViewById(R.id.new_application_title);
        CompanyName = view.findViewById(R.id.new_application_company_name);
        Skill = view.findViewById(R.id.new_application_skills_required);
        Experience = view.findViewById(R.id.new_application_experience);
        LocationText = view.findViewById(R.id.new_application_location);
        Details = view.findViewById(R.id.new_application_details);
        CreateApplication=view.findViewById(R.id.new_application_create_application_btn);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        frameLayout = view.findViewById(R.id.pBar_employer_new_application);
        loadingImage = view.findViewById(R.id.loading_image_employer_new_application);
        CreateApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
        return view;
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }


    private void doRegister() {
        String title, companyName, skill, experience, locationText, details;
        title=Title.getText().toString();
        companyName=CompanyName.getText().toString();
        skill=Skill.getText().toString();
        experience=Experience.getText().toString();
        locationText=LocationText.getText().toString();
        details=Details.getText().toString();

        if(!title.isEmpty()&&!companyName.isEmpty()&&!skill.isEmpty()&&!experience.isEmpty()&&!locationText.isEmpty()&&!details.isEmpty()){
            frameLayout.setVisibility(View.VISIBLE);
            loadingImage.setVisibility(View.VISIBLE);
            hideView(loadingImage);
            RequestApplication application=new RequestApplication();
            application.setEmployerId(globalClass.getEmployer().getId());
            application.setStatus("Fresh");
            application.setCompanyName(companyName);
            application.setTitle(title);
            application.setApplicationDetails(details);
            application.setExperienceRequired(experience);
            application.setSkillsRequired(skill);
            application.setLocation(locationText);

            Call<ResponseApplication> call=apiInterface.createApplication(application);
            call.enqueue(new Callback<ResponseApplication>() {
                @Override
                public void onResponse(Call<ResponseApplication> call, Response<ResponseApplication> response) {
                    if(response.code()==200){
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Application Created", Toast.LENGTH_LONG).show();
                    }else {
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseApplication> call, Throwable t) {
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            });
        }else {
            Toast.makeText(getActivity(), "Enter All Credentials", Toast.LENGTH_LONG).show();
        }
    }
}
