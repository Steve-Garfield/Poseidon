package com.example.poseidon;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ActRealWater extends AppCompatActivity {

    private Button btn_realwater;

    private BluetoothSocket mBTSocket;
    final static String on="92";//on
    final static String ML = "550"; // DB로 불러와야 함
    final static String sendstring = on + ":" + ML;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_waterout_arduino);

        btn_realwater = (Button) findViewById(R.id.btn_realwater);


        btn_realwater.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    mBTSocket.getOutputStream().write(sendstring.getBytes());
                    Intent intent = new Intent(ActRealWater.this, ActWaterOut.class);
                    startActivity(intent);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}