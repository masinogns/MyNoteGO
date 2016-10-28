package com.tistory.fasdgoc.mynotego.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tistory.fasdgoc.mynotego.MainActivity;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.MoveMap;
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

    private SupportMapFragment mMapFragment;
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

        boolean enabled = locMan.checkService();
        if (!enabled) {
            return;
        }

        boolean grantedPermission = locMan.requestPermission();
        if (grantedPermission) {
            boolean ret = locMan.newProviderUpdate();
            if (ret == true) {
                Toast.makeText(getActivity(), "GPS 신호를 기다리는 중...", Toast.LENGTH_SHORT).show();
                return;
            }

            Location currentLocation = locMan.getCurrentLocation();
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            moveMapCamera(new MoveMap(latLng));
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View root = layoutInflater.inflate(R.layout.mymapfragment, viewGroup, false);
        ButterKnife.bind(this, root);

        SupportMapFragment mapFragment = new SupportMapFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.map, mapFragment)
                .commit();

        mMapFragment = mapFragment;

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        locMan = ((MainActivity)getActivity()).getmMyLocationManager();

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
                    moveMarker(new MoveMarker());
                }

                moveMapCamera(new MoveMap(latLng));
            }
        });
    }

    @Subscribe
    public void moveMapCamera(MoveMap moveMap) {
        LatLng latLng = moveMap.latLng;
        CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Subscribe
    public void moveMarker(MoveMarker moveMarker) {
        if(mMyMarker != null) {
            mMyMarker.remove();
        }

        LatLng latLng = new LatLng(
                locMan.getCurrentLocation().getLatitude(),
                locMan.getCurrentLocation().getLongitude()
        );

        mMyMarker = mGoogleMap.addMarker(
                new MarkerOptions()
                        .position(latLng));
    }
}
