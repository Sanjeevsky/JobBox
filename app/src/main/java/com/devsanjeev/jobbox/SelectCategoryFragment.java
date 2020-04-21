package com.devsanjeev.jobbox;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devsanjeev.jobbox.employee.employeeLogin.LoginEmployeeFragment;
import com.devsanjeev.jobbox.employer.employerLogin.LoginEmployerFragment;

public class SelectCategoryFragment extends Fragment {

    private CardView Employer,Employee;

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
        View view=inflater.inflate(R.layout.fragment_select_category, container, false);
        Employer=view.findViewById(R.id.employer_cardView);
        Employee=view.findViewById(R.id.employee_cardView);
        Employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             LoginEmployerFragment fragment=new LoginEmployerFragment();
             addFragment(fragment);
            }
        });
        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            LoginEmployeeFragment fragment=new LoginEmployeeFragment();
            addFragment(fragment);
            }
        });
        return view;
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, fragment);
        ft.commit();
    }

}
