package com.tistory.fasdgoc.mynotego.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fasdg on 2016-10-24.
 */

public class IntroFragment extends Fragment {
    public static boolean _throughFactory = false;
    private Integer resId;
    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public IntroFragment() throws Exception {
        super();

        // Force to use the factory
        if(IntroFragment._throughFactory == false) {
            throw new Exception("You have to use the IntroFragmentFactory");
        } else {
            IntroFragment._throughFactory = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(resId == null) {
            return null;
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(resId, container, false);

        return rootView;
    }
}
