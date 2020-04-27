package com.devsanjeev.jobbox.employer.employerRegister;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
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

import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.employerLogin.LoginEmployerFragment;
import com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmployerFragment extends Fragment {
    private EditText FirstName,LastName,Email,Mobile,Password,ConfirmPassword,Industry;
    private Button Register;
    private TextView Login;
    private APIInterface apiInterface;
    private FrameLayout frameLayout;
    private ImageView loadingImage;

    public RegisterEmployerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_register, container, false);
        FirstName=view.findViewById(R.id.fragment_register_employer_first_name);
        LastName=view.findViewById(R.id.fragment_register_employer_last_name);
        Email=view.findViewById(R.id.fragment_register_employer_email);
        Mobile=view.findViewById(R.id.fragment_register_employer_mobile);
        Password=view.findViewById(R.id.fragment_register_employer_password);
        ConfirmPassword=view.findViewById(R.id.fragment_register_employer_confirm_password);
        Industry=view.findViewById(R.id.fragment_register_employer_industry);
        Register=view.findViewById(R.id.fragment_register_employer_register_btn);
        Login=view.findViewById(R.id.fragment_register_employer_already_have_account_txt);
        frameLayout = view.findViewById(R.id.pBar_employer_register);
        loadingImage = view.findViewById(R.id.loading_image_employer_register);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEmployerFragment fragment=new LoginEmployerFragment();
                addFragment(fragment);
            }
        });
        return view;
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

    private void doRegister() {
        String firstName,lastName,email,mobile,password,confirmPassword,industry;
        firstName=FirstName.getText().toString();
        lastName=LastName.getText().toString();
        email=Email.getText().toString();
        mobile=Mobile.getText().toString();
        password=Password.getText().toString();
        confirmPassword= ConfirmPassword.getText().toString();
        industry=Industry.getText().toString();
        if(firstName.isEmpty()){
         FirstName.setError("First Name Is Mandatory Field");
        }
        if(email.isEmpty()){
         Email.setError("Email Is Mandatory Field");
        }
        if(mobile.length()!=10){
            Mobile.setError("Incorrect Mobile Number");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Incorrect Email Field");
        }
        if(password.isEmpty()){
         Password.setError("Password Is Mandatory Field");
        }
        if(confirmPassword.isEmpty()){
         ConfirmPassword.setError("Confirm Password Is Mandatory");
        }
        if(industry.isEmpty()){
         Industry.setError("Industry Is Mandatory Field");
        }

        if(!firstName.isEmpty()&&!email.isEmpty()&&!password.isEmpty()&&!confirmPassword.isEmpty()&&!industry.isEmpty()&&Patterns.EMAIL_ADDRESS.matcher(email).matches()&&mobile.length()==10) {
            if (password.equals(confirmPassword)) {
                frameLayout.setVisibility(View.VISIBLE);
                loadingImage.setVisibility(View.VISIBLE);
                hideView(loadingImage);
                com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer employer = new RequestEmployer();
                employer.setEmail(email);
                employer.setFirstName(firstName);
                employer.setLastName(lastName);
                employer.setIndustry(industry);
                employer.setMobile(mobile);
                employer.setPassword(password);
                Call<com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer> call=apiInterface.registerEmployer(employer);
                call.enqueue(new Callback<com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer>() {
                    @Override
                    public void onResponse(Call<com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer> call, Response<com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer> response) {
                        if(response.code()==200){
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                            if(response.body().getSuccess()){
                                Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Failed To Register", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Error Occurred: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                    }
                });

            }else {
                Toast.makeText(getActivity(), "Password MisMatch", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Enter All Required Fields", Toast.LENGTH_SHORT).show();
        }
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }
}
