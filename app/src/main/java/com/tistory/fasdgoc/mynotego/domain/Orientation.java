package com.tistory.fasdgoc.mynotego.domain;

/**
 * Created by fasdg on 2016-10-28.
 */

public class Orientation {
    private float azimuth;
    private float pitch;
    private float roll;

    public Orientation() {
    }

    public Orientation(float azimuth, float pitch, float roll) {
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.roll = roll;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
