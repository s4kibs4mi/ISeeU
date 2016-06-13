package net.codersgarage.iseeu.networks;

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
    private String HOST = "192.168.0.105";
    private int PORT = 5670;
    private DatagramSocket datagramSocket;
    private DatagramPacket packet;
    private InetAddress inetAddress;

    public void init() {
        try {
            datagramSocket = new DatagramSocket();
            inetAddress = InetAddress.getByName(HOST);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void send(final byte[] data) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                packet = new DatagramPacket(data, data.length, inetAddress, PORT);
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
