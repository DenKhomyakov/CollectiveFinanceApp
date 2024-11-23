package com.c.collectivefinanceapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.entities.Goal;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder>{

    private List<Goal> goalList;
    private OnItemClickListener listener;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.tvTitle.setText(goal.getName());
        holder.tvAmount.setText(String.valueOf(goal.getAmount()) + " руб.");
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvAmount = itemView.findViewById(R.id.tvAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(goalList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Goal goal);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
