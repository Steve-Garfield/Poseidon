package com.example.poseidon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActLogin extends AppCompatActivity {
    Button  mLoginBtn;
    TextView mResigettxt;
    EditText mEmailText, mPasswordText;
    private FirebaseAuth firebaseAuth;
    String emailtext;
    public static Context mContext;

    public String getemail(){
        emailtext = mEmailText.getText().toString();
        return emailtext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        firebaseAuth =  FirebaseAuth.getInstance();
        //버튼 등록하기
        mResigettxt = findViewById(R.id.btn_signup);
        mLoginBtn = findViewById(R.id.btn_login);
        mEmailText = findViewById(R.id.et_email);
        mPasswordText = findViewById(R.id.et_pw);


        //가입 버튼이 눌리면
        mResigettxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(ActLogin.this, ActRegister.class));
            }
        });
        //로그인 버튼이 눌리면
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(ActLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(ActLogin.this, MainActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(ActLogin.this,"로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}