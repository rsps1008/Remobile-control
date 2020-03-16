package com.example.user.myapplication0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class aa extends AppCompatActivity {
    Button key;
    Button ppt;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aa);
        ppt=(Button)findViewById(R.id.b2);
        key=(Button)findViewById(R.id.b1);
        button=(Button)findViewById(R.id.button);

        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(aa.this, keyboard.class);
                startActivity(intent);
            }
        });
        ppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(aa.this, powerpoint.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(aa.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
