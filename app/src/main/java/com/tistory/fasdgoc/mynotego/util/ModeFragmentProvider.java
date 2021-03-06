package com.tistory.fasdgoc.mynotego.util;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.tistory.fasdgoc.mynotego.fragment.CameraFragment;
import com.tistory.fasdgoc.mynotego.fragment.MyMapFragment;
import com.tistory.fasdgoc.mynotego.helper.ViewModeHelper;

/**
 * Created by fasdg on 2016-10-26.
 */

public class ModeFragmentProvider {
    private Context context;

    private Fragment mapFragment = new MyMapFragment();
    private CameraFragment cameraFragment = new CameraFragment();

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
                result = cameraFragment;
                break;

            default:
                break;
        }

        return result;
    }
}
