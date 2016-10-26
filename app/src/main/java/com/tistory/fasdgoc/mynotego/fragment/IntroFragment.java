package com.tistory.fasdgoc.mynotego.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fasdg on 2016-10-24.
 */

public class IntroFragment extends Fragment {
    private final static String TAG = "IntroFragment";

    private Integer resId;
    public void setResId(Integer resId) {
        this.resId = resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(resId == null) {
            Log.d(TAG, "###You have to set Resource id using setter###");
            return null;
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(resId, container, false);

        return rootView;
    }
}
