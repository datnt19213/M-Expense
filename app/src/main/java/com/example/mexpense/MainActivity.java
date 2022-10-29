package com.example.mexpense;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mexpense.accessfragments.LoginFragment;
import com.example.mexpense.accessfragments.RegisterFragment;
import com.example.mexpense.mainfragments.AddExpenseFragment;
import com.example.mexpense.mainfragments.AddTripFragment;
import com.example.mexpense.mainfragments.ExpenseFragment;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.example.mexpense.mainfragments.TotalFragment;
import com.example.mexpense.mainfragments.TripFragment;
import com.example.mexpense.mainfragments.UpdateTripFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    TripFragment tripFragment;
    ExpenseFragment expenseFragment;
    AddTripFragment addTripFragment;
    UpdateTripFragment updateTripFragment;
    AddExpenseFragment addExpenseFragment;
    UpdateTripFragment updateExpenseFragment;
    TotalFragment totalFragment;
    SettingsFragment settingsFragment;

    FrameLayout frameMainContainer;

    Button loginButton;

    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager;


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
            switch (item.getItemId()) {
                case R.id.HomeMenuItem:
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frameMainContainer, TotalFragment.class, null).commit();
                    break;
                case R.id.TripMenuItem:
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frameMainContainer, TripFragment.class, null).commit();
                    break;
                case R.id.SettingsMenuItem:
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    ).replace(R.id.frameMainContainer, SettingsFragment.class, null).commit();
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}