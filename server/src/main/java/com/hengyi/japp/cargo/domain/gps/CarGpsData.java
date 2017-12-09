package com.hengyi.japp.cargo.domain.gps;

import java.io.Serializable;

/**
 * Created by jzb on 16-3-28.
 */
public class CarGpsData implements Serializable {
    private String carNo;
    private String dateTimeString;
    private double longitude;
    private double latitude;
    private String province;
    private String road;
    private String near;
    private double speed;
    private String direction;
    private double km;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "CarGpsData{" +
                "carNo='" + carNo + '\'' +
                ", dateTimeString='" + dateTimeString + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", province='" + province + '\'' +
                ", road='" + road + '\'' +
                ", near='" + near + '\'' +
                ", speed=" + speed +
                ", direction='" + direction + '\'' +
                ", km=" + km +
                '}';
    }
}
