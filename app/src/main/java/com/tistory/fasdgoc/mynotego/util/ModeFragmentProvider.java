package com.tistory.fasdgoc.mynotego.util;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.tistory.fasdgoc.mynotego.fragment.CameraFragment;
import com.tistory.fasdgoc.mynotego.fragment.MyMapFragment;
import com.tistory.fasdgoc.mynotego.fragment.PlaceHolderFragment;
import com.tistory.fasdgoc.mynotego.helper.ViewModeHelper;

/**
 * Created by fasdg on 2016-10-26.
 */

public class ModeFragmentProvider {
    private Context context;

    private MyMapFragment mapFragment = new MyMapFragment();

    private CameraFragment cameraFragment = new CameraFragment();
    private PlaceHolderFragment placeHolderFragment = new PlaceHolderFragment();

    public ModeFragmentProvider(Context context) {
        this.context = context;
    }

    public Fragment provideFragment(int mode) {

        Fragment result = null;

        switch (mode) {
            case ViewModeHelper.MAP:
                result = mapFragment;
                break;

            case ViewModeHelper.CAMERA:
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    result = placeHolderFragment;
                } else {
                    result = cameraFragment;
                }

                break;

            default:
                break;
        }

        return result;
    }
}
