package com.example.lanji_jrzc.cardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import java.util.Random;
import android.graphics.Color;

import java.util.List;

/**
 * This is the adapter for the RecyclerView used in the MainActivity.
 *
 * @author Lan Jing Li
 * @version May 29, 2018
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Task> taskList;

    public RecyclerViewAdapter(Context mContext, List<Task> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView time;
        public TextView thumbnailLetter;
        public ImageButton deleteButton;

        public ViewHolder(View v) {
            super(v);
            //Initialize widget reference variables
            taskName = v.findViewById(R.id.taskView);
            time = v.findViewById(R.id.timeView);
            thumbnailLetter = v.findViewById(R.id.thumbnailView);
            deleteButton = v.findViewById(R.id.deleteButton);
        }
    }

    // This method creates new vies
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new View
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        return new ViewHolder(itemView);
    }

    //This method gets elements from taskList at the current position. Replace the contents of the view
    //with elements
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getTaskName());
        holder.time.setText(task.getTime());
        holder.thumbnailLetter.setText((task.getThumbnailLetter()));
        holder.thumbnailLetter.setBackgroundColor(task.getColor());

        // Set a click listener for delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskList.size());
            }
        });
    }

    //This method returns the size of the taskList
    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
