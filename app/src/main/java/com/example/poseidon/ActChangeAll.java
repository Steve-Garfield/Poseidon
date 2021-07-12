package com.example.poseidon;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

public class ActChangeAll extends AppCompatActivity {

    boolean heart = true;
    public int dataml;
    DatabaseReference heartdata;
    String wat;
    int wat1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_all);
        ImageView image = (ImageView) findViewById(R.id.heart);
        heartdata = FirebaseDatabase.getInstance().getReference();

        TextView tv_name = findViewById(R.id.p_name);
        ImageView iv_img = findViewById(R.id.p_img);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Integer ml = intent.getIntExtra("ml", 1);
        Integer flag = intent.getIntExtra("flag", 1);
        String img = intent.getStringExtra("img");

        if (flag==1) {
            heart=false;
            image.setImageResource(R.drawable.fullheart);
        }

        Glide.with(this)
                .load(img)
                .into(iv_img);
        tv_name.setText(name);

        Button btnwaterout;

        btnwaterout = (Button)findViewById(R.id.waterbtn);



        String[] amount = new String[100];
        int plus = 10;
        for(int i=0; i<100; i++){
            amount[i] = plus + "ML";
            plus += 10;
        }

        NumberPicker picker1 = (NumberPicker)findViewById(R.id.picker1);
        picker1.setMinValue(0);
        picker1.setMaxValue(2);
        picker1.setDisplayedValues(new String[]{
                "기본량","선호량","추천량"
        });

        NumberPicker picker2 = (NumberPicker)findViewById(R.id.picker2);
        picker2.setMinValue(ml);
        picker2.setMaxValue(ml);
        picker2.setWrapSelectorWheel(false);

        NumberPicker picker3 = (NumberPicker)findViewById(R.id.picker3);
        picker3.setValue(1);
        picker3.setDisplayedValues(new String[]{
                "ML"
        });


        picker1.setOnValueChangedListener(new OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                if(newVal == 1){
                    picker2.setMinValue(1);
                    picker2.setMaxValue(1000);
                    picker2.setWrapSelectorWheel(true);
                    Toast.makeText(ActChangeAll.this, "본인만의 정량 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if(newVal == 0){
                    picker2.setMinValue(ml);
                    picker2.setMaxValue(ml);
                    picker2.setWrapSelectorWheel(false);
                    Toast.makeText(ActChangeAll.this, "기본 제공되는 양 입니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    picker2.setMinValue(500);
                    picker2.setMaxValue(500);
                    picker2.setWrapSelectorWheel(false);
                    Toast.makeText(ActChangeAll.this, "가장 많이 사용하는 양 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnwaterout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                wat1 = picker2.getValue();
                String to = Integer.toString(wat1);
                wat = to;
                Intent intent = new Intent(ActChangeAll.this, ActBluetooth.class);
                intent.putExtra("ml", wat);
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(heart) {
                    image.setImageResource(R.drawable.fullheart);
                    dataml = picker2.getValue();
                    HashMap result = new HashMap();
                    result.put("name",name);
                    result.put("ml",dataml);
                    result.put("img",img);
                    result.put("flag", 1);
                    heartdata.child("Product").child("Favorite").child("Favorite_01").setValue(result);
                    heart=false;
                    Toast.makeText(ActChangeAll.this,"즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    image.setImageResource(R.drawable.emptyheart);
                    heartdata.child("Product").child("Favorite").child("Favorite_01").removeValue();
                    heart = true;
                    Toast.makeText(ActChangeAll.this,"즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActChangeAll.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}