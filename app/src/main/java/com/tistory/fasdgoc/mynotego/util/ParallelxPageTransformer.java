package com.tistory.fasdgoc.mynotego.util;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tistory.fasdgoc.mynotego.R;

/**
 * Created by fasdg on 2016-10-24.
 */

public class ParallelxPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        switch (page.getId()) {
            case R.id.intro1: {
                TextView title = (TextView) page.findViewById(R.id.title);
                title.setTranslationX((float) ((position) * (pageWidth / 0.8)));

                TextView description = (TextView) page.findViewById(R.id.description);
                description.setTranslationX((float) ((position) * (pageWidth / 4)));

                ImageView bottle = (ImageView) page.findViewById(R.id.bottle);
                bottle.setTranslationX((float) ((position) * (pageWidth / 0.1)));

                ImageView message = (ImageView) page.findViewById(R.id.message);
                message.setTranslationX((float) ((position) * (pageWidth / 0.9)));
                break;
            }
            case R.id.intro2: {
                TextView title = (TextView) page.findViewById(R.id.title);
                title.setTranslationX((float) ((position) * (pageWidth / 0.2)));

                ImageView envelope = (ImageView)page.findViewById(R.id.envelope);
                envelope.setTranslationX((float) ((position) * (pageWidth / 0.8)));

                ImageView plus = (ImageView)page.findViewById(R.id.plus);
                plus.setTranslationX((float) ((-position) * (pageWidth / 0.5)));

                ImageView smartphone = (ImageView)page.findViewById(R.id.smartphone);
                smartphone.setTranslationX((float) ((position) * (pageWidth / 2)));
                break;
            }
            case R.id.intro3: {
                TextView title = (TextView) page.findViewById(R.id.title);
                title.setTranslationX((float) ((position) * (pageWidth / 0.5)));

                TextView description = (TextView) page.findViewById(R.id.description);
                description.setTranslationX((float) ((position) * (pageWidth / 2)));

                ImageView envelope = (ImageView)page.findViewById(R.id.envelope);
                envelope.setTranslationX((float) ((position) * (pageWidth / 0.1)));

                ImageView arrowright = (ImageView)page.findViewById(R.id.arrowright);
                arrowright.setTranslationX((float) ((position) * (pageWidth / 0.9)));

                ImageView arrowleft = (ImageView)page.findViewById(R.id.arrowleft);
                arrowleft.setTranslationX((float) ((position) * (pageWidth / 0.8)));
                break;
            }
            case R.id.intro4: {
                TextView description = (TextView) page.findViewById(R.id.description);
                description.setTranslationX((float) ((position) * (pageWidth / 0.6)));

                int pageHeight = page.getHeight();
                ImageView phonemap = (ImageView)page.findViewById(R.id.phonemap);
                phonemap.setTranslationY((float) ((-position) * (pageHeight / 0.2)));
                break;
            }
        }
    }
}
