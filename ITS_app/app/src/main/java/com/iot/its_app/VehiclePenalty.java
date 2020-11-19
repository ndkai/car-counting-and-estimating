package com.iot.its_app;

public class VehiclePenalty {
    String image;
    int speed;
    String time;

    public VehiclePenalty(String image, int speed, String time) {
        this.image = image;
        this.speed = speed;
        this.time = time;
    }

    public VehiclePenalty() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
