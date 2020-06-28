package com.example.network.uitls;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class okhttp {
    /*public static void post(String url, FormBody.Builder builder, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody build = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(build)
                .build();
        client.newCall(request).enqueue(callback);



    }*/
    public static void post(String address, String sign_code, String token, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("sign_code","signcode")
                .add("token", "usertoken")
                .build();
        Request request = new Request.Builder()
                .url("http://218.78.85.248:8888/v1/sign/sign_in")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);


    }
}
