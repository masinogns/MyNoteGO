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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fasdg on 2016-10-23.
 */

public class ViewModeFragment extends Fragment {
    private static enum VIEW_MODE {
        CAMERA, MAP
    }
    private VIEW_MODE current_mode;

    @BindView(R.id.mode_button)
    ImageButton mModeButton;

    @OnClick(R.id.mode_button)
    public void changeMode() {

        FragmentManager fragmentManager = getFragmentManager();

        YoYo.with(Techniques.RubberBand)
                .duration(500)
                .playOn(mModeButton);

        if(current_mode == VIEW_MODE.CAMERA) {

            mModeButton.setImageResource(R.drawable.map);
            current_mode = VIEW_MODE.MAP;

        } else {

            mModeButton.setImageResource(R.drawable.camera);
            current_mode = VIEW_MODE.CAMERA;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_mode = VIEW_MODE.CAMERA;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewmodefragment, container, false);
        ButterKnife.bind(this, root);

        mModeButton.setImageResource(R.drawable.camera);

        return root;
    }
}
