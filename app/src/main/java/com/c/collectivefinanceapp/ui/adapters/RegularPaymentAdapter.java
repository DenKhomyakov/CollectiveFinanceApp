package com.c.collectivefinanceapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.entities.RegularPayment;

import java.util.List;

public class RegularPaymentAdapter extends RecyclerView.Adapter<RegularPaymentAdapter.ViewHolder> {
    private List<RegularPayment> regularPaymentList;
    private OnItemClickListener listener;

    public RegularPaymentAdapter(List<RegularPayment> regularPaymentList) {
        this.regularPaymentList = regularPaymentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.regular_payment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegularPayment regularPayment = regularPaymentList.get(position);
        holder.tvTitle.setText(regularPayment.getName());
        holder.tvAmount.setText(String.valueOf(regularPayment.getAmount()) + " руб.");
    }

    @Override
    public int getItemCount() {
        return regularPaymentList.size();
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
                        listener.onItemClick(regularPaymentList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RegularPayment regularPayment);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
