package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.model.Trips;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private final List<Trips> mListTrips;

    //Create TrippAdapter using listTrip data
    public TripAdapter(List<Trips> mListTrips) {
        this.mListTrips = mListTrips;
    }

    @NonNull
    @Override //get view item_trip layout
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override //Bind data on the list view item
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trips trip = mListTrips.get(position);
        if (trip == null){
            return;
        }
        holder.tripName.setText(trip.getmTripName());
        holder.tripDate.setText(trip.getmTripDate());
        holder.tripDestination.setText(trip.getmTripDestination());
    }

    @Override //check and return item in listTrips data
    public int getItemCount() {
        if(mListTrips != null){
           return mListTrips.size();
        }
        return 0;
    }



    //view holder of Trip
    public static class TripViewHolder extends RecyclerView.ViewHolder{

        private final TextView tripName;
        private final TextView tripDate;
        private final TextView tripDestination;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = (TextView) itemView.findViewById(R.id.tripName);
            tripDate = (TextView) itemView.findViewById(R.id.tripDate);
            tripDestination = (TextView) itemView.findViewById(R.id.tripDestination);
        }
    }
}
