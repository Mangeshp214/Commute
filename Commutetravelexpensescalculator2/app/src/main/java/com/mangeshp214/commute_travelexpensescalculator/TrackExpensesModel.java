package com.mangeshp214.commute_travelexpensescalculator;

public class TrackExpensesModel {

    private String travelMatesName, amount;

    public TrackExpensesModel(String travelMatesName, String amount) {
        this.travelMatesName = travelMatesName;
        this.amount = amount;
    }

    public String getTravelMatesName() {
        return travelMatesName;
    }

    public void setTravelMatesName(String travelMatesName) {
        this.travelMatesName = travelMatesName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
