package com.geshtop.firstproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geshtop.firstproject.Data.Firebase_DBManager;
import com.geshtop.firstproject.Entities.Travel;
import com.geshtop.firstproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;

public class AddTravelActivity extends AppCompatActivity {
    Button addRequestBtn;
    ProgressBar addTravelProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        AddTravelActivity that = this;
        addRequestBtn = (Button) findViewById(R.id.addRequestBtn);
        addTravelProgressBar = (ProgressBar)findViewById(R.id.addTravelProgressBar);


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
        t.setClientName(editTextClientName.getText().toString().trim());
        t.setClientPhone(editTextTextClientPhone.getText().toString().trim());
        t.setClientEmail(editTextEmailAddress.getText().toString().trim());

        return t;
    }
}