package com.example.mexpense;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.mainfragments.AddExpenseFragment;
import com.example.mexpense.mainfragments.ExpenseFragment;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.example.mexpense.mainfragments.TotalFragment;
import com.example.mexpense.mainfragments.TripFragment;
import com.example.mexpense.mainfragments.UpdateTripFragment;
import com.example.mexpense.model.Trips;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, ExpenseFragment.ISendData, AddExpenseFragment.IReloadData {

    FrameLayout frameMainContainer;
    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager;

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    //bottom menu navigation choice transaction
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

    //get id from trip item to expense list in expense fragment
    public void goExpenseFragment(Trips trip){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ExpenseFragment expenseFragment = new ExpenseFragment();
        AddExpenseFragment addExpenseFragment = new AddExpenseFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_trip", trip);

        expenseFragment.setArguments(bundle);
        addExpenseFragment.setArguments(bundle);

        transaction.replace(R.id.frameMainContainer, expenseFragment);
        transaction.commit();
    }

    //get trip data to update trip
    public void updateTripItem(Trips trip){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        UpdateTripFragment updateTripFragment = new UpdateTripFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_update_trip", trip);

        updateTripFragment.setArguments(bundle);

        transaction.replace(R.id.frameMainContainer, updateTripFragment);
        transaction.addToBackStack(UpdateTripFragment.TAG); //Save position of trip recycle view
        transaction.commit();
    }

    //get id from expense added to show list expense in expense fragment
    @Override
    public void reloadData(int tripId) {
        ExpenseFragment expenseFragment = new ExpenseFragment();
        expenseFragment.receiveExpenseTripId(tripId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameMainContainer, expenseFragment);
        transaction.commit();
    }

    //get id from expense fragment to send to add expense
    @Override
    public void sendData(int tripId) {
        AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
        addExpenseFragment.onReceiveData(tripId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameMainContainer, addExpenseFragment);
        transaction.commit();
    }
}