package com.example.mexpense;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.accessfragments.LoginFragment;
import com.example.mexpense.accessfragments.RegisterFragment;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.example.mexpense.mainfragments.TotalFragment;
import com.example.mexpense.mainfragments.TripAdapter;
import com.example.mexpense.mainfragments.TripFragment;
import com.example.mexpense.model.Trips;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    TripAdapter tripAdapter;

    FrameLayout frameMainContainer;
    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerViewTrip;
    FragmentManager fragmentManager;

    LayoutAnimationController layoutAnimationController;



    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();

        frameMainContainer = findViewById(R.id.frameMainContainer);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameMainContainer, TotalFragment.class, null).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            onNavigationItemSelected(item);
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.HomeMenuItem:
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frameMainContainer, TotalFragment.class, null).commit();
                return true;
            case R.id.TripMenuItem:
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frameMainContainer, TripFragment.class, null).commit();
//                setAnimation(R.anim.layout_animation_down_to_up);
                return true;
            case R.id.SettingsMenuItem:
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frameMainContainer, SettingsFragment.class, null).commit();
                return true;
        }
        return false;
    }

//    private void setAnimation (int animResource){
//        recyclerViewTrip = findViewById(R.id.recycleViewTrip);
//        tripAdapter = new TripAdapter(getListOfTrips());
//        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, animResource);
//        recyclerViewTrip.setLayoutAnimation(layoutAnimationController);
//
//        recyclerViewTrip.setAdapter(tripAdapter);
//    }
//
//    private List<Trips> getListOfTrips(){
//        List<Trips> list = new ArrayList<>();
//        list.add(new Trips("1", "2", "3", true, "5"));
//        list.add(new Trips("1", "2", "3", true, "5"));
//        list.add(new Trips("1", "2", "3", true, "5"));
//        return list;
//    }
}