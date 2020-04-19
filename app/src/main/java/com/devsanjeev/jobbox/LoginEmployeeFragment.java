package com.devsanjeev.jobbox;

import android.app.Application;
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
import com.devsanjeev.jobbox.employee.employeeLogin.RequestEmployee;
import com.devsanjeev.jobbox.employee.employeeLogin.ResponseEmployee;
import com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer;
import com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginEmployeeFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private EditText Email,Password;
    private Button RegisterButton;
    private TextView DoesntHaveAccount;
    private APIInterface apiInterface;
    GlobalClass globalVariable;

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
        DoesntHaveAccount=view.findViewById(R.id.doesnt_have_account);
        RegisterButton=view.findViewById(R.id.et_login_employee_login_btn);
        globalVariable = (GlobalClass)getActivity().getApplicationContext();
        apiInterface = APIClient.getClient().create(APIInterface.class);
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
                            Intent intent=new Intent(getActivity(),EmployeeActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getActivity(), "Error Occurred: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Error "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
