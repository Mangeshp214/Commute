package com.mangeshp214.commute_travelexpensescalculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddTravelMateAdapter extends ArrayAdapter<AddTravelMateModel> {

    Context context;

    public AddTravelMateAdapter(@NonNull Context context, ArrayList<AddTravelMateModel> allTravelMates) {
        super(context, 0, allTravelMates);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listMateNameView = convertView;
        final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(listMateNameView == null)
            listMateNameView = inflater.inflate(R.layout.listview_add_travel_mate,parent, false);

        final AddTravelMateModel currentPos = getItem(position);

        TextView travelMateName = listMateNameView.findViewById(R.id.textViewMatesName);
        ImageView removeMate = listMateNameView.findViewById(R.id.imageViewRemove);

        travelMateName.setText(currentPos.getTravelMatesName());
        removeMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHandler dbHandler = new DbHandler(context);
                if(dbHandler.removeTravelMate(currentPos.getTravelMatesName())){

                    Toast.makeText(context, "Travel Mate Removed("+currentPos.getTravelMatesName()+")", Toast.LENGTH_SHORT).show();
                    //AddTravelMateActivity addTravelMate = new AddTravelMateActivity();
                    //addTravelMate.updateTravelMatesList();

                    remove(currentPos);

                }

            }
        });

        return listMateNameView;
    }
}
