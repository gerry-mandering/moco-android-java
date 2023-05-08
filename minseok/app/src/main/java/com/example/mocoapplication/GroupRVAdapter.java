package com.example.mocoapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupRVAdapter extends RecyclerView.Adapter<GroupRVAdapter.ViewHolder> {

    private ArrayList<GroupParcel> groupParcelArrayList;
    private Context context;
    int lastPos = -1;
    private GroupClickInterface groupClickInterface;

    public GroupRVAdapter(ArrayList<GroupParcel> groupParcelArrayList, Context context, GroupClickInterface groupClickInterface) {
        this.groupParcelArrayList = groupParcelArrayList;
        this.context = context;
        this.groupClickInterface = groupClickInterface;
    }

    @NonNull
    @Override
    public GroupRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GroupParcel groupParcel = groupParcelArrayList.get(position);
        holder.titleTV.setText(groupParcel.getTitle());
        holder.dateTV.setText(groupParcel.getDate().substring(5));
        holder.contentTV.setText(groupParcel.getContent());
        holder.locationTV.setText(groupParcel.getLocation());
        holder.timeTV.setText(groupParcel.getTime());
        holder.headCntTV.setText(groupParcel.getCurrentHeadCnt() + " / " + groupParcel.getTotalHeadCnt());

        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupClickInterface.onGroupClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return groupParcelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV, contentTV, locationTV, dateTV, timeTV, headCntTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVTitle);
            contentTV = itemView.findViewById(R.id.idTVContent);
            locationTV = itemView.findViewById(R.id.idTVLocation);
            dateTV = itemView.findViewById(R.id.idTVDate);
            timeTV = itemView.findViewById(R.id.idTVTime);
            headCntTV = itemView.findViewById(R.id.idTVHeadCnt);
        }
    }

    public interface GroupClickInterface {
        void onGroupClick(int position);
    }
}
