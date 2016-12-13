package com.lihao.minaclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/**
 * Created by lihao on 2016/12/12.
 */

public class ConnectionManager {

    private static final String BROADCAST_ACTION = "com.lihao.minaclient";
    private static final String MESSAGE = "mina_message";
    private ConnectionConfig config;
    private WeakReference<Context> mContext;
    private NioSocketConnector mConnector;
    private IoSession mSession;
    private InetSocketAddress socketAddress;

    public ConnectionManager(ConnectionConfig config){
        this.config = config;
        this.mContext = new WeakReference<Context>(config.getContext());
        init();
    }

    private void init() {
        socketAddress = new InetSocketAddress(config.getIp(),config.getPort());
        mConnector = new NioSocketConnector();
        mConnector.getFilterChain().addLast("logging", new LoggingFilter());
        mConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mConnector.setHandler(new MySocketHandler(mContext.get()));
    }

    private static class MySocketHandler extends IoHandlerAdapter{

        private Context mContext;
        MySocketHandler(Context context){
            mContext = context;
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            super.sessionClosed(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            if (mContext != null){
                Intent intent = new Intent(BROADCAST_ACTION);
                intent.putExtra(MESSAGE, message.toString());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception {
            super.messageSent(session, message);
        }

    }

    public boolean connect(){
        ConnectFuture future = mConnector.connect();
        future.awaitUninterruptibly();
        mSession = future.getSession();

        return mSession == null ? false : true;
    }

    public void disConnection(){

    }
}
