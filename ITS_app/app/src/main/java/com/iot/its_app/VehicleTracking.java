package com.iot.its_app;

public class VehicleTracking {
    int averageSpeed;
    int vehiclesCount;
    int totalSpeed;

    public VehicleTracking() {
    }

    public VehicleTracking(int averageSpeed, int vehiclesCount, int totalSpeed) {
        this.averageSpeed = averageSpeed;
        this.vehiclesCount = vehiclesCount;
        this.totalSpeed = totalSpeed;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getVehiclesCount() {
        return vehiclesCount;
    }

    public void setVehiclesCount(int vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    public int getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(int totalSpeed) {
        this.totalSpeed = totalSpeed;
    }
}
