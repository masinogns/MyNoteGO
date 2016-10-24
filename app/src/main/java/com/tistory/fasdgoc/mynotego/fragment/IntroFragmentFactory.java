package com.tistory.fasdgoc.mynotego.fragment;

import android.util.Log;

/**
 * Created by fasdg on 2016-10-24.
 */

public class IntroFragmentFactory {
    private final static String TAG = "IntroFragmentFactory";
    private static IntroFragmentFactory instance = new IntroFragmentFactory();

    public static IntroFragmentFactory getInstance() {
        return instance;
    }

    public IntroFragment generateFragment(int resId) {
        IntroFragment._throughFactory = true;

        IntroFragment introFragment = null;
        try {
            introFragment = new IntroFragment();
        } catch (Exception e) {
            Log.e(TAG, "You have to use the FragmentFactory");
            e.printStackTrace();
        }

        introFragment.setResId(resId);
        return introFragment;
    }

    private IntroFragmentFactory() {}
}
