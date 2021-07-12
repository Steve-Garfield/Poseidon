package com.example.poseidon;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragHome extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ProductList> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private View view;
    public String water;


    private ImageButton btn_water, btn_plus, btn_minus, recent_1, recent_2, recent_3, recent_4;
    boolean i = true;
    private EditText watervalue;
    int value = 300; // 홈 화면에 제일 기본으로 떠 있는 한 잔 물 양임.

    @Override
    public void onClick(View v) {

    }

    public void setText(int item) {
        EditText et_num_Home = (EditText) getView().findViewById(R.id.edittext_num_HOME);
        int value = Integer.parseInt(et_num_Home.getText().toString());
        et_num_Home.setText(String.valueOf(item));
    }

    /*
        public static Home newInstance(){
            return new Home();
        }
    */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        // 중복 클릭 방지 시간 설정 ( 해당 시간 이후에 다시 클릭 가능 )
        private static final long MIN_CLINCK_INTERVAL = 600;
        private long mLastClickTime = 0;

        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;

            // 중복클릭 아닌 경우
            if (elapsedTime > MIN_CLINCK_INTERVAL) {
                onSingleClick(v);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false); //Fragment로 불러올 xml 파일을 view로 가져온다.

        //리사이클러뷰
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Coffee 객체를 담을 어레이 리스트 (어댑터쪽으로)
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference().child("Product").child("Ramen"); // DB 테이블연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List 추출
                    ProductList productList = snapshot.getValue(ProductList.class); //만들어뒀던 Product 객체에 데이터를 담음
                    arrayList.add(productList); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // 디비가져오던중 에러 발생 시
                Log.e("Fraglike",String.valueOf(error.toException())); //에러문 출력
            }
        });
        adapter = new AdtRecycler(arrayList, getContext());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
        // 블루투스
        ImageButton btn_water;
        btn_water = (ImageButton) view.findViewById(R.id.btn_waterout);
        btn_minus = (ImageButton) view.findViewById(R.id.btn_minus);
        btn_plus = (ImageButton) view.findViewById(R.id.btn_plus);


        //init(view);
       // readProduct();

        btn_water.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), ActWaterOut2.class);
                watervalue = (EditText) view.findViewById(R.id.edittext_num_HOME);
                water = watervalue.getText().toString();
                intent.putExtra("value", water);
                startActivity(intent);
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (value != 10) {
                    setText(value - 10);
                    value = value - 10;
                    //textView_num_HOME.setText(String.valueOf(value-10));
                } else if (value == 10) {
                    setText(value);
                    //textView_num_HOME.setText(String.valueOf(value));
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (value != 990) {
                    setText(value + 10);
                    value = value + 10;
                    //textView_num_HOME.setText(String.valueOf(value + 10));
                } else if (value == 990) {
                    setText(value);
                    //textView_num_HOME.setText(String.valueOf(value));
                }
            }
        });



        return view;
    }

/*
    void init(View view){
        arrayList = new ArrayList<>(); // Category 객체를 담을 어레이 리스트 ( 어댑터 쪽으로 날림 )

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_horizontal);
        layoutManager = new LinearLayoutManager((getContext()), LinearLayoutManager.HORIZONTAL, false); // 가로 리사이클러 뷰 매니저
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // Category 객체를 담을 어레이 리스트 ( 어댑터 쪽으로 날림 )

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        // firebase 데이터베이스 정의
        databaseReference = database.getInstance().getReference("Product").child("Coffee").child("Coffee_01"); // DB 테이블 연결
        databaseReference.keepSynced(true);

        storageReference = FirebaseStorage.getInstance().getReference("Product").child("Coffee").child("Coffee_01");

        adapter = new CustomAdapter(arrayList, getContext());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_horizontal);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }



    void readProduct() {
        databaseReference.child("Product").child("Coffee").child("Coffee_01").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Category.class) != null) {
                    for(DataSnapshot Snapshot : snapshot.getChildren()){
                        Category post = Snapshot.getValue(Category.class);
                        arrayList.add(post);
                        Log.w("FirebaseData", "데이터 가져오기 " + post.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FireBaseData", "데이터 가져오기 실패 " + error.toException());
            }
        });
    }
    */
}