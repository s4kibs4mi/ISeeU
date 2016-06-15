package net.codersgarage.iseeu.networks;

import android.content.Context;

import net.codersgarage.iseeu.models.Settings;
import net.codersgarage.iseeu.settings.SettingsProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by s4kib on 6/14/16.
 */

public class ISeeUClient {
    private DatagramSocket datagramSocket;
    private DatagramPacket packet;
    private InetAddress inetAddress;

    private SettingsProvider settingsProvider;
    private Settings settings;

    public ISeeUClient(Context context) {
        settingsProvider = new SettingsProvider(context);
        settings = settingsProvider.getSettings();
    }

    public void init() {

        try {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName(settings.getHost());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void send(final byte[] data) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                packet = new DatagramPacket(data, data.length, inetAddress, settings.getPort());
                try {
                    datagramSocket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
