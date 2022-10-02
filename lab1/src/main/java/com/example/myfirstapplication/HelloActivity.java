package com.example.myfirstapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;


public class HelloActivity extends Activity {
    int counter=0;
    int counter1=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        Button button1 = findViewById(R.id.button1);
        FrameLayout layout = findViewById(R.id.mainLayout);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setText("F "+ " " + counter1);
                counter1++;
                layout.setBackgroundColor(Color.rgb(255,255,0));

            }
        });

    }

    public void omClick(View view) {
        Button button2 = findViewById(R.id.button2);
        button2.setText("Clicked "+ counter);
        counter++;
        findViewById(R.id.mainLayout).setBackgroundColor(Color.rgb(0,0,255));
    }
}