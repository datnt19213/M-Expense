package com.example.mexpense.mainfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.MainActivity;
import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.Trips;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class TripFragment extends Fragment {

    View view;

    RecyclerView recyclerViewTrip;
    TripAdapter tripAdapter;

    FragmentTransaction transaction;
    LinearLayoutManager linearLayoutManager;

    MainActivity mMainActivity;

    androidx.appcompat.widget.SearchView searchTrip;

    MaterialAlertDialogBuilder alertDialog;

    int id;


    public TripFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Set create only at Trip fragment
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    // create menu option to Trip fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_trip_option_menu, menu); // create menu option to Trip fragment
        super.onCreateOptionsMenu(menu, inflater);
    }

    //check and active the action by id item click on the option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        id = item.getItemId();

        if (id == R.id.optionMenuAddTripBtn){
            addTripFormTransaction();
            //go to add trip form
        }
        if (id == R.id.optionMenuDeleteAllTripBtn){
            deleteAllTripClick();
            //delete all trip
        }
        return super.onOptionsItemSelected(item);
    }

    //Create view for Trip fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trip, container, false);

        mMainActivity = (MainActivity) getActivity();

        recyclerViewTrip = view.findViewById(R.id.recycleViewTrip);
        linearLayoutManager = new LinearLayoutManager(mMainActivity);
        recyclerViewTrip.setLayoutManager(linearLayoutManager);

        updateTripFormVsExpenseTransaction();
        searchTrip();

        recyclerViewTrip.setAdapter(tripAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //show trip search data filtered from trip adapter
    private void searchTrip() {
        searchTrip = view.findViewById(R.id.tripSearchBox);
        searchTrip.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tripAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tripAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void updateTripFormVsExpenseTransaction() {
        DBManager dbManager = new DBManager(getActivity());
        tripAdapter = new TripAdapter(dbManager.getAllTrip(), new TripAdapter.ITripClickItem(){

            //calling to go to expenseFragment
            @Override
            public void onClickTripItem(Trips trip) {
                mMainActivity.goExpenseFragment(trip);
                //go to expenseFragment
            }

            //calling to go to update trip fragment
            @Override
            public void onClickTripEditItem(Trips trip) {
                mMainActivity.updateTripItem(trip);
                //go to update trip fragment
            }
        });
    }

    //add trip form transaction
    public void addTripFormTransaction(){
        assert getFragmentManager() != null;
        transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
        transaction.replace(R.id.frameMainContainer, AddTripFragment.class, null);
        transaction.commit();

    }

    //delete all trip click listener
    public void deleteAllTripClick(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Are you sure you want to delete all trips?");
        alertDialog.setPositiveButton(R.string.delete_trip, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBManager db = new  DBManager(getActivity());
                db.deleteAllTrip();

                assert getFragmentManager() != null;
                transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
                transaction.replace(R.id.frameMainContainer, TripFragment.class, null);
                transaction.commit();
            }
        })
        .setNegativeButton(R.string.cancel_delete_trip, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert delete all trip

    }

}