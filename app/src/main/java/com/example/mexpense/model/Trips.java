package com.example.mexpense.model;

import java.io.Serializable;

public class Trips implements Serializable {
    private int mId;
    private String mTripName;
    private String mTripDestination;
    private String mTripDate;
    private String mTripRiskAssessment;
    private String mTripDescription;

    //Getter and Setter
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTripName() {
        return mTripName;
    }

    public void setmTripName(String mTripName) {
        this.mTripName = mTripName;
    }

    public String getmTripDestination() {
        return mTripDestination;
    }

    public void setmTripDestination(String mTripDestination) {
        this.mTripDestination = mTripDestination;
    }

    public String getmTripDate() {
        return mTripDate;
    }

    public void setmTripDate(String mTripDate) {
        this.mTripDate = mTripDate;
    }

    public String getmTripRiskAssessment() {
        return mTripRiskAssessment;
    }

    public void setmTripRiskAssessment(String mTripRiskAssessment) {
        this.mTripRiskAssessment = mTripRiskAssessment;
    }

    public String getmTripDescription() { return mTripDescription; }

    public void setmTripDescription(String mTripDescription) {
        this.mTripDescription = mTripDescription;
    }
}
