package com.tistory.fasdgoc.mynotego.util;

import com.manolovn.colorbrewer.ColorBrewer;
import com.manolovn.trianglify.generator.color.ColorGenerator;

/**
 * Created by fasdg on 2016-10-23.
 */

public class BrewColorGenerator implements ColorGenerator {
    private ColorBrewer colorBrewer;
    private int index = 0;
    private int[] colors;

    public BrewColorGenerator(ColorBrewer colorBrewer) {
        this.colorBrewer = colorBrewer;
    }

    public void setCount(int count) {
        colors = colorBrewer.getColorPalette(count);
        index = 0;
    }

    @Override
    public int nextColor() {
        int color = colors[index];
        index++;

        return color;
    }
}
