package com.example.crazydrive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class NewTopTenActivity extends AppCompatActivity {
    private TopTenRecord record;
    private int LOCATION_PERMISSION =0;
    private Context con;
    private String newTopTenMsgStart = "You got: ";
    private String newTopTenMsgSEnd = "$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        con = this;
        Bundle b = getIntent().getExtras();
        long score = b.getLong(GameOverActivity.SCORE_KEY);
        record = new TopTenRecord(score);
        Location location = fetchLocation();
        record.setLat(location.getLatitude());
        record.setLng(location.getLongitude());
        setContentView(R.layout.new_top_ten);
        Button btn = findViewById(R.id.new_top_ten_btn);
        TextView newRecordMsg = findViewById(R.id.new_record_msg);
        newRecordMsg.setText(newTopTenMsgStart + score + newTopTenMsgSEnd);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = findViewById(R.id.name_input);
                record.setName(input.getText().toString());
                TopTenDBHandler topTenDBHandler = new TopTenDBHandler(con);
                topTenDBHandler.updateRecordsFromDB();
                if(topTenDBHandler.insert(record)) {
                    Intent myIntent = new Intent(NewTopTenActivity.this, TopTenActivity.class);
                    NewTopTenActivity.this.startActivity(myIntent);
                }
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private Location getLatLong(){
            LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            try {
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    private Location fetchLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return getLatLong();
    }
}


