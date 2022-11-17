package com.example.mexpense.mainfragments;

import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.ExpenseTypes;
import com.example.mexpense.model.Expenses;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;


public class AddExpenseFragment extends Fragment {

    View view;
    Spinner expenseTypeInput;
    EditText expenseAmountInput, expenseDateInput, expenseDescriptionInput;
    Button addExpenseBtn, addExpenseBackBtn;

    ExpenseTypesAdapter expenseTypesAdapter;
    ExpenseFragment expenseFragment;
    TripFragment tripFragment;

    FragmentTransaction transaction;
    MaterialAlertDialogBuilder alertDialog;

    public String selectedExpenseTypeItem;
    int tripId;

    private IReloadData mIReloadData;

    public interface IReloadData{
        void reloadData(int tripId);
    }

    public AddExpenseFragment(){
        //Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        expenseTypeInput = view.findViewById(R.id.expenseType_input);
        expenseTypesAdapter = new ExpenseTypesAdapter(getContext(), R.layout.item_expense_type_selected, getListExpenseTypes());
        expenseTypeInput.setAdapter(expenseTypesAdapter);

        tripFragment = new TripFragment();
        expenseTypeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedExpenseTypeItem = expenseTypesAdapter.getItem(i).getmExpenseType();
                //get list item selected and set that data row choice to widget
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //empty
            }
        });

        expenseAmountInput = view.findViewById(R.id.expenseAmount_input);
        expenseDateInput = view.findViewById(R.id.expenseDate_input);
        expenseDescriptionInput = view.findViewById(R.id.expenseDescription_input);
        addExpenseBtn = view.findViewById(R.id.addExpenseBtn);

        addNewExpenseClick();
        backToExpenseClick();

        return view;
    }

    //list add type of expense
    public List<ExpenseTypes> getListExpenseTypes(){
        List<ExpenseTypes> expenseTypesList = new ArrayList<>();
        expenseTypesList.add(new ExpenseTypes("Food"));
        expenseTypesList.add(new ExpenseTypes("Drink"));
        expenseTypesList.add(new ExpenseTypes("Moving expenses"));
        expenseTypesList.add(new ExpenseTypes("Accommodation"));
        expenseTypesList.add(new ExpenseTypes("Entertainment"));
        expenseTypesList.add(new ExpenseTypes("Others"));
        return expenseTypesList;
    }

    //lifecycle methods of this expense add
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIReloadData = (IReloadData) getActivity();
        //create interface method
    }

    //Click add new expense button
    public void addNewExpenseClick(){
        DBManager dbManager = new DBManager(getActivity());
        addExpenseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (selectedExpenseTypeItem.isEmpty()
                || expenseAmountInput.getText().toString().isEmpty()
                || expenseDateInput.getText().toString().isEmpty()
                || expenseDescriptionInput.getText().toString().isEmpty()){
                    AlertDialogEmpty();
                }else {
                    if (tripId != 0) {
                        Expenses expenses = new Expenses();

                        expenses.setmExpenseType(selectedExpenseTypeItem);
                        expenses.setmExAmount(Integer.parseInt(expenseAmountInput.getText().toString()));
                        expenses.setmExDate(expenseDateInput.getText().toString());
                        expenses.setmExComment(expenseDescriptionInput.getText().toString());
                        expenses.setmExTripId(tripId);

                        long result = dbManager.addExpense(expenses);

                        if (result == -1) {
                            AlertDialogFailed();
                        } else {
                            AlertDialogSuccess();
                        }
                    }else {
                        AlertDialogFailed();
//                        Toast.makeText(getContext(),String.valueOf(tripId),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Click to back the expense overview
    public void backToExpenseClick(){
        addExpenseBackBtn = view.findViewById(R.id.addExpenseBackBtn);
        expenseFragment = new ExpenseFragment();
        addExpenseBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //send trip id to main activity to transferring back to expense to show list expense by that id
                mIReloadData.reloadData(tripId);
            }
        });
    }

    //get data transferred from expense fragment icon add click
    public void onReceiveData(int rTripid){
        tripId = rTripid;
    }

    public void AlertDialogFailed(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Add Failed");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert failed to add trip
    }

    public void AlertDialogSuccess(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Add Successfully");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            //send trip id to main activity to transferring back to expense to show list expense by that id
            mIReloadData.reloadData(tripId);
        });
        alertDialog.create();
        alertDialog.show();
        //Dialog alert success to add trip
    }

    public void AlertDialogEmpty(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Fields must not be empty");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.create();
        alertDialog.show();
        //Dialog alert empty fields data
    }
}