package net.codersgarage.iseeu.views;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.codersgarage.iseeu.networks.ISeeUClient;
import net.codersgarage.iseeu.utils.Utils;

import java.io.IOException;

/**
 * Created by s4kib on 6/14/16.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private Camera camera;
    private SurfaceHolder surfaceHolder;

    private ISeeUClient iSeeUClient;

    public CameraView(Context context, Camera camera) {
        super(context);

        this.camera = camera;
        this.camera.setDisplayOrientation(90);
        this.camera.getParameters().setPreviewFormat(ImageFormat.RGB_565);

        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        new StreamTask().execute();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.unlock();
            camera.reconnect();
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(this);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null)
            return;

        try {
            camera.stopPreview();
        } catch (Exception e) {

        }

        try {
            camera.unlock();
            camera.reconnect();
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(this);
            camera.startPreview();
        } catch (IOException e) {

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d("Byte Received := ", "" + data.length);

        data = Utils.frameByteToJpegByte(data, camera);
        if (data != null) {
            iSeeUClient.send(data);
        }
    }

    public class StreamTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            iSeeUClient = new ISeeUClient();
            iSeeUClient.init();
            return null;
        }
    }
}
