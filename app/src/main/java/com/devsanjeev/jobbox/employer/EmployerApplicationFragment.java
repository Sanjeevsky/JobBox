package com.devsanjeev.jobbox.employer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.devsanjeev.jobbox.GlobalClass;
import com.devsanjeev.jobbox.R;
import com.devsanjeev.jobbox.employer.createdApplication.ChangeApplicationResponse;
import com.devsanjeev.jobbox.employer.createdApplication.ChangeApplicationStatus;
import com.devsanjeev.jobbox.employer.createdApplication.CreatedApplicationAdapter;
import com.devsanjeev.jobbox.employer.createdApplication.CreatedApplicationResponse;
import com.devsanjeev.jobbox.employer.createdApplication.ViewSingleFragment;
import com.devsanjeev.jobbox.retrofit.APIClient;
import com.devsanjeev.jobbox.retrofit.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerApplicationFragment extends Fragment {
    private APIInterface apiInterface;
    private GlobalClass globalClass;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private ImageView loadingImage;
    private ArrayList<CreatedApplicationResponse> list;
    public EmployerApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employer_application, container, false);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
        apiInterface= APIClient.getClient().create(APIInterface.class);
        recyclerView=view.findViewById(R.id.application_created_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        frameLayout = view.findViewById(R.id.pBar_employer_applications);
        loadingImage = view.findViewById(R.id.loading_image_employer_applications);
        frameLayout.setVisibility(View.VISIBLE);
        loadingImage.setVisibility(View.VISIBLE);
        hideView(loadingImage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        Call<ArrayList<CreatedApplicationResponse>> call=apiInterface.createdApplications(globalClass.getEmployer().getId());
        call.enqueue(new Callback<ArrayList<CreatedApplicationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CreatedApplicationResponse>> call, final Response<ArrayList<CreatedApplicationResponse>> response) {
                if(response.code()==200){
                    list=response.body();
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                    CreatedApplicationAdapter applicationAdapter=new CreatedApplicationAdapter(new CreatedApplicationAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                       // closeApplicationAlert(list,position);
                            ViewSingleFragment fragment=new ViewSingleFragment(list.get(position));
                            addFragment(fragment);
                        }
                    },getActivity(),list);
                    recyclerView.setAdapter(applicationAdapter);
                }
                else {
                    frameLayout.setVisibility(View.GONE);
                    loadingImage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CreatedApplicationResponse>> call, Throwable t) {
                frameLayout.setVisibility(View.GONE);
                loadingImage.setVisibility(View.GONE);
            }
        });

        return view;
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
       // ft.addToBackStack(null);
// Replace the contents of the container with the new fragment
        ft.addToBackStack(null);
        ft.replace(R.id.container_employer, fragment);
        ft.commit();
    }

    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_out);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }

}

