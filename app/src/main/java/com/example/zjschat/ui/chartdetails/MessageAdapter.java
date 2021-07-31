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
import com.example.zjschat.base.MyApplication;
import com.example.zjschat.entity.Record;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<Record> mRecords;

    public MessageAdapter(ArrayList<Record> records) {
        mRecords = records;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Record record = mRecords.get(position);
        if (false) {
//            系统消息
            holder.mLinearLayoutSystem.setVisibility(View.VISIBLE);
            holder.mRelativeLayoutReceive.setVisibility(View.GONE);
            holder.mRelativeLayoutSend.setVisibility(View.GONE);
            holder.mTextViewSystemText.setText(record.getText());
        } else {
            if (record.getId1().equals(MyApplication.getUser().getId())) {
//                自己发的
                holder.mLinearLayoutSystem.setVisibility(View.GONE);
                holder.mRelativeLayoutReceive.setVisibility(View.GONE);
                holder.mRelativeLayoutSend.setVisibility(View.VISIBLE);

//                holder.mImageViewSend.setImageResource();
//                holder.mTextViewNameSend.setText(MyApplication.getUser().getUsername());
                holder.mTextViewContentSend.setText(record.getText());
            } else {
//                收到的
                holder.mLinearLayoutSystem.setVisibility(View.GONE);
                holder.mRelativeLayoutReceive.setVisibility(View.VISIBLE);
                holder.mRelativeLayoutSend.setVisibility(View.GONE);

//                holder.mImageViewReceive.setImageResource();
//                holder.mTextViewNameReceive.setText(record.getId2());
                holder.mTextViewContentReceive.setText(record.getText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
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

//            mTextViewNameReceive = itemView.findViewById(R.id.text_name_receive);
//            mTextViewNameSend = itemView.findViewById(R.id.text_name_send);
            mTextViewContentReceive = itemView.findViewById(R.id.text_content_receive);
            mTextViewContentSend = itemView.findViewById(R.id.text_content_send);
            mTextViewSystemText = itemView.findViewById(R.id.system_text);
        }
    }
}
