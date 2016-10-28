package com.tistory.fasdgoc.mynotego.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.CameraFocus;
import com.tistory.fasdgoc.mynotego.view.CameraSurfaceView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fasdg on 2016-10-26.
 */

public class CameraFragment extends Fragment {

    @BindView(R.id.camera)
    CameraSurfaceView mCameraSurfaceView;

    @BindView(R.id.focus)
    ImageButton focusButton;

    @OnClick(R.id.focus)
    public void onFocusClicked() {
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .playOn(focusButton);

        EventBus.getDefault().post(new CameraFocus());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.camerafragment, container, false);

        ButterKnife.bind(this ,root);

        return root;
    }
}
