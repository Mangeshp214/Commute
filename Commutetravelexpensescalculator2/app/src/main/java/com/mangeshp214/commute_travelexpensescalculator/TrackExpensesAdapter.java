package com.mangeshp214.commute_travelexpensescalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrackExpensesAdapter extends ArrayAdapter<TrackExpensesModel> {

    Context context;
    private ArrayList<TrackExpensesModel> allTravelMates;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        DbHandler dbHandler = new DbHandler(context);
        allTravelMates = dbHandler.getAllTravelMatesExpenses();

    }

    public TrackExpensesAdapter(@NonNull Context context, @NonNull ArrayList<TrackExpensesModel> allTravelMates) {
        super(context, 0, allTravelMates);
        this.context = context;
        this.allTravelMates = allTravelMates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listMateExpenseView = convertView;
        final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(listMateExpenseView == null)
            listMateExpenseView = inflater.inflate(R.layout.listview_track_expenses,parent, false);

        final TrackExpensesModel currentPos = getItem(position);

        TextView travelMateName = listMateExpenseView.findViewById(R.id.textViewTravelMatesName);
        ImageView minusButton = listMateExpenseView.findViewById(R.id.imageViewMinus);
        ImageView plusButton = listMateExpenseView.findViewById(R.id.imageViewPlus);
        final TextView amountTV = listMateExpenseView.findViewById(R.id.textViewAmount);

        travelMateName.setText(currentPos.getTravelMatesName());
        amountTV.setText(currentPos.getAmount());

       final int curAmt = Integer.parseInt(currentPos.getAmount());

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Minus", Toast.LENGTH_SHORT).show();
                createDialogBox("sub", currentPos, curAmt);
                amountTV.setText(currentPos.getAmount());

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "plus", Toast.LENGTH_SHORT).show();
                createDialogBox("add", currentPos, curAmt);
                amountTV.setText(currentPos.getAmount());
            }
        });

        return listMateExpenseView;
    }

    private void createDialogBox(String operation, final TrackExpensesModel currentPos, final int curAmt) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.track_expenses_plus_minus_popup, null);
        final TextInputEditText amount = view.findViewById(R.id.enterAmountEditText);
        final Button opr = view.findViewById(R.id.buttonAddOrSub);
        final DbHandler dbHandler = new DbHandler(context);
        final int curr = curAmt;

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        if(operation.equals("add")){

            opr.setText(R.string.addAmountButton);

            opr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty(amount.getText().toString().trim()))
                        amount.setError("Please enter amount");
                    else{

                        int inputAmt = Integer.parseInt(amount.getText().toString());
                        int newAmt = curr + inputAmt;

                        dbHandler.updateAmountOfMate(currentPos.getTravelMatesName(), newAmt);
                        notifyDataSetChanged();
                        Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                        currentPos.setAmount(newAmt+"");
                        dialog.dismiss();

                    }

                }
            });

        }else{

            opr.setText(R.string.subAmountButton);
            opr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty(amount.getText().toString().trim()))
                        amount.setError("Please enter amount");
                    else{

                        int inputAmt = Integer.parseInt(amount.getText().toString());

                        if(curAmt == 0 || curr < inputAmt){

                            Toast.makeText(context, "Can't subtract", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }else{

                            int newAmt = curr - inputAmt;

                            dbHandler.updateAmountOfMate(currentPos.getTravelMatesName(), newAmt);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                            currentPos.setAmount(newAmt+"");
                            dialog.dismiss();

                        }

                    }

                }
            });

        }



    }

}
