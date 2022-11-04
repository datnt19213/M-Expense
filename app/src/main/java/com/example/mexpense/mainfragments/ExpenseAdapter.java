package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.model.Expenses;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    private final List<Expenses> mListExpenses;

    public ExpenseAdapter(List<Expenses> mListExpenses) {
        this.mListExpenses = mListExpenses;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.expenseAmount.setText((int)expenses.getmExAmount());
    }

    @Override
    public int getItemCount() {
        if (mListExpenses != null) {
            return mListExpenses.size();
        }
        return 0;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView expenseType;
        private final TextView expenseDate;
        private final TextView expenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseType = itemView.findViewById(R.id.expenseType);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
        }
    }
}
