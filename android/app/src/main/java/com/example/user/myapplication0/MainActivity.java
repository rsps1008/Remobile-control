package com.example.user.myapplication0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gcssloop.widget.RockerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private Handler mMainHandler;
    boolean check=false;
    private Socket socket;
    private ExecutorService mThreadPool;
    InputStream is;
    InputStreamReader isr ;
    BufferedReader br ;
    String response;
    OutputStream outputStream;
    private Button btnConnect, btnDisconnect, btnSend;
    private TextView Receive,receive_message;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button) findViewById(R.id.connect);
        btnDisconnect = (Button) findViewById(R.id.disconnect);
        btnSend = (Button) findViewById(R.id.send);
        mEdit = (EditText) findViewById(R.id.edit);
        //receive_message = (TextView) findViewById(R.id.receive_message);
        //Receive = (Button) findViewById(R.id.Receive);
        mThreadPool = Executors.newCachedThreadPool();
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /*switch (msg.what) {
                    case 0:
                        receive_message.setText(response);
                        break;
                }*/
            }
        };

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket = new Socket("140.117.169.145", 54321);
                            //socket = new Socket("218.161.105.93", 54321);
                            ((ApplicationUtil)getApplication()).setSocket(socket) ;
                            //System.out.println(socket.isConnected());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream = socket.getOutputStream();
                            outputStream.write("android".getBytes("utf-8"));
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        });

       /*Receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            is = socket.getInputStream();
                            isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            response = br.readLine();
                            if (response == "1"){
                                check = true;
                            }
                            Message msg = Message.obtain();
                            msg.what = 0;
                            mMainHandler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });*/

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            outputStream = socket.getOutputStream();
                            outputStream.write((mEdit.getText().toString()).getBytes("utf-8"));
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            is = socket.getInputStream();
                            isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            response = br.readLine();
                            System.out.println(response);
                            if (Integer.parseInt(response) == 1){
                                check = true;
                            }
                            System.out.println(check);
                            Message msg = Message.obtain();
                            msg.what = 0;
                            mMainHandler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(check == true){
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, aa.class);
                            startActivity(intent);
                            check = false;
                        }
                    }
                });
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputStream.close();
                    br.close();
                    socket.close();
                    System.out.println(socket.isConnected());
                    android.os.Process.killProcess(android.os.Process.myPid());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
