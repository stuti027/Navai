package com.example.navai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton; // Added for the menu icon
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView sourceAutoComplete;
    private AutoCompleteTextView destinationAutoComplete;
    private AutoCompleteTextView vehicleTypeAutoComplete;
    private ImageButton menuButton; // Reference to the new menu button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fetching views
        sourceAutoComplete = findViewById(R.id.sourceAutoComplete);
        destinationAutoComplete = findViewById(R.id.destinationAutoComplete);
        vehicleTypeAutoComplete = findViewById(R.id.vehicleTypeAutoComplete);
        Button confirmButton = findViewById(R.id.confirmButton);
        menuButton = findViewById(R.id.menuButton); // Initialize the new menu button

        // --- New Menu Button Click Listener ---
        menuButton.setOnClickListener(v -> {
            // Since we only have one item ("Report") for now, we navigate directly.
            // For more items, you would show a popup menu here.
            Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(reportIntent);
        });


        // --- 1. Vehicle Type Adapter ---
        String[] vehicleTypes = new String[]{"Sedan", "SUV", "Truck", "Bike"};
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                vehicleTypes
        );
        vehicleTypeAutoComplete.setAdapter(vehicleAdapter);


        // --- 2. Location Suggestions Adapter (From GeoJSON Utility) ---
        // Ensure GeoJsonParserUtility is implemented and works (from previous steps)
        List<String> locationNames = new ArrayList<>(
                GeoJsonParserUtility.loadLocationCoordinates(this).keySet()
        );

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                locationNames
        );

        // Apply Location Adapter to Source and Destination
        sourceAutoComplete.setAdapter(locationAdapter);
        destinationAutoComplete.setAdapter(locationAdapter);
        sourceAutoComplete.setThreshold(1);
        destinationAutoComplete.setThreshold(1);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = sourceAutoComplete.getText().toString().trim();
                String destination = destinationAutoComplete.getText().toString().trim();
                String vehicleType = vehicleTypeAutoComplete.getText().toString().trim();

                if (source.isEmpty() || destination.isEmpty() || vehicleType.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Optional: Basic validation to ensure the name is in the lookup map
                if (!GeoJsonParserUtility.loadLocationCoordinates(MainActivity.this).containsKey(source) ||
                        !GeoJsonParserUtility.loadLocationCoordinates(MainActivity.this).containsKey(destination)) {
                    Toast.makeText(MainActivity.this, "Source or destination is not a recognized location.", Toast.LENGTH_LONG).show();
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