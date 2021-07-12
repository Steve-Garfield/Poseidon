package com.example.poseidon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdtRecycler extends RecyclerView.Adapter<AdtRecycler.CustomViewHolder> {

    private ArrayList<ProductList> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public AdtRecycler(ArrayList<ProductList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImg())
                .into(holder.iv_img);
        holder.tv_ml.setText(String.valueOf(arrayList.get(position).getMl()));
        holder.tv_name.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "position = " + holder.getAdapterPosition());
                Intent intent = new Intent(context, ActChangeAll.class);
                String img = arrayList.get(position).getImg();
                Integer ml = arrayList.get(position).getMl();
                Integer flag = arrayList.get(position).getFlag();
                String name = arrayList.get(position).getName();
                intent.putExtra("img", img);
                intent.putExtra("ml", ml);
                intent.putExtra("name", name);
                intent.putExtra("flag", flag);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_ml;
        TextView tv_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_img = itemView.findViewById(R.id.iv_img);
            this.tv_ml = itemView.findViewById(R.id.tv_ml);
            this.tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}