package com.example.user.myapplication0;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.gcssloop.widget.RockerView;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class keyboard extends AppCompatActivity {
    private Button btnSend, btnBack, buttonL, buttonR;
    private EditText mEdit;
    OutputStream outputStream;
    private Handler mMainHandler;
    private ExecutorService mThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        final Socket socket = ((ApplicationUtil)getApplication()).getSocket();
        mThreadPool = Executors.newCachedThreadPool();
        mMainHandler = new Handler() {
            public void handleMessage() {
            }
        };

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
            try {
                outputStream = socket.getOutputStream();
                outputStream.write("MouseInputS".getBytes("utf-8"));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });

        btnSend = (Button) findViewById(R.id.send);
        btnBack = (Button) findViewById(R.id.back);
        buttonL =(Button) findViewById(R.id.buttonL);
        buttonR=(Button) findViewById(R.id.buttonR);
        mEdit = (EditText) findViewById(R.id.edit);

        btnSend.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               mThreadPool.execute(new Runnable() {
                   @Override
                   public void run() {
                   try {
                       outputStream = socket.getOutputStream();
                       outputStream.write("KeyboardInput".getBytes("utf-8"));
                       outputStream.write((mEdit.getText().toString()).getBytes("utf-8"));
                       outputStream.flush();

                   } catch (IOException e) {
                       System.out.println("4");
                       e.printStackTrace();
                   }
                   }
               });
            }
        });

        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outputStream = socket.getOutputStream();
                            outputStream.write("buttonL".getBytes("utf-8"));
                            outputStream.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outputStream = socket.getOutputStream();
                            outputStream.write("buttonR".getBytes("utf-8"));
                            outputStream.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            outputStream = socket.getOutputStream();
                            outputStream.write("EndofKeyboard".getBytes("utf-8"));
                            outputStream.flush();
                        } catch (IOException e) {
                            System.out.println("4");
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setClass(keyboard.this, aa.class);
                        startActivity(intent);
                    }
                });
            }
        });

        try{
            RockerView rocker = (RockerView) findViewById(R.id.rocker);
            if (null != rocker){
                rocker.setListener(new RockerView.RockerListener() {
                    @Override
                    public void callback(final int eventType, final int currentAngle, final float currentDistance) {
                        switch (eventType) {
                            case RockerView.EVENT_ACTION:
                                // 触摸事件回调
                                Log.e("EVENT_ACTION-------->", "angle=" + currentAngle + " - distance" + currentDistance);
                                break;
                            case RockerView.EVENT_CLOCK:
                                // 定时回调
                                Log.e("EVENT_CLOCK", "angle=" + currentAngle + " - distance" + currentDistance);
                                mThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            outputStream = socket.getOutputStream();
                                            if (currentAngle!= -1) {
                                                outputStream.write(String.valueOf(currentAngle).getBytes("utf-8"));
                                            }
                                            outputStream.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
