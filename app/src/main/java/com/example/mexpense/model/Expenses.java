package com.example.mexpense.model;

public class Expenses {
    private int mExId;
    private long mExAmount;
    private String mExDate;
    private String mExComment;

    //Constructor not has id
    public Expenses(long mExAmount, String mExDate, String mExComment) {
        this.mExAmount = mExAmount;
        this.mExDate = mExDate;
        this.mExComment = mExComment;
    }

    //Constructor has id
    public Expenses(int mExId, long mExAmount, String mExDate, String mExComment) {
        this.mExId = mExId;
        this.mExAmount = mExAmount;
        this.mExDate = mExDate;
        this.mExComment = mExComment;
    }

    public int getmExId() {
        return mExId;
    }

    public void setmExId(int mExId) {
        this.mExId = mExId;
    }

    public long getmExAmount() {
        return mExAmount;
    }

    public void setmExAmount(long mExAmount) {
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
}
