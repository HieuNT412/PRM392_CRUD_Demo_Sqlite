package com.example.pe_trial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pe_trial.DBHelper.DBHelper;
import com.example.pe_trial.Entity.Product;

public class CreateActivity extends AppCompatActivity {

    Button btnInsert, btnBack;
    EditText eTxtName, eTxtPrice, eTxtQuantity;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);

        eTxtName = findViewById(R.id.eTxtName);
        eTxtPrice = findViewById(R.id.eTxtPrice);
        eTxtQuantity = findViewById(R.id.eTxtQuantity);

        db = new DBHelper(this);

        btnBack = findViewById(R.id.btnBackToMain);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eTxtName.getText().toString().trim();
                String price = eTxtPrice.getText().toString().trim();
                String quantity = eTxtQuantity.getText().toString().trim();

                if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(CreateActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double dPrice = Double.parseDouble(price);
                int iQuantity = Integer.parseInt(quantity);


                // Add product to the database
                boolean result = db.insertItem(name, dPrice, iQuantity);

                if (!result) {
                    Toast.makeText(CreateActivity.this, "Error saving product ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CreateActivity.this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}