package com.geshtop.firstproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geshtop.firstproject.R;

public class TravelsListActivity extends AppCompatActivity {
    TravelsListActivity that = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels_list);
        Button addTravelRequestBtn = (Button)findViewById(R.id.addTravelRequestBtn);
        addTravelRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(that, AddTravelActivity.class);
                startActivity(i);
            }
        });
    }
}