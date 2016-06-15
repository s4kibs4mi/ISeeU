package net.codersgarage.iseeu.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;

import net.codersgarage.iseeu.models.BusAction;
import net.codersgarage.iseeu.views.CameraViewService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PersistenceStreamService extends Service {
    private CameraViewService cameraViewService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(getClass().getCanonicalName(), "onStartCommand");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        cameraViewService = new CameraViewService(getApplicationContext());
        return START_NOT_STICKY;
    }

    @Subscribe
    public void onBusAction(BusAction busAction) {
        if (!busAction.isStart()) {
            cameraViewService.doOnStop();
            EventBus.getDefault().post(new BusAction(true));
            EventBus.getDefault().unregister(this);
            onDestroy();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
