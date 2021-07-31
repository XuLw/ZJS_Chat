package com.example.zjschat.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zjschat.R;
import com.example.zjschat.base.BaseActivity;
import com.example.zjschat.base.MyApplication;
import com.example.zjschat.entity.User;
import com.example.zjschat.network.MyNetwork;
import com.example.zjschat.ui.chartlist.FriendListActivity;
import com.example.zjschat.utils.StringUtils;

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userIdET = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 验证用户是否存在
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!StringUtils.isNull(userIdET.getText().toString())) {
                            User user = MyNetwork.getUserById(userIdET.getText().toString());
//                        User user = new User("1", "21312");
                            if (user != null) {
//                            进入消息列表
                                Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("id", user.getId());
//                            bundle.putString("username", user.getUsername());
//                            intent.putExtras(bundle);
                                MyApplication.setUser(user);
                                startActivity(intent);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}