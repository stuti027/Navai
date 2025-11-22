package com.example.navai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText sourceEditText;
    private TextInputEditText destinationEditText;
    private AutoCompleteTextView vehicleTypeAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.sourceEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        vehicleTypeAutoComplete = findViewById(R.id.vehicleTypeAutoComplete);
        Button confirmButton = findViewById(R.id.confirmButton);

        String[] vehicleTypes = new String[]{"Sedan", "SUV", "Truck", "Bike"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                vehicleTypes
        );
        vehicleTypeAutoComplete.setAdapter(adapter);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = sourceEditText.getText().toString().trim();
                String destination = destinationEditText.getText().toString().trim();
                String vehicleType = vehicleTypeAutoComplete.getText().toString().trim();

                if (source.isEmpty() || destination.isEmpty() || vehicleType.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Intent intent = new Intent(MainActivity.this, MapViewActivity.class);

                    intent.putExtra("SOURCE_KEY", source);
                    intent.putExtra("DESTINATION_KEY", destination);
                    intent.putExtra("VEHICLE_TYPE_KEY", vehicleType);

                    startActivity(intent);
            }
        });
    }
}
