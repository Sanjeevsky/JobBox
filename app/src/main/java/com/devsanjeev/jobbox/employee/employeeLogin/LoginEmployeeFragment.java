package com.devsanjeev.jobbox.employee.employeeLogin;

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
import com.devsanjeev.jobbox.employee.employeeRegister.RegisterEmployeeFragment;
import com.devsanjeev.jobbox.employee.EmployeeActivity;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginEmployeeFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private EditText Email,Password;
    private Button RegisterButton;
    private TextView ForgetPassword;
    private APIInterface apiInterface;
    GlobalClass globalVariable;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
    private SharedPreferences preferences;

    public LoginEmployeeFragment() {
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
        View view= inflater.inflate(R.layout.fragment_login_employee, container, false);
        floatingActionButton=view.findViewById(R.id.fab_login_employee);
        Email=view.findViewById(R.id.et_login_employee_fragment_email);
        Password=view.findViewById(R.id.et_login_employee_fragment_password);
        ForgetPassword=view.findViewById(R.id.doesnt_have_account);
        RegisterButton=view.findViewById(R.id.et_login_employee_login_btn);
        globalVariable = (GlobalClass)getActivity().getApplicationContext();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        frameLayout = view.findViewById(R.id.pBar_employee_login);
        loadingImage = view.findViewById(R.id.loading_image_employee_login);
        preferences = getActivity().getSharedPreferences("jobBox", MODE_PRIVATE);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterEmployeeFragment fragment=new RegisterEmployeeFragment();
                addFragment(fragment);
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeForgetPasswordFragment forgetPasswordEmployeeFragment=new EmployeeForgetPasswordFragment();
                addFragment(forgetPasswordEmployeeFragment);
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
            RequestEmployee employee=new RequestEmployee();
            employee.setEmail(email);
            employee.setPassword(password);
            Call<ResponseEmployee> call=apiInterface.loginEmployee(employee);

            call.enqueue(new Callback<ResponseEmployee>() {
                @Override
                public void onResponse(Call<ResponseEmployee> call, Response<ResponseEmployee> response) {
                    if(response.code()==200){
                        if(response.body().getSuccess()){
                            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                            globalVariable.setToken(response.body().getToken());
                            globalVariable.setCandidate(response.body().getCandidate());
                            preferences.edit().putString("auth_key_employee",response.body().getCandidate().getId() ).commit();
                            Intent intent=new Intent(getActivity(),EmployeeActivity.class);
                            startActivity(intent);
                            frameLayout.setVisibility(View.GONE);
                            loadingImage.setVisibility(View.GONE);
                            getActivity().finish();

                        }
                        else {
                            Toast.makeText(getActivity(), "Error Occurred: "+response.errorBody(), Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }


}
