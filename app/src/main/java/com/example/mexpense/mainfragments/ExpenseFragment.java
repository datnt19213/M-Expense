package com.example.mexpense.mainfragments;

import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class ExpenseFragment extends Fragment {

    View view;
    FragmentTransaction transaction;
    ExpenseAdapter expenseAdapter;
    Button expenseBackToTrip;
    MainActivity mMainActivity;
    TripFragment tripFragment;
    SearchView searchExpense;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recycleViewExpense;

    int id, tripId, mtripId;

    private ISendData mISendData;

    public interface ISendData{
        void sendData(int tripId);
    }

    public ExpenseFragment(){
        //Empty Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    // create menu option to Trip fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_expense_option_menu, menu); // create menu option to Trip fragment
        super.onCreateOptionsMenu(menu, inflater);
    }

    //check and active the action by id item click on the option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        id = item.getItemId();
        if (id == R.id.optionMenuAddExpenseBtn){
            //check null tripId transfer to expense overview to show list of expense
            if (tripId != 0) {
                mISendData.sendData(tripId);
            }else {
                mISendData.sendData(mtripId);
            }
        }
        if (id == R.id.optionMenuExportExpenseBtn){
//            function();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_expense, container, false);

        mMainActivity = (MainActivity) getActivity();

        recycleViewExpense = view.findViewById(R.id.recycleViewExpense);
        linearLayoutManager = new LinearLayoutManager(mMainActivity);
        recycleViewExpense.setLayoutManager(linearLayoutManager);

        DBManager dbManager = new DBManager(getActivity());



        //Back to trip button click
        expenseBackToTrip = view.findViewById(R.id.expenseBackToTrip);
        expenseBackToTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tripFormTransaction();
            }
        });


        //get data expense from trip id
        Bundle bundleTripReceiveToExpense = getArguments();
        if(bundleTripReceiveToExpense != null){
            Trips trip = (Trips) bundleTripReceiveToExpense.get("object_trip");
            if (trip != null) {
                tripId = trip.getmId();
                expenseAdapter = new ExpenseAdapter(dbManager.getAllExpense(tripId));
                Toast.makeText(mMainActivity, String.valueOf(trip.getmId()), Toast.LENGTH_SHORT).show();
            }
        } else {
            expenseAdapter = new ExpenseAdapter(dbManager.getAllExpense(mtripId));
            Toast.makeText(mMainActivity, String.valueOf(mtripId), Toast.LENGTH_SHORT).show();
        }

        searchExpense();

        recycleViewExpense.setAdapter(expenseAdapter);
        return view;
    }

    //search expense
    private void searchExpense() {
        searchExpense = view.findViewById(R.id.expenseSearchBox);
        searchExpense.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                expenseAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                expenseAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    //lifecycle methods of this expense
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mISendData = (ISendData) getActivity();
    }

    //transaction to trip fragment
    public void tripFormTransaction(){
        tripFragment = new TripFragment();
        assert getFragmentManager() != null;
        transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
        transaction.replace(R.id.frameMainContainer, tripFragment);
        transaction.commit();
    }

    public void receiveExpenseTripId(int tripId) {
        mtripId = tripId;
    }
}