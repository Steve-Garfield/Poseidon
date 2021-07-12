package com.example.poseidon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragSearch extends Fragment {
    private ArrayList<ProductList> arrayList;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;
    SearchView searchView;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Product").child("Coffee");
        databaseReference2 = database.getReference().child("Product").child("Ramen"); // DB 테이블연결
        databaseReference3 = database.getReference().child("Product").child("Tea"); // DB 테이블연결
        databaseReference4 = database.getReference().child("Product").child("Etc"); // DB 테이블연결
        arrayList = new ArrayList<>();
        searchView = (SearchView) view.findViewById(R.id.searchView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(databaseReference!= null)
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        arrayList = new ArrayList<>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            arrayList.add(ds.getValue(ProductList.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        arrayList = new ArrayList<>();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            arrayList.add(ds.getValue(ProductList.class));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(searchView!= null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }
    private void search(String str){
        ArrayList<ProductList> mylist  = new ArrayList<>();
        for(ProductList object :arrayList)
        {
            if(object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                mylist.add(object);

            }
        }
        AdtRecycler adtRecycler = new AdtRecycler(mylist, getContext());
        recyclerView.setAdapter(adtRecycler);

    }
}