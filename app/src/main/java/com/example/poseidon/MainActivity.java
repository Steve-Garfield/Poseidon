package com.example.poseidon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        // 처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new FragHome()).commit(); //FrameLayout에 Home.xml 띄우기

        // 바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public  boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml 띄우기
                    case R.id.item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragHome()).commit();
                        break;
                    case R.id.item_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragSearch()).commit();
                        break;
                    case R.id.item_category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragCategory()).commit();
                        break;
                    case R.id.item_favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragFavorite()).commit();
                        break;
                    case R.id.item_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragMyPage()).commit();
                        break;
                }
                return true;
            }
        });
    }
}