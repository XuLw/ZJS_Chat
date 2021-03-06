package com.example.zjschat.ui.chartlist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.zjschat.R;
import com.example.zjschat.base.BaseActivity;
import com.example.zjschat.base.MyApplication;
import com.example.zjschat.entity.ReLa;
import com.example.zjschat.entity.User;
import com.example.zjschat.network.MyNetwork;
import com.example.zjschat.ui.chartdetails.ChatActivity;
import com.example.zjschat.ui.recyclerViwer.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends BaseActivity {

    private ArrayList<User> mDatas = new ArrayList<>();

    MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        RecyclerView mRv = findViewById(R.id.rcv_friend_list);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MyRecyclerAdapter(this, mDatas);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);

                //    获取当前位置的User
                User user = mDatas.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("user", JSON.toJSONString(user));
                intent.putExtras(bundle);
                //      跳转到对应的聊天室
                startActivity(intent);
            }
        });
        mRv.setAdapter(adapter);
        new MyTask().execute();
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            List<ReLa> list = MyNetwork.getFriendList(MyApplication.getUser().getId());
            for (ReLa rela : list) {
                User user = new User();
                if (rela.getId1().equals(MyApplication.getUser().getId())) {
                    user.setId(rela.getId2());
                    user.setUsername(rela.getUsername2());
                } else {
                    user.setId(rela.getId1());
                    user.setUsername(rela.getUsername1());
                }
                mDatas.add(user);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
        }
    }


}