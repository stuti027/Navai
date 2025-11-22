package com.example.navai;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String source = extras.getString("SOURCE_KEY");
            String destination = extras.getString("DESTINATION_KEY");
            String vehicleType = extras.getString("VEHICLE_TYPE_KEY");

            // You can now use these variables for map logic
            // e.g., Log.d("MapViewActivity", "Source: " + source);
            // In a real application, you would use these to:
            // a) Geocode the source and destination to (lat, lon)
            // b) Call a routing algorithm using your GeoJSON data and segment 'weight'
            // c) Use a WebView bridge (JavaScriptInterface) to pass the resulting route coordinates
            //    to the Leaflet map in navai_map.html for drawing.
        }

        WebView mapWebView = findViewById(R.id.mapWebView);
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JS for Leaflet/Folium to work

        webSettings.setDomStorageEnabled(true);

        mapWebView.setWebViewClient(new WebViewClient());

        // Load the HTML file from the 'assets' folder
        mapWebView.loadUrl("file:///android_asset/navai_map.html");

        /*
         * Placeholder for Map Route Logic:
         * To draw a route based on 'source', 'destination', and 'vehicleType',
         * you would implement a routing algorithm in Java/Kotlin (or a server)
         * that uses your segment data. Once you have the route's coordinates,
         * you would call a JavaScript function within the mapWebView like this:
         *
         * String jsCommand = "javascript:drawRoute(" + routeCoordinatesJson + ");";
         * mapWebView.evaluateJavascript(jsCommand, null);
         */
    }
}