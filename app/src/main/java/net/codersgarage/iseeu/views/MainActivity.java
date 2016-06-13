package net.codersgarage.iseeu.views;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import net.codersgarage.iseeu.R;

public class MainActivity extends AppCompatActivity {
    private CameraView cameraView;
    private Camera camera;
    private FrameLayout camreaPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = Camera.open();

        if (camera != null) {
            cameraView = new CameraView(this, camera);
            camreaPreview = (FrameLayout) findViewById(R.id.camera_view);
            camreaPreview.addView(cameraView);
        }
    }
}
