package com.example.mexpense.mainfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.Trips;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class UpdateTripFragment extends Fragment {

    View view;
    Button updateTripBtn, updateTripBackBtn;
    EditText tripNameInput, tripDestiInput, tripDateInput, tripDescInput;
    RadioGroup radioGroup;
    RadioButton radioButtonRisk, radioButtonNoneRisk;

    FragmentTransaction transaction;
    MaterialAlertDialogBuilder alertDialog;
    AlertDialog dialog;

    int selected;

    public static final String TAG = UpdateTripFragment.class.getName(); //save status of trip fragment

    public UpdateTripFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_trip, container, false);

        tripNameInput = view.findViewById(R.id.updateTripName_input);
        tripDateInput = view.findViewById(R.id.updateTripDate_input);
        tripDestiInput = view.findViewById(R.id.updateTripDestination_input);
        tripDescInput = view.findViewById(R.id.updateTripDescription_input);
        radioButtonRisk = view.findViewById(R.id.updateRadioButtonRisk);
        radioButtonNoneRisk = view.findViewById(R.id.updateRadioButtonNoneRisk);
        radioGroup = view.findViewById(R.id.updateRadioGroupRiskCheck);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonNoneRisk.isChecked()) {
                    selected = 0;
                    //set none risk status into database to trip
                }
                else {
                    selected = 1;
                    //set risk into status database to trip
                }
            }
        });

        updateTripBtn = view.findViewById(R.id.updateTripBtn);
        updateTripBackBtn = view.findViewById(R.id.updateTripBackBtn);

        //get data bundle by key in main activity to this fragment
        Bundle bundleTripReceive = getArguments();
        if(bundleTripReceive != null){
            Trips trip = (Trips) bundleTripReceive.get("object_update_trip");
            if (trip != null){
                tripNameInput.setText(trip.getmTripName());
                tripDestiInput.setText(trip.getmTripDestination());
                tripDateInput.setText(trip.getmTripDate());
                if(trip.getmTripRiskAssessment() != null){
                    if (trip.getmTripRiskAssessment().equals("0")){
                        radioButtonNoneRisk.setChecked(true);
                    }
                    if (trip.getmTripRiskAssessment().equals("1")){
                        radioButtonRisk.setChecked(true);
                    }
                }
                tripDescInput.setText(trip.getmTripDescription());
            }
        }

        //update trip back to trip fragment button click
        updateTripBackBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });

        //update trip button click to go to update trip form
        updateTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateTripClick();
            }
        });


        return view;
    }

    //update trip
    public void updateTripClick(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Are you sure you want to update trip?");
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (tripNameInput.getText().toString().isEmpty()
                || tripDestiInput.getText().toString().isEmpty()
                || tripDateInput.getText().toString().isEmpty()
                || tripDescInput.getText().toString().isEmpty()){
                    AlertDialogEmpty();
                }else{
                    DBManager dbManager = new DBManager(getActivity());
                    Bundle bundleTripReceive = getArguments();
                    if (bundleTripReceive != null){
                        Trips trip = (Trips) bundleTripReceive.get("object_update_trip");
                        if (trip != null) {
                            trip.setmTripName(tripNameInput.getText().toString());
                            trip.setmTripDestination(tripDestiInput.getText().toString());
                            trip.setmTripDate(tripDateInput.getText().toString());
                            if (selected == 0) {
                                trip.setmTripRiskAssessment(String.valueOf(selected));
                            } else {
                                trip.setmTripRiskAssessment(String.valueOf(selected));
                            }
                            trip.setmTripDescription(tripDestiInput.getText().toString());

                            long result = dbManager.updateTrip(trip);

                            if (result == -1) {
                                AlertDialogFailed();
                            } else {
                                AlertDialogSuccess();
                            }
                            updateTripFormTransaction();
                        }
                    }
                }
            }
        })
        .setNegativeButton(R.string.cancel_delete_trip, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
    }

    public void AlertDialogFailed(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle(R.string.failed);
        alertDialog.setMessage("Update Failed");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert failed to add trip
    }

    public void AlertDialogSuccess(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle(R.string.success);
        alertDialog.setMessage("Update Successfully");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert success to add trip
    }

    public void AlertDialogEmpty(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle(R.string.empty_fields);
        alertDialog.setMessage("Fields must not be empty");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert empty fields data
    }

    public void updateTripFormTransaction(){
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