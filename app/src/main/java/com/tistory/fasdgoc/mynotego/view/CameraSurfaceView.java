package com.tistory.fasdgoc.mynotego.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tistory.fasdgoc.mynotego.event.CameraFocus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * Created by fasdg on 2016-10-26.
 */

@SuppressWarnings("deprecation")
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final static String TAG = "CameraSurfaceView";

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        EventBus.getDefault().register(this);
        mCamera = Camera.open();
        try{
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            Log.d(TAG, "CameraSurfaceView error - setPreviewDisplay");

            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> arSize = parameters.getSupportedPreviewSizes();

        if(arSize == null) {
            parameters.setPreviewSize(width, height);
        } else {
            int _width = arSize.get(0).width;
            int _height = arSize.get(0).height;

            parameters.setPreviewSize(_width, _height);
        }

        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
        focusCamera(new CameraFocus());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        EventBus.getDefault().unregister(this);
        if(mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Subscribe
    public void focusCamera(CameraFocus focus) {
        if(mCamera == null) {
            return;
        }

        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                
            }
        });
    }
}
