package net.codersgarage.iseeu.models;

/**
 * Created by s4kib on 6/16/16.
 */

public class Settings {
    private String host;
    private int port;
    private boolean autoLogin;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }
}
