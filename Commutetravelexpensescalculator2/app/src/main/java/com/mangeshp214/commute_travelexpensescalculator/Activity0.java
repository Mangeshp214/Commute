package com.mangeshp214.commute_travelexpensescalculator;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class Activity0 extends AppCompatActivity {

    private DbHandler dbHandler;
    private TextInputEditText name;
    private Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0);

        init();

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(name.getText().toString().trim()))
                    name.setError("Please enter your name");
                else{

                    String setName = name.getText().toString().trim();
                    dbHandler.addTravelMatesName(setName);
                    Intent intent = new Intent(Activity0.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });

    }

    private void init() {

        dbHandler = new DbHandler(Activity0.this);
        name = findViewById(R.id.editTextName);
        buttonContinue = findViewById(R.id.buttonContinue);

    }
}
