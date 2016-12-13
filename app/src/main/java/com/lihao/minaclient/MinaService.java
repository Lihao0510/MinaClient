package com.lihao.minaclient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by lihao on 2016/12/13.
 */

public class MinaService extends Service {

    private  ConnectionThread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ConnectionThread("mina",getApplicationContext());
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    class ConnectionThread extends HandlerThread{

        Context context;
        boolean isConnect;
        ConnectionManager manager;

        ConnectionThread(String name,Context context){
            super(name);
            this.context = context;
            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setIp("192.168.1.107")
                    .setPort(8080)
                    .setTimeout(10000)
                    .setReadBufferSize(4096)
                    .build();
            manager = new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {
            while (true){
                isConnect = manager.connect();
                if(isConnect){
                    break;
                }
                try{
                    Thread.sleep(3000);
                }catch (Exception e){

                }
            }
        }

        public void disconnect(){
            manager.disConnection();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disconnect();
        thread = null;
    }
}
