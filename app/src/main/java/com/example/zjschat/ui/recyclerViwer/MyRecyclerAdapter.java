package com.example.zjschat.ui.recyclerViwer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.zjschat.R;
import com.example.zjschat.entity.User;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private ArrayList<User> mDatas;
    private OnItemClickListener mOnItemClickListener;


    public MyRecyclerAdapter(Context context, ArrayList<User> datas) {
        mContext = context;
        mDatas = datas;
        System.out.println(mDatas.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.friend_item, parent, false);
        return new MyViewHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyViewHolder normalHolder = (MyViewHolder) holder;
        normalHolder.userName.setText(mDatas.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public class MyViewHolder extends ViewHolder {

        public TextView userName;

        public MyViewHolder(@NonNull final View itemView, final OnItemClickListener onClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClicked(view, pos);
                        }
                    }
                }
            });
            userName = (TextView) itemView.findViewById(R.id.username);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }
}
