package com.example.mexpense.mainfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mexpense.R;
import com.example.mexpense.data.DBManager;
import com.example.mexpense.model.Trips;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> implements Filterable {

    private List<Trips> mListTrips; //new list to show search data result
    private final List<Trips> mListTripsOld; //current list
    private final ITripClickItem mITripClickItem;
    private int delTripId;

    DBManager db;

    //transfer trip data to expense using interface
    public interface ITripClickItem{
        void onClickTripItem(Trips trip);
        void onClickTripEditItem(Trips trip);
    }

    public TripAdapter(List<Trips> mListTrips, ITripClickItem listener) {
        this.mListTrips = mListTrips;
        this.mListTripsOld = mListTrips;
        this.mITripClickItem = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trips trip = mListTrips.get(position);
        if (trip == null) {
            return;
        }

        holder.tripName.setText(trip.getmTripName());
        holder.tripDate.setText(trip.getmTripDate());
        holder.tripDestination.setText(trip.getmTripDestination());

        //using interface to push data from trip of trip recyclerview to expense fragment
        holder.tripItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mITripClickItem.onClickTripItem(trip);
            }
        });

        //using interface to push data from trip of trip recyclerview to update trip fragment
        holder.updateTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mITripClickItem.onClickTripEditItem(trip);
            }
        });

        //delete a trip
        holder.deleteTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                delTripId = trip.getmId();
                db = new DBManager(view.getContext());
                db.deleteTrip(String.valueOf(delTripId));
                mListTrips.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
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

        TextView tripName, tripDate, tripDestination;
        RelativeLayout tripItem;
        Button deleteTrip, updateTrip;

        public TripViewHolder(@NonNull View itemview) {
            super(itemview);
            tripItem = itemview.findViewById(R.id.tripInfo);
            deleteTrip = itemview.findViewById(R.id.deleteTripBtn);
            updateTrip = itemview.findViewById(R.id.editTripBtn);

            tripName = itemview.findViewById(R.id.tripName);
            tripDate = itemview.findViewById(R.id.tripDate);
            tripDestination = itemview.findViewById(R.id.tripDestination);
        }
    }

    //check and import list data of trips
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchTripString = charSequence.toString();
                if(searchTripString.isEmpty()){
                    mListTrips = mListTripsOld;
                }else {
                    List<Trips> tripsList = new ArrayList<>();
                    for(Trips trip : mListTripsOld){
                        if (trip.getmTripName().toLowerCase().contains(searchTripString.toLowerCase())
                        || trip.getmTripDestination().toLowerCase().contains(searchTripString.toLowerCase())
                        || trip.getmTripDate().toLowerCase().contains(searchTripString.toLowerCase())
                        || trip.getmTripDescription().toLowerCase().contains(searchTripString.toLowerCase())){

                            tripsList.add(trip);
                        }
                    }
                    mListTrips = tripsList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListTrips;
                //return new list trips to show
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListTrips = (List<Trips>) filterResults.values;

                notifyDataSetChanged();
                //show the list returned
            }
        };
    }
}
