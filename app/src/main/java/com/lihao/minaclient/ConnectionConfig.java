package com.lihao.minaclient;

import android.content.Context;

/**
 * Created by lihao on 2016/12/13.
 */

public class ConnectionConfig {

    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeout;

    @Override
    public String toString() {
        return "ConnectionConfig{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", readBufferSize=" + readBufferSize +
                '}';
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setContext(Context context) {

        this.context = context;
    }

    public Context getContext() {

        return context;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public static class Builder{

        private Context context;
        private String ip = "192.168.1.107";
        private int port = 3721;
        private int readBufferSize = 4096;
        private long connectionTimeout = 10000;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setIp(String ip){
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port){
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize){
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setTimeout(long timeout){
            this.connectionTimeout = timeout;
            return this;
        }

        public void applyConfig(ConnectionConfig config){
            config.context = this.context;
            config.ip = this.ip;
            config.port = this.port;
            config.readBufferSize = this.readBufferSize;
            config.connectionTimeout = this.connectionTimeout;
        }

        public ConnectionConfig build(){
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }
    }
}
