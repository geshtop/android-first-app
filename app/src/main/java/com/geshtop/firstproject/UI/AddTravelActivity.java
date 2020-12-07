package com.geshtop.firstproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geshtop.firstproject.Data.Firebase_DBManager;
import com.geshtop.firstproject.Entities.Travel;
import com.geshtop.firstproject.Entities.UserLocation;
import com.geshtop.firstproject.R;
import com.geshtop.firstproject.Utils.GeoHelper;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTravelActivity extends AppCompatActivity {
    Button addRequestBtn;
    ProgressBar addTravelProgressBar;
    Calendar fromDatetemporary ;
    Calendar toDatetemporary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        AddTravelActivity that = this;
        addRequestBtn = (Button) findViewById(R.id.addRequestBtn);
        addTravelProgressBar = (ProgressBar)findViewById(R.id.addTravelProgressBar);
        CalendarView fromDate = (CalendarView)findViewById(R.id.fromDate);
        fromDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                fromDatetemporary = Calendar.getInstance();
                fromDatetemporary.set(year, month, dayOfMonth);

            }
        });

        CalendarView toDate = (CalendarView)findViewById(R.id.toDate);
        toDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                toDatetemporary = Calendar.getInstance();
                toDatetemporary.set(year, month, dayOfMonth);

            }
        });

        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  addTravel();
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference().child("TraveRequest");
//
//                //myRef.setValue("Hello, World!");
//                Travel t = new Travel();
//                EditText editTextClientName = (EditText)findViewById(R.id.editTextClientName);
//                EditText editTextTextClientPhone = (EditText)findViewById(R.id.editTextTextClientPhone);
//                EditText editTextEmailAddress = (EditText)findViewById(R.id.editTextEmailAddress);
//                EditText editTextFromAddress = (EditText)findViewById(R.id.editTextFromAddress);
//                t.setClientName(editTextClientName.getText().toString().trim());
//                t.setClientPhone(editTextTextClientPhone.getText().toString().trim());
//                t.setClientEmail(editTextEmailAddress.getText().toString().trim());
//
//                myRef.push().setValue(t);
//                Toast.makeText(getApplicationContext(), "This Request add to database successfully", Toast.LENGTH_LONG);
//                //NavHostFragment.findNavController(TravelsListActivity.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
//                Intent i = new Intent(that, TravelsListActivity.class);
//                startActivity(i);
            }
        });

    }



    private void addTravel() {
        try {

            Travel t = getTravel();
            addRequestBtn.setEnabled(false);

            Firebase_DBManager.addTravel(t, new Firebase_DBManager.Action<Long>() {
                @Override
                public void onSuccess(Long obj) {
                    Toast.makeText(getBaseContext(), "insert id " + obj, Toast.LENGTH_LONG).show();
                    resetView();
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getBaseContext(), "Error \n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    resetView();
                }

                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        addRequestBtn.setEnabled(false);
                    addTravelProgressBar.setProgress((int) percent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error ", Toast.LENGTH_LONG).show();
            resetView();
        }
    }

    private void resetView() {
        Intent i = new Intent(this, TravelsListActivity.class);
        startActivity(i);
    }

    private Travel getTravel() {

        Travel t = new Travel();
        EditText editTextClientName = (EditText)findViewById(R.id.editTextClientName);
        EditText editTextTextClientPhone = (EditText)findViewById(R.id.editTextTextClientPhone);
        EditText editTextEmailAddress = (EditText)findViewById(R.id.editTextEmailAddress);
        EditText editTextFromAddress = (EditText)findViewById(R.id.editTextFromAddress);

        EditText editTextToAddress = (EditText)findViewById(R.id.editTextToAddress);


        EditText editTextPassengers = (EditText)findViewById(R.id.editTextPassengers);

        //from address
        String fromAddress =editTextFromAddress.getText().toString().trim();
        LatLng lFrom =GeoHelper.getLocationFromAddress(fromAddress, getBaseContext());
        if(lFrom != null)
            t.setTravelLocation(new UserLocation(lFrom.latitude, lFrom.longitude, fromAddress));
        //to address
        String toAddress =editTextToAddress.getText().toString().trim();
        LatLng lTo =GeoHelper.getLocationFromAddress(toAddress, getBaseContext());
        if(lTo != null){
            UserLocation toLocation =    new UserLocation(lTo.latitude, lTo.longitude, toAddress);
               // List<UserLocation> l = new ArrayList<>();
               // l.add(toLocation);
               // t.setDestinations(l);
            t.addDestionationLocation(toLocation);
        }
        t.setClientName(editTextClientName.getText().toString().trim());
        t.setClientPhone(editTextTextClientPhone.getText().toString().trim());
        t.setClientEmail(editTextEmailAddress.getText().toString().trim());
        t.setTravelDate(fromDatetemporary.getTime());
        t.setArrivalDate(toDatetemporary.getTime());
        t.setPassengers(Integer.parseInt(editTextPassengers.getText().toString().trim()));
        return t;
    }
}