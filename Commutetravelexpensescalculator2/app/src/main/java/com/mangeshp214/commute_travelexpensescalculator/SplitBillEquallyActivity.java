package com.mangeshp214.commute_travelexpensescalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SplitBillEquallyActivity extends AppCompatActivity {

    private Button endJourneyButton;
    private DbHandler dbHandler;
    private ArrayList<TrackExpensesModel> allExpenses;
    private String splittedBill;
    private TextView splitedBillInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill_equally);

        init();

        allExpenses = dbHandler.getAllTravelMatesExpenses();

        splittedBill = calcBillSplit(allExpenses);
        splitedBillInfo.setText(splittedBill);

        endJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHandler.truncateTableExpenses();
                Intent intent = new Intent(SplitBillEquallyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private String calcBillSplit(ArrayList<TrackExpensesModel> allExpenses) {

        String _splittedBill = "";

        int totalAmountSpent = 0;
        int numberOfMates = allExpenses.size();
        int equity = 0;

        for(int i=0; i<allExpenses.size(); i++){

            totalAmountSpent = totalAmountSpent + Integer.parseInt(allExpenses.get(i).getAmount());

        }

        equity = totalAmountSpent / numberOfMates;

        _splittedBill = _splittedBill + " Total amount spent by all the mates is Rs.: "+totalAmountSpent+".\n";
        _splittedBill = _splittedBill + " Per head amount is Rs.: "+equity+".\n\n";
        for (int i=0; i<allExpenses.size(); i++){

            if(equity > Integer.parseInt(allExpenses.get(i).getAmount())){

                //has to give
                int amount = 0;
                amount = equity - Integer.parseInt(allExpenses.get(i).getAmount());
                _splittedBill = _splittedBill + allExpenses.get(i).getTravelMatesName()+" has given Rs. "+allExpenses.get(i).getAmount()
                        +" , has to give Rs. "+amount+".\n";

            }else if (equity < Integer.parseInt(allExpenses.get(i).getAmount())){

                //has to be given
                int amount = 0;
                amount = Integer.parseInt(allExpenses.get(i).getAmount()) - equity;
                _splittedBill = _splittedBill + allExpenses.get(i).getTravelMatesName()+" has given Rs. "+allExpenses.get(i).getAmount()
                        +" , should take Rs. "+amount+".\n";

            }else {

                _splittedBill = _splittedBill + allExpenses.get(i).getTravelMatesName()+" has given Rs. "+allExpenses.get(i).getAmount()
                        +" , has no need to take or give any amount.\n";

            }

        }

        return _splittedBill;

    }

    private void init() {

        endJourneyButton = findViewById(R.id.buttonEndJourney);
        dbHandler = new DbHandler(SplitBillEquallyActivity.this);
        allExpenses = new ArrayList<>();
        splitedBillInfo = findViewById(R.id.textViewSplitBillInfo);

    }
}
