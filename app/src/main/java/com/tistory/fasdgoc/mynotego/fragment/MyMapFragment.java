package com.tistory.fasdgoc.mynotego.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Location;
import android.os.Bundle;
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
import com.tistory.fasdgoc.mynotego.MainActivity;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.MoveMarker;
import com.tistory.fasdgoc.mynotego.util.MyLocationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fasdg on 2016-10-24.
 */

public class MyMapFragment extends Fragment {
    private final String TAG = "MyMapFragment";

    private final int LOCATION_PERM = 8000;

    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private Marker mMyMarker;

    private MyLocationManager locMan;

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

        boolean immediateRet = locMan.checkService();
        if (immediateRet) {
            return;
        }

        boolean grantedPermission = locMan.requestPermission();
        if (grantedPermission) {
            moveMaptoCurrentLocation();
        }
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        locMan = ((MainActivity) getActivity()).getmMyLocationManager();

        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;

                Location currentLocation = locMan.getCurrentLocation();
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

    private void moveMaptoCurrentLocation() {

        boolean immediateRet = locMan.newProviderUpdate();
        if (immediateRet == true) {
            Toast.makeText(getActivity(), "GPS 신호를 기다리는 중...", Toast.LENGTH_SHORT).show();
            return;
        }

        Location currentLocation = locMan.getCurrentLocation();
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        moveMapCamera(latLng);
    }

    private void moveMapCamera(LatLng latLng) {
        CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Subscribe
    public void moveMarker(MoveMarker moveMarker) {
        mMyMarker.remove();

        LatLng latLng = new LatLng(locMan.getCurrentLocation().getLatitude(),
                locMan.getCurrentLocation().getLongitude());
        mMyMarker = mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(latLng));
    }
}
