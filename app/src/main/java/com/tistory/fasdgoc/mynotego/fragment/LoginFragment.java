package com.tistory.fasdgoc.mynotego.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tistory.fasdgoc.mynotego.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by fasdg on 2016-10-24.
 */

public class LoginFragment extends Fragment {
    private OnSignInTryListener activity;

    public interface OnSignInTryListener {
        public void onSignInTry(View v);
    }

    @Optional
    @OnClick({R.id.google_sign_in})
    public void onSignClick(View v) {
        activity.onSignInTry(v);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            activity = (OnSignInTryListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity Must implement OnSignInTryListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_page, container, false);

        ButterKnife.bind(this, root);

        return root;
    }
}
