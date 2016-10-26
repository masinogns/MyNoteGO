package com.tistory.fasdgoc.mynotego.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tistory.fasdgoc.mynotego.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fasdg on 2016-10-24.
 */

public class MyMapFragment extends Fragment {
    private final String TAG = "MyMapFragment";

    private final int LOCATION_PERM = 8000;

    private boolean grantedPermission = false;

    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private Marker mMyMarker;

    private LocationManager mLocManager;
    private String mLocProvider;
    private LocationListener mLocListener;
    private Location currentLocation;

    @BindView(R.id.mylocation)
    ImageButton mMyLocButton;

    @OnClick(R.id.mylocation)
    public void onClick() {
        YoYo.with(Techniques.Flash)
                .duration(700)
                .playOn(mMyLocButton);

        if (mGoogleMap == null) {
            Toast.makeText(getActivity(), "지도를 불러올 때까지 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean gps = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = mLocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!gps && !network) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("위치 서비스 설정 요청")
                    .setContentText("앱 설정에서 위치 정보 기능을 켜주세요.")
                    .setCustomImage(R.drawable.settings)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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
            return;
        }

        requestCurrentLocation();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View root = layoutInflater.inflate(R.layout.mymapfragment, viewGroup, false);
        ButterKnife.bind(this, root);

        MapFragment mapFragment = new MapFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.map, mapFragment)
                .commit();

        mMapFragment = mapFragment;

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocProvider = mLocManager.getBestProvider(new Criteria(), true);
        mLocListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (currentLocation != null) {
                    mMyMarker.remove();
                }

                currentLocation = location;
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMyMarker = mGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng));
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

        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;

                LatLng latLng;
                if (currentLocation == null) {
                    latLng = new LatLng(33.4549053, 126.5600566); // Cheju University
                } else {
                    latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMyMarker = mGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(latLng));
                }

                moveMapCamera(latLng);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            mLocManager.removeUpdates(mLocListener);
        } catch (IllegalArgumentException e) {

        } catch (SecurityException e) {

        }
    }

    public boolean requestCurrentLocation() {
        if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) &&
                (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("위치 권한 요청")
                        .setContentText("사용자의 위치를\n추적하기 위해서\n위치권한이\n필요합니다.")
                        .setCustomImage(R.drawable.earthglobe)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                requestPermission();
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
                requestPermission();
            }
        } else {
            grantedPermission = true;
            moveMaptoCurrentLocation();
        }

        return grantedPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                grantedPermission = true;
                moveMaptoCurrentLocation();
            } else {
                if(FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) == false){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("위치 권한 요청")
                            .setContentText("앱을 정상적으로 이용하기 위한 \n위치 권한이 필요합니다.\n앱 설정에서 위치 권한을 허용해주세요")
                            .setCustomImage(R.drawable.settings)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
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

    private void requestPermission() {
        FragmentCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERM);
    }

    private void moveMaptoCurrentLocation() {
        if (currentLocation == null) {
            Toast.makeText(getActivity(), "GPS 신호를 기다리는 중...", Toast.LENGTH_SHORT).show();
            mLocProvider = mLocManager.getBestProvider(new Criteria(), true);
            try {
                mLocManager.requestLocationUpdates(mLocProvider, 5000, 5, mLocListener);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, e.getMessage());
            } catch (SecurityException e) {
                Log.d(TAG, e.getMessage());
            }

            return;
        }

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        moveMapCamera(latLng);
    }

    private void moveMapCamera(LatLng latLng) {
        CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
