package com.devsanjeev.jobbox.employer.createdApplication;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.newFeeds.EmployeeID;
import com.devsanjeev.jobbox.employer.EmployerApplicationFragment;
import com.devsanjeev.jobbox.employer.newApplication.ResponseApplication;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSingleFragment extends Fragment {
    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private TextView Title,Industry,Status,SkillSets,Details,Location,Experience;
    private CardView cardView;
    private Button ChangeStatus;
    private CreatedApplicationResponse list;
    private GlobalClass globalClass;
    private FrameLayout frameLayout;
    private ImageView loadingImage;

    public ViewSingleFragment() {
        // Required empty public constructor
    }

    public ViewSingleFragment(CreatedApplicationResponse list){
        this.list=list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_single, container, false);
        Title=view.findViewById(R.id.created_applications_title);
        Industry=view.findViewById(R.id.created_applications_industry);
        SkillSets=view.findViewById(R.id.created_applications_skillset);
        Status=view.findViewById(R.id.created_applications_status);
        Details=view.findViewById(R.id.created_applications_details);
        Location=view.findViewById(R.id.created_applications_location);
        Experience=view.findViewById(R.id.created_applications_experience);
        recyclerView=view.findViewById(R.id.applied_candidate_recycler);
        frameLayout = view.findViewById(R.id.pBar_employee_single_fragment);
        loadingImage = view.findViewById(R.id.loading_image_single_fragment);
        ChangeStatus=view.findViewById(R.id.created_applications_change_status_btn);
        cardView=view.findViewById(R.id.application_cardView);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        doSome();
        return view;
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }

    private void doSome() {
        Experience.setText(list.getExperienceRequired());
        Location.setText(list.getLocation());
        Details.setText(list.getApplicationDetails());
        Status.setText((list.getStatus()));
        SkillSets.setText(list.getSkillsRequired());
        Title.setText(list.getTitle());
        Industry.setText(list.getCompanyName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        AppliedCandidateAdapter applicationAdapter=new AppliedCandidateAdapter(new AppliedCandidateAdapter.CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                changeCandidateStatusDialog(position, list.getEmployeeID());
            }
        }, list.getEmployeeID(), getActivity());
        recyclerView.setAdapter(applicationAdapter);
        ChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  listener.onItemClick(view,position);
                if(list.getStatus().equals("Closed")){
                    Toast.makeText(getActivity(), "Application Already Closed", Toast.LENGTH_SHORT).show();
                }else {
                    closeApplicationAlert();
                }

            }
        });
    }


    public void changeCandidateStatusDialog(int position, List<EmployeeID> employeeID) {
        final EmployeeID employeeID1=employeeID.get(position);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.change_candidate_status,
                null, false);

        final RadioGroup genderRadioGroup = (RadioGroup) formElementsView
                .findViewById(R.id.genderRadioGroup);
        RadioButton radioButton=formElementsView.findViewById(R.id.appliedRadioButton);
        radioButton.setChecked(true);
        // the alert dialog
        new AlertDialog.Builder(getActivity()).setView(formElementsView)
                .setTitle("Change Application Status")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String toastString = "";
                        int selectedId = genderRadioGroup
                                .getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        RadioButton selectedRadioButton = (RadioButton) formElementsView
                                .findViewById(selectedId);

                        toastString=selectedRadioButton.getText().toString();
                        ChangeCandidateRequest request=new ChangeCandidateRequest();
                        request.setId(globalClass.getEmployer().getId());
                        request.setEmployeeId(employeeID1.getapplicantId());
                        request.setStatus(toastString);
                        frameLayout.setVisibility(View.VISIBLE);
                        loadingImage.setVisibility(View.VISIBLE);
                        hideView(loadingImage);
                        Call<ResponseApplication> call=apiInterface.updateCandidateStatus(request);
                        call.enqueue(new Callback<ResponseApplication>() {
                            @Override
                            public void onResponse(Call<ResponseApplication> call, Response<ResponseApplication> response) {
                                if(response.code()==200){
                                    frameLayout.setVisibility(View.GONE);
                                    loadingImage.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Successfully Changed Status", Toast.LENGTH_SHORT).show();
                                    EmployerApplicationFragment fragment=new EmployerApplicationFragment();
                                    addFragment(fragment);
                                }
                                else {
                                    frameLayout.setVisibility(View.GONE);
                                    loadingImage.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseApplication> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                frameLayout.setVisibility(View.GONE);
                                loadingImage.setVisibility(View.GONE);
                            }
                        });
                        dialog.cancel();
                    }

                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();

    }
    public void closeApplicationAlert() {
        final CreatedApplicationResponse applicationResponse=list;
        new AlertDialog.Builder(getActivity())
                .setTitle("Close Application")
                .setMessage("Want To Close Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        frameLayout.setVisibility(View.VISIBLE);
                        loadingImage.setVisibility(View.VISIBLE);
                        hideView(loadingImage);
                        ChangeApplicationStatus changeApplicationStatus=new ChangeApplicationStatus();
                        changeApplicationStatus.setId(applicationResponse.getId());
                        changeApplicationStatus.setStatus("Closed");
                        Call<ChangeApplicationResponse> call=apiInterface.changeApplicationStatus(changeApplicationStatus);
                        call.enqueue(new Callback<ChangeApplicationResponse>() {
                            @Override
                            public void onResponse(Call<ChangeApplicationResponse> call, Response<ChangeApplicationResponse> response) {
                                if(response.code()==200){
                                    frameLayout.setVisibility(View.GONE);
                                    loadingImage.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Application Status Changed", Toast.LENGTH_SHORT).show();
                                    EmployerApplicationFragment fragment=new EmployerApplicationFragment();
                                    addFragment(fragment);
                                }
                                else {
                                    frameLayout.setVisibility(View.GONE);
                                    loadingImage.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<ChangeApplicationResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Failed To Change Status", Toast.LENGTH_SHORT).show();
                                frameLayout.setVisibility(View.GONE);
                                loadingImage.setVisibility(View.GONE);
                            }
                        });
                        dialog.cancel();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
       // ft.addToBackStack(null);
// Replace the contents of the container with the new fragment
        ft.replace(R.id.container_employer, fragment);
        ft.commit();
    }
}
