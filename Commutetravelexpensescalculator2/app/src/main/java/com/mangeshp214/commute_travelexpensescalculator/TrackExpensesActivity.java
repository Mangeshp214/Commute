package com.mangeshp214.commute_travelexpensescalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TrackExpensesActivity extends AppCompatActivity {

    private ListView lvTrackExpenses;
    private Button splitAmountButton;
    private DbHandler dbHandler;

    @Override
    protected void onResume() {
        super.onResume();
        updateExpenseTable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateExpenseTable();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateExpenseTable();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateExpenseTable();
    }

    private void updateExpenseTable() {

        ArrayList<TrackExpensesModel> expensesAllMates = new ArrayList<>();
        expensesAllMates = dbHandler.getAllTravelMatesExpenses();

        ArrayAdapter<TrackExpensesModel> trackExpensesAdapterArrayAdapter =
                new TrackExpensesAdapter(TrackExpensesActivity.this, expensesAllMates);
        lvTrackExpenses.setAdapter(trackExpensesAdapterArrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_expences);

        init();

        ArrayList<AddTravelMateModel> allTravelMatesNames = new ArrayList<>();
        allTravelMatesNames = dbHandler.getAllTravelMates();

        if(dbHandler.isEmptyExpenses())
            createExpensesTable(allTravelMatesNames);

        ArrayList<TrackExpensesModel> expensesAllMates = new ArrayList<>();
        expensesAllMates = dbHandler.getAllTravelMatesExpenses();

        ArrayAdapter<TrackExpensesModel> trackExpensesAdapterArrayAdapter =
                new TrackExpensesAdapter(TrackExpensesActivity.this, expensesAllMates);
        lvTrackExpenses.setAdapter(trackExpensesAdapterArrayAdapter);

        splitAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackExpensesActivity.this, SplitBillEquallyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void createExpensesTable(ArrayList allTravelMates) {

        dbHandler.initExpensesTable(allTravelMates);

    }

    private void init() {

        lvTrackExpenses = findViewById(R.id.listViewTrackExpenses);
        splitAmountButton = findViewById(R.id.buttonSplitBillEqually);
        dbHandler = new DbHandler(TrackExpensesActivity.this);

    }
}
