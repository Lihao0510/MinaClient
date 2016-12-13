package com.lihao.minaclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView recText;
    private TextView sendText;
    private Button sendButton;
    private Button startService;
    private MyMessageReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBroadcast();
    }

    private void initBroadcast() {
        receiver = new MyMessageReceiver();
        IntentFilter filter = new IntentFilter("com.lihao.minaclient");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    private void initView() {
        recText = (TextView) findViewById(R.id.tv_rec);
        sendText = (TextView) findViewById(R.id.tv_send);
        sendButton = (Button) findViewById(R.id.bt_send);
        startService = (Button) findViewById(R.id.bt_start);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.getInstance().writeToServer("Hello Server");
            }
        });
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MinaService.class);
                startService(intent);
            }
        });
    }

    private class MyMessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            recText.setText(intent.getStringExtra("message"));
        }
    }
}
