package com.tistory.fasdgoc.mynotego.util;

import android.graphics.PorterDuff;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tistory.fasdgoc.mynotego.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by fasdg on 2016-10-24.
 */

public class ParallelxPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        switch (page.getId()) {
            case R.id.intro1:
                TextView title = (TextView) page.findViewById(R.id.title);
                title.setTranslationX((float) ((position) * (pageWidth / 0.8)));

                TextView description = (TextView)page.findViewById(R.id.description);
                description.setTranslationX((float) ((position) * (pageWidth / 4)));

                ImageView bottle = (ImageView)page.findViewById(R.id.bottle);
                bottle.setTranslationX((float) ((position) * (pageWidth / 0.1)));

                ImageView message = (ImageView)page.findViewById(R.id.message);
                message.setTranslationX((float) ((position) * (pageWidth / 0.9)));
                break;
        }



    }
}
