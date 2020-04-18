package com.devsanjeev.jobbox.employee;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devsanjeev.jobbox.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmployeeNewFeedsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmployeeNewFeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeNewFeedsFragment extends Fragment {


    public EmployeeNewFeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_new_feeds, container, false);
    }

}
