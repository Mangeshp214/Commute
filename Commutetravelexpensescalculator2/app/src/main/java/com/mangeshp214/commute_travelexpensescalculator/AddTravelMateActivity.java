package com.mangeshp214.commute_travelexpensescalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTravelMateActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTravelMate;
    private ListView lvTravelMate;
    private Button doneAddingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_mate);

        init();

        fabAddTravelMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTravelMateActivity.this);
                View view = getLayoutInflater().inflate(R.layout.add_travel_mate_popup, null);
                final TextInputEditText etName = view.findViewById(R.id.etName);
                Button addMate = view.findViewById(R.id.buttonAddMate);

                addMate.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(etName.getText().toString().trim()))
                            etName.setError("Please enter your mate name!");
                        else{

                            DbHandler dbHandler = new DbHandler(AddTravelMateActivity.this);
                            dbHandler.addTravelMatesName(etName.getText().toString().trim());
                            updateTravelMatesList();
                            etName.setText("");
                            etName.setHint(R.string.addMoreTravelMateHint);
                            Toast.makeText(AddTravelMateActivity.this, R.string.travelMateAddedToast, Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();

            }
        });

        DbHandler dbHandler = new DbHandler(AddTravelMateActivity.this);

        ArrayList<AddTravelMateModel> allTravelMates = new ArrayList<>();
        allTravelMates = dbHandler.getAllTravelMates();

        ArrayAdapter<AddTravelMateModel> addTravelMateModelArrayAdapter = new AddTravelMateAdapter(AddTravelMateActivity.this, allTravelMates);
        lvTravelMate.setAdapter(addTravelMateModelArrayAdapter);

        doneAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddTravelMateActivity.this, TrackExpensesActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void updateTravelMatesList(){

        DbHandler dbHandler = new DbHandler(AddTravelMateActivity.this);

        ArrayList<AddTravelMateModel> allTravelMates = new ArrayList<>();
        allTravelMates = dbHandler.getAllTravelMates();

        ArrayAdapter<AddTravelMateModel> addTravelMateModelArrayAdapter = new AddTravelMateAdapter(AddTravelMateActivity.this, allTravelMates);
        lvTravelMate.setAdapter(addTravelMateModelArrayAdapter);


    }

    private void init() {

        fabAddTravelMate = findViewById(R.id.fabAddTravelMate);
        lvTravelMate = findViewById(R.id.ListViewTravelMate);
        doneAddingButton = findViewById(R.id.buttonDoneAdding);

    }
}
