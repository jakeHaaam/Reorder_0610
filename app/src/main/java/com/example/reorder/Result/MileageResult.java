package com.example.reorder.Result;

public class MileageResult {
    int result;
    int mileage;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public MileageResult(int result, int mileage) {
        this.result = result;
        this.mileage = mileage;
    }
}
