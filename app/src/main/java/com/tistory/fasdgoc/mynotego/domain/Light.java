package com.tistory.fasdgoc.mynotego.domain;

/**
 * Created by fasdg on 2016-10-28.
 */

public class Light {
    private float intensity;

    public Light() {
    }

    public Light(float intensity) {
        this.intensity = intensity;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
