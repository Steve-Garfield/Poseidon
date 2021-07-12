package com.example.poseidon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragMyPage extends Fragment {
    TextView textView;
    String emailtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        textView = (TextView)v.findViewById(R.id.userid);
        emailtext = ((ActLogin)ActLogin.mContext).getemail();

        textView.setText(emailtext);

        // Inflate the layout for this fragment
        return v;

    }
}