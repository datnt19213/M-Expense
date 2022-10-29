package com.example.mexpense.mainfragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private View mAddTrip;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAddTrip = inflater.inflate(R.layout.fragment_add_trip, container, false);
        DBManager myDB = new DBManager(getActivity());

        tripName_input = mAddTrip.findViewById(R.id.tripName_input);
        tripDate_input = mAddTrip.findViewById(R.id.tripDate_input);
        tripDescription_input = mAddTrip.findViewById(R.id.tripDescription_input);
        add_button = mAddTrip.findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trips trip = new Trips();
                trip.setmTripName(tripName_input.getText().toString().trim());
                trip.setmTripDate(tripDate_input.getText().toString().trim());
                trip.getmTripDescription();
                long result = myDB.add(trip);

                if (result == -1) {
                    Toast.makeText(AddTripFragment.this.getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTripFragment.this.getActivity(),  "Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_trip, container, false);
    }
}