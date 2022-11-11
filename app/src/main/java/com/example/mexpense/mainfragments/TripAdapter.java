package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.databinding.ItemTripBinding;
import com.example.mexpense.model.Trips;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {


    private final List<Trips> mListTrips;

    public TripAdapter(List<Trips> mListTrips) {
        this.mListTrips = mListTrips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripBinding itemTripBinding = ItemTripBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new TripViewHolder(itemTripBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trips trip = mListTrips.get(position);
        if (trip == null) {
            return;
        }
        holder.itemTripBinding.tripName.setText(trip.getmTripName());
        holder.itemTripBinding.tripDate.setText(trip.getmTripDate());
        holder.itemTripBinding.tripDestination.setText(trip.getmTripDestination());
    }

    @Override
    public int getItemCount() {
        if (mListTrips != null) {
            return mListTrips.size();
        }
        return 0;
    }

    //view holder of Trip
    public static class TripViewHolder extends RecyclerView.ViewHolder{

        private final ItemTripBinding itemTripBinding;

        public TripViewHolder(@NonNull ItemTripBinding itemTripBinding) {
            super(itemTripBinding.getRoot());
            this.itemTripBinding = itemTripBinding;
        }
    }
}
