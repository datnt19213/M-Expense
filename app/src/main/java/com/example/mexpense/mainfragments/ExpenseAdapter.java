package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.model.Expenses;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> implements Filterable {

    private List<Expenses> mListExpenses; //new list to show search data result
    private final List<Expenses> mListExpensesOld; //current list

    MaterialAlertDialogBuilder alertDialog;

    public ExpenseAdapter(List<Expenses> mListExpenses) {
        this.mListExpenses = mListExpenses;
        this.mListExpensesOld = mListExpenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expenses expenses = mListExpenses.get(position);

        if (expenses == null){
            return;
        }
        holder.expenseType.setText(expenses.getmExpenseType());
        holder.expenseDate.setText(expenses.getmExDate());
        holder.expenseAmount.setText(String.valueOf(expenses.getmExAmount()));

        holder.expenseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detailExpense = "Expense Type: " + expenses.getmExpenseType() + "\n" +
                        "Expense Amount: " + expenses.getmExAmount() + "$" + "\n" +
                        "Expense Date: " + expenses.getmExDate() + "\n" +
                        "Expense Description: " + expenses.getmExComment() + "\n";
                alertDialog = new MaterialAlertDialogBuilder(view.getContext(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
                alertDialog.setTitle(R.string.view_details);
                alertDialog.setMessage(detailExpense);
                alertDialog.setNegativeButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListExpenses != null) {
            return mListExpenses.size();
        }
        return 0;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expenseType, expenseDate, expenseAmount;
        MenuItem addExpenseItem;
        RelativeLayout expenseItem;

        public ExpenseViewHolder(@NonNull View itemview) {
            super(itemview);
            addExpenseItem = itemview.findViewById(R.id.optionMenuAddExpenseBtn);
            expenseItem = itemview.findViewById(R.id.expenseItem);

            expenseType = itemview.findViewById(R.id.expenseType);
            expenseDate = itemview.findViewById(R.id.expenseDate);
            expenseAmount = itemview.findViewById(R.id.expenseAmount);

        }
    }

    //check and import list data by search of expenses
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if (searchString.isEmpty()){
                    mListExpenses = mListExpensesOld;
                }else {
                    List<Expenses> list = new ArrayList<>();
                    for (Expenses expe : mListExpensesOld){
                        if (expe.getmExpenseType().toLowerCase().contains(searchString.toLowerCase())
                        || expe.getmExDate().toLowerCase().contains(searchString.toLowerCase())
                        || expe.getmExComment().toLowerCase().contains(searchString.toLowerCase())
                        || convertResultToString(expe.getmExAmount()).toString().contains(searchString.toLowerCase())){
                            list.add(expe);
                        }
                    }
                    mListExpenses = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListExpenses;
                //return new list expenses to show
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListExpenses = (List<Expenses>) filterResults.values;
                notifyDataSetChanged();
                //show the list returned
            }
        };
    }
}
