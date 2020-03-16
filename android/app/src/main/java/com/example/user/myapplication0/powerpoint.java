package com.example.user.myapplication0;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class powerpoint extends AppCompatActivity {
    private Button next, prev, nlink, plink, enter, tokey, gott;
    private EditText input_number;
    OutputStream outputStream;
    private Handler mMainHandler;
    private ExecutorService mThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerpoint);
        final Socket socket = ((ApplicationUtil)getApplication()).getSocket();
        mThreadPool = Executors.newCachedThreadPool();
        mMainHandler = new Handler() {
            public void handleMessage() {
            }
        };
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        nlink=(Button)findViewById(R.id.nlink);
        plink=(Button)findViewById(R.id.plink);
        enter=(Button)findViewById(R.id.enter);
        tokey=(Button)findViewById(R.id.tokeyboard);
        gott=(Button)findViewById(R.id.go);
        input_number=(EditText)findViewById(R.id.number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream = socket.getOutputStream();
                        outputStream.write("next".getBytes("utf-8"));
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("previous".getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
            }
        });

        nlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("nextlink".getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
            }
        });

        plink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("previouslink".getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("enter".getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
            }
        });

        gott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write("page".getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String input=input_number.getText().toString();
                    outputStream = socket.getOutputStream();
                    outputStream.write(input.getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
            }
        });

        tokey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(powerpoint.this, aa.class);
            startActivity(intent);
            }
        });

    }
}
