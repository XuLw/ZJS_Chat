package com.example.zjschat.ui.recyclerViwer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zjschat.R;
import com.example.zjschat.entity.User;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<User> mDatas;

    public MyRecyclerAdapter(Context context, ArrayList<User> datas) {
        mContext = context;
        mDatas = datas;
        System.out.println(mDatas.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder normalHolder = (MyViewHolder) holder;
        normalHolder.userName.setText(mDatas.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.username);
        }
    }
}
