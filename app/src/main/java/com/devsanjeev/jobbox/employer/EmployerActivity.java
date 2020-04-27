package com.devsanjeev.jobbox.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.devsanjeev.jobbox.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmployerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        EmployerApplicationFragment fragment = new EmployerApplicationFragment();
        addFragment(fragment);
        bottomNavigation = findViewById(R.id.bottom_navigation_employer);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home_employer:
                        EmployerApplicationFragment fragment = new EmployerApplicationFragment();
                        addFragment(fragment);
                        return true;
                    case R.id.navigation_applications_employer:
                        EmployerNewApplicationFragment fragment1 = new EmployerNewApplicationFragment();
                        addFragment(fragment1);
                        return true;
                    case R.id.navigation_profile_employer:
                        EmployerProfileFragment fragment2 = new EmployerProfileFragment();
                        addFragment(fragment2);
                        return true;
                }
                return false;
            }
        });
    }


    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.container_employer, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1) {
               // write your code to switch between fragments.
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else {
            super.onBackPressed();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

//    @Override
//    public void onBackPressed() {

}