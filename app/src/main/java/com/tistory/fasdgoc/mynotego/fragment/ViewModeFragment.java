package com.tistory.fasdgoc.mynotego.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.CameraPermissionGranted;
import com.tistory.fasdgoc.mynotego.helper.ViewModeHelper;
import com.tistory.fasdgoc.mynotego.util.ModeFragmentProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fasdg on 2016-10-23.
 */

public class ViewModeFragment extends Fragment {
    private ModeFragmentProvider mProvider;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    private int current_mode;

    @BindView(R.id.mode_button)
    ImageButton mModeButton;

    @OnClick(R.id.mode_button)
    public void changeMode() {

        if (current_mode == ViewModeHelper.CAMERA) {

            current_mode = ViewModeHelper.MAP;
            fragment = mProvider.provideFragment(ViewModeHelper.MAP);
            fragmentManager.beginTransaction()
                    .replace(R.id.mode, fragment)
                    .commit();
            mModeButton.setImageResource(R.drawable.camera);
        } else {

            current_mode = ViewModeHelper.CAMERA;
            fragment = mProvider.provideFragment(ViewModeHelper.CAMERA);
            fragmentManager.beginTransaction()
                    .replace(R.id.mode, fragment)
                    .commit();

            mModeButton.setImageResource(R.drawable.map);
        }

        YoYo.with(Techniques.RubberBand)
                .duration(500)
                .playOn(mModeButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewmodefragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();

        current_mode = ViewModeHelper.MAP;
        fragment = mProvider.provideFragment(ViewModeHelper.MAP);
        fragmentManager.beginTransaction()
                .add(R.id.mode, fragment)
                .commit();

        mModeButton.setImageResource(R.drawable.camera);
    }

    private void initialize() {
        this.fragmentManager = getFragmentManager();

        mProvider = new ModeFragmentProvider(getActivity());
    }

    @Subscribe
    public void OnCameraPermissionGranted(CameraPermissionGranted permissionGranted) {
        current_mode = ViewModeHelper.MAP;
        changeMode();
    }
}
