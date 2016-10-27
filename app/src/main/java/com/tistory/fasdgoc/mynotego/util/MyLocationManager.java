package com.tistory.fasdgoc.mynotego.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.MoveMarker;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fasdg on 2016-10-28.
 */

public class MyLocationManager {
    private final static String TAG = "MyLocationManager";

    public final static int LOCATION = 9000;
    public final static int SETTING = 9001;

    private Activity activity;

    private LocationManager locMan;
    private String locProvider;
    private LocationListener locListener;

    private Location currentLocation;

    private boolean grantedPermission = false;

    public MyLocationManager(Activity activity) {
        this.activity = activity;

        locMan = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locProvider = locMan.getBestProvider(new Criteria(), true);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                EventBus.getDefault().post(new MoveMarker());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean requestPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            grantedPermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("위치 권한 요청")
                        .setContentText("사용자의 위치를\n추적하기 위해서\n위치권한이\n필요합니다.")
                        .setCustomImage(R.drawable.earthglobe)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyLocationManager.LOCATION);
                            }
                        })
                        .setConfirmText("확인")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setCancelText("취소")
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MyLocationManager.LOCATION);
            }
        } else {
            grantedPermission = true;
        }

        return grantedPermission;
    }

    public boolean checkService() {
        boolean retResult = false;

        boolean gps = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps && !network) {
            new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("위치 서비스 설정 요청")
                    .setContentText("앱 설정에서 위치 정보 기능을 켜주세요.")
                    .setCustomImage(R.drawable.settings)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setConfirmText("확인")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelText("취소")
                    .show();
            retResult = true;
        }

        return retResult;
    }

    public void OnRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MyLocationManager.LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                grantedPermission = true;
            } else {
                grantedPermission = false;
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) == false) {
                    new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("위치 권한 요청")
                            .setContentText("앱을 정상적으로 이용하기 위한 \n위치 권한이 필요합니다.\n앱 설정에서 위치 권한을 허용해주세요")
                            .setCustomImage(R.drawable.settings)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                    intent.setData(uri);
                                    activity.startActivityForResult(intent, MyLocationManager.SETTING);
                                }
                            })
                            .setConfirmText("확인")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .setCancelText("취소")
                            .show();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "request code : " + requestCode + ", result code : " + resultCode + ", data is " + data);
        if(requestCode == MyLocationManager.SETTING) {
            if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                grantedPermission = false;
            } else {
                grantedPermission = true;
            }
        }
    }


    public boolean newProviderUpdate() {
        boolean retResult = false;

        try {
            currentLocation = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getMessage());
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }

        if (currentLocation == null) {

            locProvider = locMan.getBestProvider(new Criteria(), true);

            try {
                locMan.requestLocationUpdates(locProvider, 5000, 5, locListener);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, e.getMessage());
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }

            retResult = true;
        }

        return retResult;
    }

    public void addUpdate() {
        if(locProvider == null) {
            locProvider = locMan.getBestProvider(new Criteria(), true);
        }

        try {
            locMan.requestLocationUpdates(locProvider, 5000, 5, locListener);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getMessage());
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void removeUpdate() {
        try {
            locMan.removeUpdates(locListener);
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}