package com.tistory.fasdgoc.mynotego.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.tistory.fasdgoc.mynotego.domain.Light;
import com.tistory.fasdgoc.mynotego.domain.Orientation;

/**
 * Created by fasdg on 2016-10-28.
 */

public class MySensorManager implements SensorEventListener {
    private SensorManager sensorManager;

    private Orientation currentOrient;
    private Light currentLight;

    public MySensorManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        currentOrient = new Orientation();
        currentLight = new Light();
    }

    @SuppressWarnings("deprecation")
    public void register() {
        Sensor sensor = null;

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onSensorChanged(SensorEvent event) {
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }

        float[] values = event.values;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                currentOrient.setAzimuth(values[0]);
                currentOrient.setPitch(values[1]);
                currentOrient.setRoll(values[2]);
                break;

            case Sensor.TYPE_LIGHT:
                currentLight.setIntensity(values[0]);
                break;

            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
