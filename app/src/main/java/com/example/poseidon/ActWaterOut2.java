package com.example.poseidon;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ActWaterOut2 extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView progressText;
    private Button btn_complete;
    private TextView textview_amount;
    int i = 0;

    private BluetoothSocket mBTSocket;
    final static String on="92";//on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterout);

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        btn_complete = (Button) findViewById(R.id.btn_complete);
        textview_amount = findViewById(R.id.textview_amount);
        Intent intent = getIntent();
        String amount = intent.getStringExtra("value");
        textview_amount.setText(amount);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() { //시간이 지난 후 실행할 코딩
                if (i <= 100) {
                    btn_complete.setEnabled(false);
                    btn_complete.setVisibility(View.INVISIBLE);
                    progressText.setText("" + i);
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 50);
                } else {
                    handler.removeCallbacks(this);
                    btn_complete.setVisibility(View.VISIBLE);
                    btn_complete.setEnabled(true);
                }
            }

        }, 50); //0.1초에 1씩 바뀌는

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActWaterOut2.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}

