package com.devsanjeev.jobbox.employer;

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
 * {@link EmployerApplicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmployerApplicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployerApplicationFragment extends Fragment {

    public EmployerApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employer_application, container, false);
    }

}
