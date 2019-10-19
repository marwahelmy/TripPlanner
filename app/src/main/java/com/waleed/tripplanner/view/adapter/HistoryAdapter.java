package com.waleed.tripplanner.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.view.activities.MapsActivity;
import com.waleed.tripplanner.view.activities.TripActivity;
import com.waleed.tripplanner.view.fragments.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryTripViewHolder> {

    List<Trip> tripList;
    HistoryFragment historyFragment;
    private Context context;


    public HistoryAdapter(HistoryFragment historyFragment) {
        this.historyFragment = historyFragment;
        tripList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HistoryTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trip_row, parent, false);
        return new HistoryTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTripViewHolder holder, final int position) {

        holder.getTextViewName().setText(tripList.get(position).getName());

        holder.getTextViewFrom().setText(tripList.get(position).getLocFrom());

        holder.getTextViewTo().setText(tripList.get(position).getLocTo());

        holder.getTextViewDate().setText(tripList.get(position).getDate());

        holder.getTextViewTime().setText(tripList.get(position).getTime());

        holder.getTextViewDescription().setText(tripList.get(position).getDescription());

        holder.getTextViewState().setText(tripList.get(position).getState());

        holder.getTextViewType().setText(tripList.get(position).getType());

        holder.getImageViewDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFragment.deleteTrip(tripList.get(position));
                historyFragment.updateRecyclerView();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList != null ? tripList.size() : 0;
    }


    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
        notifyDataSetChanged();
    }


    public class HistoryTripViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewFrom, textViewTo, textViewDate, textViewTime, textViewDescription, textViewState, textViewType;
        ImageView imageViewDelete;

        public HistoryTripViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TripActivity.class);
                    intent.putExtra("currentTrip", tripList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

        public TextView getTextViewName() {
            if (textViewName == null) {
                textViewName = itemView.findViewById(R.id.textViewName);
            }
            return textViewName;
        }

        public TextView getTextViewFrom() {
            if (textViewFrom == null) {
                textViewFrom = itemView.findViewById(R.id.textViewFrom);
            }
            return textViewFrom;
        }

        public TextView getTextViewTo() {
            if (textViewTo == null) {
                textViewTo = itemView.findViewById(R.id.textViewTo);
            }
            return textViewTo;
        }

        public TextView getTextViewDate() {
            if (textViewDate == null) {
                textViewDate = itemView.findViewById(R.id.textViewDate);
            }
            return textViewDate;
        }

        public TextView getTextViewTime() {
            if (textViewTime == null) {
                textViewTime = itemView.findViewById(R.id.textViewTime);
            }
            return textViewTime;
        }

        public TextView getTextViewDescription() {
            if (textViewDescription == null) {
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
            }
            return textViewDescription;
        }


        public TextView getTextViewState() {
            if (textViewState == null) {
                textViewState = itemView.findViewById(R.id.textViewState);
            }
            return textViewState;
        }


        public TextView getTextViewType() {
            if (textViewType == null) {
                textViewType = itemView.findViewById(R.id.textViewType);
            }
            return textViewType;
        }


        public ImageView getImageViewDelete() {
            if (imageViewDelete == null) {
                imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            }
            return imageViewDelete;
        }
    }


}
