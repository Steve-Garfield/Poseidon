package com.example.poseidon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ActRegister extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mEmailText, mPasswordText, mName;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mName = findViewById(R.id.et_name);
        mEmailText = findViewById(R.id.et_email);
        mPasswordText = findViewById(R.id.et_pw);
        mregisterBtn = findViewById(R.id.btn_CreateAccount);
        //파이어베이스 user 로 접근
        //가입버튼 클릭리스너 --> firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();

                Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                final ProgressDialog mDialog = new ProgressDialog(ActRegister.this);
                mDialog.setMessage("가입중입니다...");
                mDialog.show();

                //파이어베이스에 신규계정 등록하기
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(ActRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //가입 성공시
                        if (task.isSuccessful()) {
                            mDialog.dismiss();

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String email = user.getEmail();
                            String uid = user.getUid();
                            String name = mName.getText().toString().trim();

                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                            HashMap<Object,String> hashMap = new HashMap<>();

                            hashMap.put("email",email);
                            hashMap.put("uid",uid);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(name).setValue(hashMap);


                            //가입이 이루어져을시 가입 화면을 빠져나감.
                            Intent intent = new Intent(ActRegister.this, ActLogin.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ActRegister.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            mDialog.dismiss();
                            Toast.makeText(ActRegister.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            return;  //해당 메소드 진행을 멈추고 빠져나감.

                        }
                    }
                });
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}