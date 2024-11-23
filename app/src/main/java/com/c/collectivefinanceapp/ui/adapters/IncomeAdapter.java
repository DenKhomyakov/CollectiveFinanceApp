package com.c.collectivefinanceapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.entities.Income;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder>{
    private List<Income> incomeList;
    private OnItemClickListener listener;

    public IncomeAdapter(List<Income> incomeList) {
        this.incomeList = incomeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Income income = incomeList.get(position);
        holder.tvTitle.setText(income.getSource());
        holder.tvAmount.setText(String.valueOf(income.getAmount()) + " руб.");
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
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
                        listener.onItemClick(incomeList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Income income);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
