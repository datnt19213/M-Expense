package com.example.mexpense.mainfragments;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.Expenses;
import com.example.mexpense.model.Trips;

import java.util.List;

public class TotalFragment extends Fragment{
    View view;
    FragmentTransaction transaction;
    TextView totalTrip, totalExpense;


    public TotalFragment(){
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
        view = inflater.inflate(R.layout.fragment_total, container, false);

        totalExpenseVsTrip();

        return view;
    }

    private void totalExpenseVsTrip() {
        totalTrip = view.findViewById(R.id.totalTrip);
        totalExpense = view.findViewById(R.id.totalExpense);

        DBManager dbManager = new DBManager(getActivity());

        List<Trips> mtripsList = dbManager.getAllTrip();
        List<Expenses> mexpenseList = dbManager.getAllExpensesData();

        totalTrip.setText(String.valueOf(mtripsList.size()));
        totalExpense.setText(String.valueOf(mexpenseList.size()));
    }
}