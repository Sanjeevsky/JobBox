package com.devsanjeev.jobbox.employee;

import android.os.Bundle;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employee.applyApplication.NewApplicationRequest;
import com.devsanjeev.jobbox.employee.newFeeds.Application;
import com.devsanjeev.jobbox.employee.newFeeds.NewFeedAdapter;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeNewFeedsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Application> list=new ArrayList<>();
    private APIInterface apiInterface;
    GlobalClass globalClass;
    private FrameLayout frameLayout;
    private ImageView loadingImage;

    public EmployeeNewFeedsFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_new_feeds, container, false);
        recyclerView=view.findViewById(R.id.fragment_employee_newFeeds_recycler);
        apiInterface= APIClient.getClient().create(APIInterface.class);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        frameLayout = view.findViewById(R.id.pBar_employee_new_feed);
        loadingImage = view.findViewById(R.id.loading_image_employee_new_feed);
        frameLayout.setVisibility(View.VISIBLE);
        loadingImage.setVisibility(View.VISIBLE);
        hideView(loadingImage);
        Call<ArrayList<Application>> call=apiInterface.allApplications();
        call.enqueue(new Callback<ArrayList<Application>>() {
            @Override
            public void onResponse(final Call<ArrayList<Application>> call, Response<ArrayList<Application>> response) {
                if(response.code()==200){
                    list=response.body();
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                    NewFeedAdapter adapter= new NewFeedAdapter(new NewFeedAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            frameLayout.setVisibility(View.VISIBLE);
                            loadingImage.setVisibility(View.VISIBLE);
                            hideView(loadingImage);
                            NewApplicationRequest request=new NewApplicationRequest();
                            request.setFirstName(globalClass.getCandidate().getName());
                            request.setEmail(globalClass.getCandidate().getEmail());
                            request.setEmployeeId(globalClass.getCandidate().getId());
                            request.setMobile(globalClass.getCandidate().getMobile());
                            Call<Application> call1=apiInterface.applyApplication(list.get(position).getId(),request);
                            call1.enqueue(new Callback<Application>() {
                                @Override
                                public void onResponse(Call<Application> call, Response<Application> response) {
                                    if(response.code()==200){
                                        Toast.makeText(getContext(), "Applied Successfully", Toast.LENGTH_SHORT).show();
                                        frameLayout.setVisibility(View.GONE);
                                        loadingImage.setVisibility(View.GONE);
                                        EmployeeAppliedFragment fragment=new EmployeeAppliedFragment();
                                        addFragment(fragment);
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                        frameLayout.setVisibility(View.GONE);
                                        loadingImage.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Application> call, Throwable t) {
                                    frameLayout.setVisibility(View.GONE);
                                    loadingImage.setVisibility(View.GONE);
                                }
                            });
                        }
                    },getActivity(),list );
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Application>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                frameLayout.setVisibility(View.GONE);
                loadingImage.setVisibility(View.GONE);
            }
        });
        return view;
    }
    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.container_employee, fragment);
        ft.commit();
    }
}
