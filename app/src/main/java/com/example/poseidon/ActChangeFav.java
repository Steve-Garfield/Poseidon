package com.example.poseidon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class ActChangeFav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Button savebutton;
        savebutton = (Button)findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ActChangeFav.this, ActProductFavorite.class);
                startActivity(intent);
            }
        });

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
                "추천량","선호량","기본량"
        });

        NumberPicker picker2 = (NumberPicker)findViewById(R.id.picker2);
        picker2.setMinValue(450);
        picker2.setMaxValue(450);
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
                    Toast.makeText(ActChangeFav.this, "본인만의 정량 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if(newVal == 2){
                    picker2.setMinValue(550);
                    picker2.setMaxValue(550);
                    picker2.setWrapSelectorWheel(false);
                    Toast.makeText(ActChangeFav.this, "기본 제공되는 양 입니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    picker2.setMinValue(500);
                    picker2.setMaxValue(500);
                    picker2.setWrapSelectorWheel(false);
                    Toast.makeText(ActChangeFav.this, "가장 많이 사용하는 양 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}