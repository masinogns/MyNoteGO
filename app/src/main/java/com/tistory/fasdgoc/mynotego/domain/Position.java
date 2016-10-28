package com.tistory.fasdgoc.mynotego.domain;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by fasdg on 2016-10-28.
 */

public class Position implements Serializable {
    private double latitude;
    private double longitude;
    private double altitude;

    public Position() {
    }

    public Position(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
