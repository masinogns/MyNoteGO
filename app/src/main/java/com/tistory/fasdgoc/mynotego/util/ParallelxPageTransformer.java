package com.tistory.fasdgoc.mynotego.util;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.tistory.fasdgoc.mynotego.R;

/**
 * Created by fasdg on 2016-10-24.
 */

public class ParallelxPageTransformer implements ViewPager.PageTransformer {
    private boolean toggle = false;
    @Override
    public void transformPage(View page, float position) {

        int pageWidth = page.getWidth();
        TextView textView = (TextView) page.findViewById(R.id.textView);
        TextView textView2 = (TextView) page.findViewById(R.id.textView2);

        // page.getId
        SignInButton button = (SignInButton) page.findViewById(R.id.google_sign_in);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(1);

        } else if (position <= 1) { // [-1,1]
            if(textView != null)
                textView.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
            if(textView2 != null)
                textView.setTranslationX((position) * (pageWidth / 4));

            if(button != null)
                button.setTranslationX((float) (-position * (pageWidth) *0.5)); //Half the normal speed

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(1);
        }


    }

    private void initialize() {

    }
}
