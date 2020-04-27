package com.devsanjeev.jobbox.employer.employerLogin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.devsanjeev.jobbox.ConfirmPasswordRequest;
import com.devsanjeev.jobbox.ConfirmPasswordResponse;
import com.devsanjeev.jobbox.ForgetPasswordRequest;
import com.devsanjeev.jobbox.ForgetPasswordResponse;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerForgetPasswordFragment extends Fragment {
    private LinearLayout sendOtpLayout,enterPasswordLayout;
    private EditText OTPEditText,Password,ConfirmPassword,Email;
    private String email,password,confirmPassword,otp;
    private Button SendOtp,Submit;
    private APIInterface apiInterface;


    public EmployerForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employer_forget_password, container, false);
        sendOtpLayout=view.findViewById(R.id.employer_send_otp_layout);
        enterPasswordLayout=view.findViewById(R.id.employer_enter_otp_layout);
        OTPEditText=view.findViewById(R.id.employer_forget_otp);
        Password=view.findViewById(R.id.employer_forget_password);
        ConfirmPassword=view.findViewById(R.id.employer_forget_confirm_password);
        Email=view.findViewById(R.id.employer_forget_email);
        SendOtp=view.findViewById(R.id.employer_forget_send_otp_btn);
        Submit=view.findViewById(R.id.employer_forget_submit_btn);
        apiInterface= APIClient.getClient().create(APIInterface.class);

        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSendOTP();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPassword();
            }
        });
        return view;
    }

    private void submitPassword() {
        email=Email.getText().toString();
        if(email.isEmpty()){
            Email.setError("Please Provide Email");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Incorrect Email");
        }
        otp=OTPEditText.getText().toString();
        password=Password.getText().toString();
        confirmPassword=ConfirmPassword.getText().toString();
        if(!email.isEmpty()&&!otp.isEmpty()&&!password.isEmpty()&&!confirmPassword.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(password.equals(confirmPassword)){
                ConfirmPasswordRequest request=new ConfirmPasswordRequest();
                request.setEmail(email);
                request.setOtp(otp);
                request.setPassword(password);
                Call<ConfirmPasswordResponse> confirmPasswordResponseCall=apiInterface.updateEmployerPassword(request);
                confirmPasswordResponseCall.enqueue(new Callback<ConfirmPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmPasswordResponse> call, Response<ConfirmPasswordResponse> response) {
                        if(response.code()==200){
                            Toast.makeText(getActivity(), "Password Reset Successful", Toast.LENGTH_SHORT).show();
                            LoginEmployerFragment fragment=new LoginEmployerFragment();
                            addFragment(fragment);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConfirmPasswordResponse> call, Throwable t) {

                    }
                });

            }else {
                Toast.makeText(getActivity(), "Password Didn't Match", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getContext(), "Enter All Credentials", Toast.LENGTH_SHORT).show();
        }
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

    private void doSendOTP() {
        email=Email.getText().toString();
        if(!email.isEmpty()){
            final ForgetPasswordRequest request=new ForgetPasswordRequest();
            request.setEmail(email);
            Call<ForgetPasswordResponse> call=apiInterface.sentMailEmployer(request);
            call.enqueue(new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                    if(response.code()==200){
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        sendOtpLayout.setVisibility(View.GONE);
                        enterPasswordLayout.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(getContext(), "Error "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


