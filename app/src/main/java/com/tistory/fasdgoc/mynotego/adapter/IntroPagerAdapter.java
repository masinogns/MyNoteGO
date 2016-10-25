package com.tistory.fasdgoc.mynotego.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.fragment.IntroFragmentFactory;
import com.tistory.fasdgoc.mynotego.fragment.LoginFragment;

import java.util.ArrayList;

/**
 * Created by fasdg on 2016-10-24.
 */

public class IntroPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> array;

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);

        array = new ArrayList<>();

        IntroFragmentFactory introFactory = IntroFragmentFactory.getInstance();

        array.add(introFactory.generateFragment(R.layout.intro_page1));
        array.add(introFactory.generateFragment(R.layout.intro_page1));
        array.add(introFactory.generateFragment(R.layout.intro_page1));
        array.add(introFactory.generateFragment(R.layout.intro_page1));
        array.add(new LoginFragment());
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("MY", "Position is " + position);
        return array.get(position);
    }

    @Override
    public int getCount() {
        return array.size();
    }
}
