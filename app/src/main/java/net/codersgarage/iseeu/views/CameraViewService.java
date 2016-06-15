package net.codersgarage.iseeu.views;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.codersgarage.iseeu.utils.Utils;

/**
 * Created by s4kib on 6/14/16.
 */

public class CameraViewService extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private String SCOPE_TAG = getClass().getCanonicalName();

    private SurfaceTexture surfaceTexture;

    private Context context;

    public CameraViewService(Context context) {
        super(context);

        this.context = context;
        init();
    }

    private void init() {
        Log.d(SCOPE_TAG + " onInit", "Called");

        surfaceTexture = new SurfaceTexture(10);

        doOnStart();
    }

    private void doOnStart() {
        try {
            MainActivity.camera.setPreviewTexture(surfaceTexture);
            MainActivity.camera.setPreviewCallback(this);
            MainActivity.camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnStop() {
        getHolder().removeCallback(this);
        MainActivity.camera.stopPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(SCOPE_TAG + " onCreated", "Called");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(SCOPE_TAG + " onChanged", "Called");

        doOnStart();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(SCOPE_TAG + " onDestroyed", "Called");
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d(SCOPE_TAG + " Byte Received := ", "" + data.length);

        data = Utils.frameByteToJpegByte(data, camera);
        if (data != null) {
            MainActivity.iSeeUClient.send(data);
        }
    }
}
