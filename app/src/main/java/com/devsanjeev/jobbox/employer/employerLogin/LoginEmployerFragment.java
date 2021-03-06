package com.devsanjeev.jobbox.employer.employerLogin;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.employerRegister.RegisterEmployerFragment;
import com.devsanjeev.jobbox.employer.EmployerActivity;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginEmployerFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private EditText Email,Password;
    private Button RegisterButton;
    private TextView ForgetPassword;
    private GlobalClass globalClass;
    private APIInterface apiInterface;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
    private SharedPreferences preferences;
    public LoginEmployerFragment() {
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
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        floatingActionButton=view.findViewById(R.id.fab_login_employer);
        Email=view.findViewById(R.id.et_login_employer_fragment_email);
        Password=view.findViewById(R.id.et_login_employer_fragment_password);
        ForgetPassword=view.findViewById(R.id.doesnt_have_account_employer);
        RegisterButton=view.findViewById(R.id.et_login_employer_login_btn);
        frameLayout = view.findViewById(R.id.pBar_employer_login);
        preferences = getActivity().getSharedPreferences("jobBox", MODE_PRIVATE);
        loadingImage = view.findViewById(R.id.loading_image_employer_login);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterEmployerFragment fragment=new RegisterEmployerFragment();
                addFragment(fragment);
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployerForgetPasswordFragment forgetPasswordFragment=new EmployerForgetPasswordFragment();
                addFragment(forgetPasswordFragment);
            }
        });
        return view;
    }

    private void doLogin() {
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        if(email.isEmpty()){
            Email.setError("Please Enter Email");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Incorrect Email");
        }

        if(password.isEmpty()){
            Password.setError("Please Enter Password");
        }
        if(!email.isEmpty()&&!password.isEmpty()&&Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            frameLayout.setVisibility(View.VISIBLE);
            loadingImage.setVisibility(View.VISIBLE);
            hideView(loadingImage);
            com.devsanjeev.jobbox.employer.employerLogin.RequestEmployer employer= new com.devsanjeev.jobbox.employer.employerLogin.RequestEmployer();
            employer.setEmail(email);
            employer.setPassword(password);
            Call<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> call=apiInterface.loginEmployer(employer);
            call.enqueue(new Callback<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer>() {
                @Override
                public void onResponse(Call<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> call, Response<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> response) {
                    if(response.code()==200){
                        if(response.body().getSuccess()){
                            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getActivity(), EmployerActivity.class);
                            globalClass.setToken(response.body().getToken());
                            globalClass.setEmployer(response.body().getEmployer());
                            preferences.edit().putString("auth_key_employer",response.body().getEmployer().getId() ).commit();
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getActivity(), "Error Occurred: "+response.body(), Toast.LENGTH_SHORT).show();
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Error "+response.errorBody(), Toast.LENGTH_SHORT).show();
                        frameLayout.setVisibility(View.GONE);
                        loadingImage.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> call, Throwable t) {
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            });
        }
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

}
