package com.tistory.fasdgoc.mynotego.listener;

import android.animation.ArgbEvaluator;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.tistory.fasdgoc.mynotego.R;

import java.util.ArrayList;

/**
 * Created by fasdg on 2016-10-25.
 */

public class ArgbPageChangeListener implements ViewPager.OnPageChangeListener {
    private ArgbEvaluator mArgbEvaluator;
    private ArrayList<Integer> colors;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    public ArgbPageChangeListener(Resources resource, PagerAdapter pagerAdapter, ViewPager viewPager) {
        colors = new ArrayList<>();
        colors.add(resource.getColor(R.color.color1));
        colors.add(resource.getColor(R.color.color2));
        colors.add(resource.getColor(R.color.color3));
        colors.add(resource.getColor(R.color.color4));
        colors.add(resource.getColor(R.color.color5));
        mArgbEvaluator = new ArgbEvaluator();
        mPagerAdapter = pagerAdapter;
        mViewPager = viewPager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position < (mPagerAdapter.getCount() -1) && position < (colors.size() - 1)) {
            mViewPager.setBackgroundColor((Integer) mArgbEvaluator.evaluate(positionOffset, colors.get(position), colors.get(position + 1)));
        } else {
            mViewPager.setBackgroundColor(colors.get(colors.size() - 1));
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
