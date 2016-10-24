package com.tistory.fasdgoc.mynotego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.manolovn.colorbrewer.ColorBrewer;
import com.manolovn.trianglify.TrianglifyView;
import com.manolovn.trianglify.generator.point.RegularPointGenerator;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.util.BrewColorGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "SplashActivity";

    @BindView(R.id.trianglify)
    TrianglifyView trianglifyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        trianglifyView.setDrawingCacheEnabled(false);
        trianglifyView.getDrawable().setCellSize(200);
        trianglifyView.getDrawable().setVariance(50);
        trianglifyView.getDrawable().setColorGenerator(new BrewColorGenerator(ColorBrewer.YlOrRd));
        trianglifyView.getDrawable().setPointGenerator(new RegularPointGenerator());
        trianglifyView.setDrawingCacheEnabled(true);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 1000);
    }
}
