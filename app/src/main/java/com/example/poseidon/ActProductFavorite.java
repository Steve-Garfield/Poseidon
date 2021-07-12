package com.example.poseidon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActProductFavorite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_favorite);

        Button changebutton, savebutton;
        changebutton = (Button)findViewById(R.id.changebutton);
        savebutton = (Button)findViewById(R.id.savebutton);

        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ActProductFavorite.this, ActChangeFav.class);
                startActivity(intent);
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActProductFavorite.this, ActWaterOut.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
    }
}
