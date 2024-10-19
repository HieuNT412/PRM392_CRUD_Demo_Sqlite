package com.example.pe_trial.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe_trial.Entity.Product;
import com.example.pe_trial.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Product> productList;
    OnProductClickListener listener;

    public interface OnProductClickListener{
        void onUpdateClick(Product product);
        void onDeleteClick(Product product);
    }

    public MyAdapter(Context _context,List<Product> _productList, OnProductClickListener _listener ){
        context =_context;
        productList = _productList;
        listener = _listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameView.setText(product.getName());
        holder.priceView.setText("Price: $" + product.getPrice());
        holder.quantityView.setText("Quantity: " + product.getQuantity());

        holder.btnEdit.setOnClickListener( e -> {
            listener.onUpdateClick(product);
        });

        holder.btnDelete.setOnClickListener( e -> {
            listener.onDeleteClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
