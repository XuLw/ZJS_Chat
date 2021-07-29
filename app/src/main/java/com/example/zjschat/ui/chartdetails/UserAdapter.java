package com.example.zjschat.ui.chartdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zjschat.R;
import com.example.zjschat.entity.Record;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<Record> mUsers;

    public UserAdapter(ArrayList<Record> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //绑定所有视图
        RelativeLayout mRelativeLayoutSend, mRelativeLayoutReceive;
        LinearLayout mLinearLayoutSystem;
        ImageView mImageViewReceive, mImageViewSend;
        TextView mTextViewNameReceive, mTextViewNameSend, mTextViewContentReceive, mTextViewContentSend, mTextViewSystemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayoutReceive = itemView.findViewById(R.id.receive_relative);
            mRelativeLayoutSend = itemView.findViewById(R.id.send_relative);
            mLinearLayoutSystem = itemView.findViewById(R.id.system_layout);

            mImageViewReceive = itemView.findViewById(R.id.image_receive);
            mImageViewSend = itemView.findViewById(R.id.image_send);

            mTextViewNameReceive = itemView.findViewById(R.id.text_name_receive);
            mTextViewNameSend = itemView.findViewById(R.id.text_name_send);
            mTextViewContentReceive = itemView.findViewById(R.id.text_content_receive);
            mTextViewContentSend = itemView.findViewById(R.id.text_content_send);
            mTextViewSystemText = itemView.findViewById(R.id.system_text);
        }
    }
}
