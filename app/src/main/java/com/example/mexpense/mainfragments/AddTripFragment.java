package com.example.mexpense.mainfragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class AddTripFragment extends Fragment {
    EditText tripName_input, tripDestination_input, tripDate_input, tripDescription_input;
    Button btnAddTrip, btnAddTripBack;
    RadioGroup radioGroup;
    RadioButton radioButtonRisk, radioButtonNoneRisk;

    FragmentTransaction transaction;
    View mAddTrip;
    MaterialAlertDialogBuilder alertDialog;
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    int selected, day, month, year;
    String nameSelected;

    public AddTripFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mAddTrip = inflater.inflate(R.layout.fragment_add_trip, container, false);

        DBManager myDB = new DBManager(getActivity());

        tripName_input = mAddTrip.findViewById(R.id.tripName_input);
        tripDestination_input = mAddTrip.findViewById(R.id.tripDestination_input);
        tripDate_input = mAddTrip.findViewById(R.id.tripDate_input);
        tripDescription_input = mAddTrip.findViewById(R.id.tripDescription_input);
        radioButtonRisk = mAddTrip.findViewById(R.id.radioButtonRisk);
        radioButtonNoneRisk = mAddTrip.findViewById(R.id.radioButtonNoneRisk);
        radioGroup = mAddTrip.findViewById(R.id.radioGroupRiskCheck);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButtonNoneRisk.isChecked()) {
                    selected = 0;
                    nameSelected = "None Risk";
                }
                else {
                    selected = 1;
                    nameSelected = "Risk";
                }
            }
        });
        setVsGetCalendar();
        addTripClick();

        btnAddTripBack = mAddTrip.findViewById(R.id.addTripBackBtn);
        btnAddTripBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tripTransaction();
            }
        });

        // Inflate the layout for this fragment
        return mAddTrip;
    }

    public void setVsGetCalendar(){
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        tripDate_input = mAddTrip.findViewById(R.id.tripDate_input);
        tripDate_input.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        tripDate_input.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    //add new trip click
    public void addTripClick() {
        btnAddTrip = mAddTrip.findViewById(R.id.addTripBtn);
        btnAddTrip.setOnClickListener(v -> {

            if (tripName_input.getText().toString().isEmpty()
                    || tripDestination_input.getText().toString().isEmpty()
                    || tripDate_input.getText().toString().isEmpty()
                    || tripDescription_input.getText().toString().isEmpty()){
                AlertDialogEmpty();
            }else {
                String reviewData =
                    "Trip name: " + tripName_input.getText().toString() + "\n" +
                    "Trip destination: " + tripDestination_input.getText().toString() + "\n" +
                    "Trip date: " + tripDate_input.getText().toString() + "\n" +
                    "Trip risk: " + nameSelected + "\n" +
                    "Trip description: " + tripDescription_input.getText().toString() + "\n" +
                    "\nDo you want to add new trip?";
                AlertDialogReview(reviewData);
            }
        });
    }

    public void AlertDialogFailed(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle(R.string.failed);
        alertDialog.setMessage("Add Failed");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert failed to add trip
    }

    public void AlertDialogReview(String reviewData){
        DBManager myDB = new DBManager(getActivity());
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle("Review Notification");
        alertDialog.setMessage(reviewData);
        alertDialog.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).setPositiveButton(R.string.ok, (dialogInterfaceSecond, interf) -> {
            dialogInterfaceSecond.dismiss();
            Trips trip = new Trips();
            trip.setmTripName(tripName_input.getText().toString());
            trip.setmTripDestination(tripDestination_input.getText().toString());
            trip.setmTripDate(tripDate_input.getText().toString());
            trip.setmTripRiskAssessment(String.valueOf(selected));
            trip.setmTripDescription(tripDescription_input.getText().toString());
            long result = myDB.addTrip(trip);

            if (result == -1) {
                AlertDialogFailed();
            } else {
                AlertDialogSuccess();
                tripTransaction();
            }
        });
        alertDialog.create();
        alertDialog.show();
        //Dialog alert success to add trip
    }

    public void AlertDialogSuccess(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setTitle(R.string.success);
        alertDialog.setMessage("Added successful");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
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

    public void tripTransaction(){
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