package com.example.mexpense.model;

import java.io.Serializable;

public class Expenses implements Serializable {
    private int mExId;
    private String mExpenseType;
    private int mExAmount;
    private String mExDate;
    private String mExComment;
    private int mExTripId;

    //getter setter expense
    public int getmExId() {
        return mExId;
    }

    public void setmExId(int mExId) {
        this.mExId = mExId;
    }

    public String getmExpenseType() {
        return mExpenseType;
    }

    public void setmExpenseType(String mExpenseType) {
        this.mExpenseType = mExpenseType;
    }

    public int getmExAmount() {
        return mExAmount;
    }

    public void setmExAmount(int mExAmount) {
        this.mExAmount = mExAmount;
    }

    public String getmExDate() {
        return mExDate;
    }

    public void setmExDate(String mExDate) {
        this.mExDate = mExDate;
    }

    public String getmExComment() {
        return mExComment;
    }

    public void setmExComment(String mExComment) {
        this.mExComment = mExComment;
    }

    public int getmExTripId() {
        return mExTripId;
    }

    public void setmExTripId(int mExTripId) {
        this.mExTripId = mExTripId;
    }
}
