package com.c.collectivefinanceapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.entities.Accumulation;

import java.util.List;

public class AccumulationAdapter extends RecyclerView.Adapter<AccumulationAdapter.ViewHolder> {
    private List<Accumulation> accumulationList;
    private OnItemClickListener listener;

    public AccumulationAdapter(List<Accumulation> accumulationList) {
        this.accumulationList = accumulationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accumulation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accumulation accumulation = accumulationList.get(position);
        holder.tvTitle.setText(accumulation.getName());
        holder.tvAmount.setText(String.valueOf(accumulation.getAmount()) + " руб.");
    }

    @Override
    public int getItemCount() {
        return accumulationList.size();
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
                        listener.onItemClick(accumulationList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Accumulation accumulation);
    }

    public void setOnClickListener(AccumulationAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
