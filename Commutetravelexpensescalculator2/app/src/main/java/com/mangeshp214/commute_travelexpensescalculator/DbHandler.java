package com.mangeshp214.commute_travelexpensescalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DbHandler extends SQLiteOpenHelper{

    private String tableExpenses = "EXPENSES";
    private String TravelMate = "PARTICIPANT";
    private String amount = "AMOUNT";
    private String createTableExpenses = "CREATE TABLE "+tableExpenses+" ("+ TravelMate +" TEXT, "+amount+" INTEGER)";

    private String tableTravelMate = "MATE";
    private String TravelMateName = "NAME";
    private String createTableTravelMate = "CREATE TABLE "+ tableTravelMate +" ("+ TravelMateName +" TEXT)";

    public DbHandler(Context context) {
        super(context, "Commute", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableTravelMate);
        db.execSQL(createTableExpenses);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+tableExpenses+";");
        db.execSQL("DROP TABLE IF EXISTS "+ tableTravelMate +";");

    }

    public boolean newUser(){

        String selectFromTableParticipants = "select * from "+ tableTravelMate +";";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(selectFromTableParticipants, null);

        if(c.getCount() == 0)
            return true;

        return false;

    }

    public boolean isEmptyExpenses(){

        String selectFromTableParticipants = "select * from "+ tableExpenses +";";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(selectFromTableParticipants, null);

        if(c.getCount() == 0)
            return true;

        return false;

    }

    public void addTravelMatesName(String setName) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TravelMateName, setName);
        db.insert(tableTravelMate, null, contentValues);
        db.close();

    }

    public boolean removeTravelMate(String mateName){

        String removeQuery = "delete from "+tableTravelMate+" where "+TravelMateName+" = \""+mateName+"\";";
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.rawQuery(removeQuery, null).moveToFirst();
        }catch (Exception e){return false;}

        return true;

    }

    public ArrayList<AddTravelMateModel> getAllTravelMates(){

        ArrayList<AddTravelMateModel> allTravelMates = new ArrayList<AddTravelMateModel>();

        String selectAllQuery = "select * from "+tableTravelMate+";";

        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(selectAllQuery, null);

        if(c.moveToFirst()){

            do{

                AddTravelMateModel model = new AddTravelMateModel(c.getString(c.getColumnIndex(TravelMateName)));
                allTravelMates.add(model);

            }while (c.moveToNext());

        }

        return allTravelMates;

    }

    public void initExpensesTable(ArrayList<AddTravelMateModel> travelMatesName){

        SQLiteDatabase db = getWritableDatabase();

        Log.d(TAG, "initExpensesTable: "+travelMatesName.size());

        for(int i=0; i<travelMatesName.size(); i++){

            ContentValues contentValues = new ContentValues();
            contentValues.put(TravelMate, travelMatesName.get(i).getTravelMatesName());
            contentValues.put(amount, 0);
            db.insert(tableExpenses, null, contentValues);

        }

    }

    public ArrayList<TrackExpensesModel> getAllTravelMatesExpenses(){

        ArrayList<TrackExpensesModel> allTravelMates = new ArrayList<TrackExpensesModel>();

        String selectAllQuery = "select * from "+tableExpenses+";";

        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(selectAllQuery, null);

        if(c.moveToFirst()){

            do{

                TrackExpensesModel model = new TrackExpensesModel(c.getString(0),
                                                                    c.getString(1));
                allTravelMates.add(model);

            }while (c.moveToNext());

        }

        return allTravelMates;

    }

    public void updateAmountOfMate(String travelMatesName, int newAmt) {

        String updateQuery = "update "+tableExpenses+" set "+amount+" = \""+newAmt+"\" where "+TravelMate+" = \""+travelMatesName+"\"";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(updateQuery);
        db.close();

    }

    public String getNewAmountOf(String travelMatesName) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        String selectQuery = "select "+amount+" from "+tableExpenses+" where "+TravelMate+" = \""+travelMatesName+"\"";
        if(c.moveToFirst())
            return c.getString(0);

        return null;

    }

    public void truncateTableExpenses(){

        SQLiteDatabase db = getWritableDatabase();
        String truncateQuery = "delete from "+tableExpenses;
        db.execSQL(truncateQuery);

    }

}
