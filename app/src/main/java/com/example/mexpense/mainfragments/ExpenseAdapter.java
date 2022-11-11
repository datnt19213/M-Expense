package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.databinding.FragmentExpenseBinding;
import com.example.mexpense.databinding.ItemExpenseBinding;
import com.example.mexpense.model.Expenses;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    private final List<Expenses> mListExpenses;

    public ExpenseAdapter(List<Expenses> mListExpenses) {
        this.mListExpenses = mListExpenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExpenseBinding itemExpenseBinding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ExpenseViewHolder(itemExpenseBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expenses expenses = mListExpenses.get(position);

        if (expenses == null){
            return;
        }

        holder.itemExpenseBinding.expenseType.setText(expenses.getmExpenseType());
        holder.itemExpenseBinding.expenseDate.setText(expenses.getmExDate());
        holder.itemExpenseBinding.expenseAmount.setText(expenses.getmExAmount());
    }

    @Override
    public int getItemCount() {
        if (mListExpenses != null) {
            return mListExpenses.size();
        }
        return 0;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        ItemExpenseBinding itemExpenseBinding;

        public ExpenseViewHolder(@NonNull ItemExpenseBinding itemExpenseBinding) {
            super(itemExpenseBinding.getRoot());
            this.itemExpenseBinding = itemExpenseBinding;
        }
    }
}
