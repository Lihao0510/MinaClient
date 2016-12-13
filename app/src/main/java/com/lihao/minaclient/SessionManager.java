package com.lihao.minaclient;

import org.apache.mina.core.session.IoSession;

/**
 * Created by lihao on 2016/12/13.
 */

public class SessionManager {

    private static SessionManager instance = null;

    private IoSession mSession;

    public static SessionManager getInstance(){
        if(instance == null){
            synchronized (SessionManager.class){
                if(instance == null){
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    private SessionManager(){

    }

    public void setSession(IoSession session){
        mSession = session;
    }

    public void writeToServer(Object msg){
        if(mSession!= null){
            mSession.write(msg);
        }
    }

    public void closeSession(){
        if(mSession!= null){
            mSession.closeOnFlush();
        }
    }

    public void removeSession(){
        mSession = null;
    }
}
