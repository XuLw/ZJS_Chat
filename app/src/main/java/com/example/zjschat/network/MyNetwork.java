package com.example.zjschat.network;

import android.util.Log;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.zjschat.entity.ReLa;
import com.example.zjschat.entity.User;
import com.example.zjschat.utils.StringUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyNetwork {

    static HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .addInterceptor(new LoggerInterceptor("TAG"))
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            })
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build();

    public MyNetwork() {

    }

    public static String get(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                System.out.println(result);
                response.close();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("fail", "返回参数为空 ！");
        return null;
    }

    public static User getUserById(String id) {
        if (StringUtils.isNull(id)) {
            return null;
        }
        String result = get(Constants.GET_USER_BY_ID + id);
        if (!StringUtils.isNull(result)) {
            System.out.println(result);
            User user = JSON.parseObject(result, User.class);
            return user;
        }
        return null;
    }

    public static List<ReLa> getFriendList(String id) {
        if (StringUtils.isNull(id)) {
            return null;
        }
        String result = get(Constants.GET_FIENDS_BY_ID + id);
        if (!StringUtils.isNull(result)) {
            System.out.println(result);
            List<ReLa> list = JSON.parseArray(result, ReLa.class);
            for (ReLa reLa : list) {
                System.out.println(reLa);
            }
            return list;
        }
        return null;
    }
}
