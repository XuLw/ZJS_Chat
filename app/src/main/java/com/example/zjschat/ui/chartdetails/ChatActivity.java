package com.example.zjschat.ui.chartdetails;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.zjschat.R;
import com.example.zjschat.base.BaseActivity;
import com.example.zjschat.base.MyApplication;
import com.example.zjschat.entity.Record;
import com.example.zjschat.entity.User;
import com.example.zjschat.network.MyNetwork;
import com.example.zjschat.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends BaseActivity {

    private static final String TAG = "ChatActivity";

    private ArrayList<Record> mMessages = new ArrayList<>();//定义一个存储信息的列表
    private EditText mInputText;//输入框
    private Button mSend;//发送按钮
    private RecyclerView mRecyclerView;//滑动框
    private MessageAdapter mAdapter;//适配器
    private boolean backFlag = false;
    private WebSocket mSocket;

    private User mUser;  // 跟谁聊
    //全局User

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("user");
        if (!StringUtils.isNull(s)) {
            mUser = JSON.parseObject(s, User.class);
        }

        Log.i(TAG, "当前聊天用户为：" + mUser.toString());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessageAdapter(mMessages);
        mRecyclerView.setAdapter(mAdapter);

        //开启连接
//        start(mUser.getUserId());
        MyNetwork.startSocket(MyApplication.getUser().getId(), new EchoWebSocketListener());

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mInputText.getText().toString();
                if (!"".equals(content)) {
                    Record record = new Record();
                    record.setId1(MyApplication.getUser().getId());
                    record.setId2(mUser.getId());
                    record.setSend_date(new Date());
//                    Msg msg = new Msg(true,content,false);
//                    User tempUser = new User(mUser.getUserId(),mUser.getUserName(),R.drawable.boy,msg);
//                    mSocket.send(tempUser.toString());
//                    mUserArrayList.add(tempUser);
                    record.setText(content);
                    mSocket.send(JSON.toJSONString(record));
                    mMessages.add(record);
                    updateRecyclerView();//刷新RecyclerView
                    //清空输入栏
                    mInputText.setText("");
                }
            }
        });
    }

    /**
     * 刷新view
     */
    private void updateRecyclerView() {
        //当有新消息时，刷新RecyclerView中的显示
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        //将RecyclerView定位到最后一行
        mRecyclerView.scrollToPosition(mMessages.size() - 1);
    }


    /**
     * 显示内容
     */
    private void output(final Record record) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessages.add(record);
                updateRecyclerView();
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mInputText = findViewById(R.id.input_text);
        mSend = findViewById(R.id.send);
        mRecyclerView = findViewById(R.id.msg_recycler_view);
    }


    /**
     * 对返回键的处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !backFlag) {
            Toast.makeText(ChatActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            backFlag = true;
            return true;
        } else {
            mSocket.close(1, "主动关闭！");
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 内部类，监听web socket回调
     */
    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Log.i(TAG, "onOpen: 建立连接");
            mSocket = webSocket;    //实例化web socket

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.d(TAG, "onMessage: " + text);
            Record record = JSON.parseObject(text, Record.class);
//            显示信息
            output(record);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
//            User user = new User();
//            user.setUserMsg(new Msg(false,"关闭连接",true));
//            output(user);

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            Log.i(TAG, "onFailure: 连接失败" + response);
//            User user = new User();
//            user.setUserMsg(new Msg(false,"连接失败:"+t.getMessage(),true));
//            output(user);
        }
    }
}
