package com.devsanjeev.jobbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devsanjeev.jobbox.employee.EmployeeActivity;
import com.devsanjeev.jobbox.employer.EmployerActivity;
import com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer;
import com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginEmployerFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private EditText Email,Password;
    private Button RegisterButton;
    private TextView DoesntHaveAccount;
    private GlobalClass globalClass;
    private APIInterface apiInterface;
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
        DoesntHaveAccount=view.findViewById(R.id.doesnt_have_account);
        RegisterButton=view.findViewById(R.id.et_login_employer_login_btn);
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
        return view;
    }

    private void doLogin() {
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        if(email.isEmpty()){
            Email.setError("Please Enter Email");
        }
        if(password.isEmpty()){
            Password.setError("Please Enter Password");
        }
        if(!email.isEmpty()&&!password.isEmpty()){
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
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getActivity(), "Error Occurred: "+response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Error "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> call, Throwable t) {

                }
            });

        }
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
