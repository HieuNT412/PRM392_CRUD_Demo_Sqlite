package com.example.pe_trial.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe_trial.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nameView, priceView, quantityView;
    Button btnEdit, btnDelete;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.txtName);
        priceView = itemView.findViewById(R.id.txtPrice);
        quantityView = itemView.findViewById(R.id.txtQuantity);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

}
