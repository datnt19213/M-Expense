package com.example.mexpense.mainfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.Trips;

public class AddTripFragment extends Fragment {
    EditText tripName_input, tripDate_input, tripDescription_input;
    Button add_button;

    FragmentTransaction transaction;

    AlertDialog.Builder alertDialog;
    AlertDialog dialog;

    View mAddTrip;

    public AddTripFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mAddTrip = inflater.inflate(R.layout.fragment_add_trip, container, false);


//        DBManager myDB = new DBManager(getActivity());
//
//        tripName_input = mAddTrip.findViewById(R.id.tripName_input);
//        tripDate_input = mAddTrip.findViewById(R.id.tripDate_input);
//        tripDescription_input = mAddTrip.findViewById(R.id.tripDescription_input);
        add_button = mAddTrip.findViewById(R.id.addTripBtn);
//
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Trips trip = new Trips();
//                trip.setmTripName(tripName_input.getText().toString());
//                trip.setmTripDate(tripDate_input.getText().toString());
//                trip.setmTripDescription(tripDescription_input.getText().toString());
//                long result = myDB.add(trip);
//
//                if (result == -1) {
//                    AlertDialogFailed();
////                  Toast.makeText(AddTripFragment.this.getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//                } else {
//                    AlertDialogSuccess();
////                  Toast.makeText(AddTripFragment.this.getActivity(),  "Added Successfully!", Toast.LENGTH_SHORT).show();
//                }

                addTripFormTransaction();
            }
        });



        // Inflate the layout for this fragment
        return mAddTrip;
//                return inflater.inflate(R.layout.fragment_add_trip, container, false);
    }

    public void AlertDialogFailed(){
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Add Failed");
        alertDialog.setNegativeButton(R.string.cancel_delete_trip, (dialogInterface, i) -> dialogInterface.dismiss());
        dialog = alertDialog.create();
        alertDialog.show();
        //Dialog alert failed to add trip
    }

    public void AlertDialogSuccess(){
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Add Successfully");
        alertDialog.setNegativeButton(R.string.cancel_delete_trip, (dialogInterface, i) -> dialogInterface.dismiss());
        dialog = alertDialog.create();
        alertDialog.show();
        //Dialog alert success to add trip
    }

    public void addTripFormTransaction(){
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
}