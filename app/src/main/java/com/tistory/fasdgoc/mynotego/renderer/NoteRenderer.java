package com.tistory.fasdgoc.mynotego.renderer;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.tistory.fasdgoc.mynotego.R;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * Created by fasdg on 2016-10-29.
 */

public class NoteRenderer extends RajawaliRenderer {
    private Context context;

    private DirectionalLight mDirectionalLight;
    private Object3D mObjectGroup;

    public NoteRenderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60.f);
    }

    @Override
    protected void initScene() {
        mDirectionalLight = new DirectionalLight(1f, .2f, -1.0f);
        mDirectionalLight.setColor(1.0f, 1.0f, 1.0f);
        mDirectionalLight.setPower(2);
        mDirectionalLight.setPosition(0, 0, 2);
        getCurrentScene().addLight(mDirectionalLight);

        LoaderOBJ objParser = new LoaderOBJ(
                mContext.getResources(),
                mTextureManager,
                R.raw.ccc_obj);

        try {
            objParser.parse();
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        mObjectGroup = objParser.getParsedObject();
        mObjectGroup.setPosition(0, 0, 0);
        mObjectGroup.setScale(0.3f, 0.3f, 0.3f);

        getCurrentScene().addChild(mObjectGroup);

    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mObjectGroup.rotate(Vector3.Axis.Y, 1.0);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        Toast.makeText(context.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }
}
