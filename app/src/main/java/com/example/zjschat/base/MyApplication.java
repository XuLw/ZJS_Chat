package com.example.zjschat.base;

import android.app.Application;

import com.example.zjschat.entity.User;


public class MyApplication extends Application {
    private static User curUser;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setUser(User user) {
        if (user != null)
            curUser = user;
    }

    public static User getUser() {
        return curUser;
    }

}
