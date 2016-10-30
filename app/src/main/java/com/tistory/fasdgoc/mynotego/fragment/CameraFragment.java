package com.tistory.fasdgoc.mynotego.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.CameraFocus;
import com.tistory.fasdgoc.mynotego.renderer.NoteRenderer;
import com.tistory.fasdgoc.mynotego.view.CameraSurfaceView;

import org.greenrobot.eventbus.EventBus;
import org.rajawali3d.surface.RajawaliSurfaceView;

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

    @BindView(R.id.noteView)
    RajawaliSurfaceView noteSurfaceView;
    private NoteRenderer renderer;

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

        focusButton.bringToFront();
        container.requestLayout();
        container.invalidate();

        noteSurfaceView.setTransparent(true);

        renderer = new NoteRenderer(getActivity());
        noteSurfaceView.setSurfaceRenderer(renderer);
        noteSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ClickedView", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
