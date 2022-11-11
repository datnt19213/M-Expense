package com.example.mexpense.mainfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.databinding.FragmentTripBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class TripFragment extends Fragment {

    View view;

    RecyclerView recyclerViewTrip;
    TripAdapter tripAdapter;
    FragmentTripBinding fragmentTripBinding;

    FragmentTransaction transaction;
    FrameLayout frameMainContainer;
    LinearLayoutManager linearLayoutManager;

    Button updateTripBtn, deleteTripBtn;

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
        }
        if (id == R.id.optionMenuDeleteAllTripBtn){
            deleteAllTripClick();
        }


        return super.onOptionsItemSelected(item);
    }

    //Create view for Trip fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTripBinding = FragmentTripBinding.inflate(inflater, container, false);

        view = fragmentTripBinding.getRoot();

        recyclerViewTrip = fragmentTripBinding.recycleViewTrip;
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerViewTrip.setLayoutManager(linearLayoutManager);

        DBManager dbManager = new DBManager(getActivity());

        tripAdapter = new TripAdapter(dbManager.getAllTrip());
        recyclerViewTrip.setAdapter(tripAdapter);


//        updateTripFormTransaction();
//        deleteTripClick();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

//    //update trip form transaction
//    public void updateTripFormTransaction(){
//        updateTripBtn = view.findViewById(R.id.editTripBtn);
//        updateTripBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                assert getFragmentManager() != null;
//                transaction = getFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(
//                        R.anim.slide_in,  // enter
//                        R.anim.fade_out,  // exit
//                        R.anim.fade_in,   // popEnter
//                        R.anim.slide_out  // popExit
//                );
//                transaction.replace(R.id.frameMainContainer, UpdateTripFragment.class, null);
//                transaction.commit();
//            }
//        });
//    }
//
    //delete a trip from the Trip list in this fragment
    public void deleteTripClick(){
        deleteTripBtn = view.findViewById(R.id.deleteTripBtn);
        deleteTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
                alertDialog.setMessage("Are you sure you want to delete all trips?");
                alertDialog.setPositiveButton(R.string.delete_trip, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBManager db = new  DBManager(getActivity());

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
                                //Delete a trip in database here
                            }
                        })
                        .setNegativeButton(R.string.cancel_delete_trip, (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.create();
                alertDialog.show();
                //Dialog alert delete all trip
            }
        });
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