package com.example.mexpense.mainfragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mexpense.R;
import com.example.mexpense.model.ExpenseTypes;

import java.util.List;

public class ExpenseTypesAdapter extends ArrayAdapter<ExpenseTypes> {

    public ExpenseTypesAdapter(@NonNull Context context, int resource, @NonNull List<ExpenseTypes> objects) {
        super(context, resource, objects);
    }

    //set text from list type selected to the dropdown list box
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_type_selected, parent, false);
        TextView expenseTypeSelected = (TextView) convertView.findViewById(R.id.selectedDropDown);

        ExpenseTypes expenseType = this.getItem(position);
        if (expenseType != null) {
            expenseTypeSelected.setText(expenseType.getmExpenseType());
        }

        return convertView;
    }

    //show list data from click to dropdown list box
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_type, parent, false);
        TextView expenseTypeItem = (TextView) convertView.findViewById(R.id.expenseTypeItem);

        ExpenseTypes expenseType = this.getItem(position);
        if (expenseType != null) {
            expenseTypeItem.setText(expenseType.getmExpenseType());
        }

        return convertView;
    }
}
