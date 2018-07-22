package com.mangeshp214.commute_travelexpensescalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DbHandler dbHandler;
    private Button beginNewJourneyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if(dbHandler.newUser())
            bypassActivity();

        if(!dbHandler.isEmptyExpenses())
            bypassToExpenseActivity();

        beginNewJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTravelMateActivity.class);
                startActivity(intent);
                finish();
             }
        });

    }

    private void bypassToExpenseActivity() {

        Intent intent = new Intent(MainActivity.this, TrackExpensesActivity.class);
        startActivity(intent);
        finish();

    }

    private void bypassActivity() {

        Intent intent = new Intent(MainActivity.this, Activity0.class);
        startActivity(intent);
        finish();

    }

    private void init() {

        dbHandler = new DbHandler(MainActivity.this);
        beginNewJourneyButton = findViewById(R.id.buttonBeginNewJourney);

    }
}
