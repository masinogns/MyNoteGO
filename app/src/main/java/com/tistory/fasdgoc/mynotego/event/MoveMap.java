package com.tistory.fasdgoc.mynotego.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by fasdg on 2016-10-28.
 */

public class MoveMap {
    public LatLng latLng;

    public MoveMap(LatLng latLng) {
        this.latLng = latLng;
    }
}
