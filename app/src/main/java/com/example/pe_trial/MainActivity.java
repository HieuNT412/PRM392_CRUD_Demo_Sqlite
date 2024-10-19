package com.example.pe_trial;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pe_trial.DBHelper.DBHelper;
import com.example.pe_trial.Entity.Product;
import com.example.pe_trial.RecyclerView.MyAdapter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<Product> productList;
    DBHelper db;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button btnToCreate = findViewById(R.id.btnToCreate);
        db = new DBHelper(this);
        productList = new ArrayList<>();

        btnToCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CreateProductActivity to add a new product
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        loadProducts();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, productList, new MyAdapter.OnProductClickListener(){
            @Override
            public void onUpdateClick(Product product) {
                showUpdateDialog(product);
            }

            @Override
            public void onDeleteClick(Product product) {
                deleteProduct(product);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void loadProducts(){
        Cursor cursor = db.getAllItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                Product product = new Product(id, name, price, quantity);
                productList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void showUpdateDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Product");

        // Create a layout to hold the input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);


        final EditText inputName = new EditText(this);
        final EditText inputPrice = new EditText(this);
        final EditText inputQuantity = new EditText(this);

        inputName.setText(product.getName());
        inputPrice.setText(String.valueOf(product.getPrice()));
        inputQuantity.setText(String.valueOf(product.getQuantity()));

        // Add EditText fields to the layout
        layout.addView(inputName);
        layout.addView(inputPrice);
        layout.addView(inputQuantity);

        // Set the layout as the view for the dialog
        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = inputName.getText().toString();
            double newPrice = Double.parseDouble(inputPrice.getText().toString());
            int newQuantity = Integer.parseInt(inputQuantity.getText().toString());

            // Update product in database
            db.updateItem(product.getId(), newName, newPrice, newQuantity);
            refreshProductList();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }

    // Delete a product
    private void deleteProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete this product?");

        // Set up the "Yes" button
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Delete the product from the database
            db.deleteItem(product.getId());
            refreshProductList();  // Refresh the product list after deletion
            Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();
        });

        // Set up the "No" button
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }

    // Refresh the product list
    private void refreshProductList() {
        productList.clear();
        loadProducts();
        adapter.notifyDataSetChanged();
    }
}