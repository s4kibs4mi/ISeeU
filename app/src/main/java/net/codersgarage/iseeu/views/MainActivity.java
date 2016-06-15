package net.codersgarage.iseeu.views;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.codersgarage.iseeu.R;
import net.codersgarage.iseeu.models.BusAction;
import net.codersgarage.iseeu.models.Settings;
import net.codersgarage.iseeu.networks.ISeeUClient;
import net.codersgarage.iseeu.settings.SettingsProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CameraView cameraView;
    public static Camera camera;
    private FrameLayout cameraPreview;

    private TextView serverInfo;
    private Button stopBtn;
    private Settings settings;

    public static ISeeUClient iSeeUClient;

    public static boolean isQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("onCreated", "Called");

        try {
            getActionBar().hide();
        } catch (Exception ex) {
            try {
                getSupportActionBar().hide();
            } catch (Exception ex2) {

            }
        }

        settings = new SettingsProvider(this).getSettings();

        EventBus.getDefault().register(this);

        cameraPreview = (FrameLayout) findViewById(R.id.camera_view);
        serverInfo = (TextView) findViewById(R.id.serverInfo);
        stopBtn = (Button) findViewById(R.id.stopBtn);

        serverInfo.setText("Connected\n" + settings.getHost() + ":" + settings.getPort());

        stopBtn.setOnClickListener(this);

        if (camera == null) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
        }

        if (iSeeUClient == null) {
            new ISeeUAsyncTask().execute();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getBoolean("isBegin")) {
            Log.d("haveExtra", "Yes");
            onBusAction(new BusAction(true));
        }
    }

    @Override
    protected void onResume() {
        Log.d("onResume", "Called");

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        EventBus.getDefault().post(new BusAction(false));

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("onPause", "Called");

        EventBus.getDefault().unregister(this);

        try {
            cameraPreview.removeView(cameraView);
            cameraView.doOnStop();

            Log.d("Problem", "onIntent");
        } catch (Exception ex) {
            Log.d("SillyException", ex.getMessage());
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("onStop", "Called");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart", "Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy", "Called");
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        isQuit = true;

        cameraPreview.removeView(cameraView);
        cameraPreview = null;
        camera.stopPreview();
        camera.release();
        camera = null;

        finish();
        System.exit(0);
    }

    @Subscribe
    public void onBusAction(BusAction busAction) {
        Log.d("onBusAction", "Called");

        if (busAction.isStart()) {
            Log.d("onBusActionRe", "Called");

            camera.getParameters().setPreviewFormat(ImageFormat.RGB_565);
            cameraView = new CameraView(this);
            cameraPreview.addView(cameraView);
        }
    }

    public class ISeeUAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            iSeeUClient = new ISeeUClient(getApplicationContext());
            iSeeUClient.init();
            return null;
        }
    }
}
