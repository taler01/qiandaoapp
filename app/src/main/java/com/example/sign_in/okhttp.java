package com.example.sign_in;


import android.os.Bundle;
import android.widget.EditText;

import com.example.sign_in.R;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import android.os.Bundle;
import android.content.Intent;

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
    //Bundle bundle = this.get
    public static void post( String sign_code, String token, Callback callback){
        OkHttpClient client = new OkHttpClient();


        FormBody body = new FormBody.Builder()
                .add("sign_code",sign_code)

                .build();
        Request request = new Request.Builder()
                .url("http://218.78.85.248:8888/v1/sign/sign_in")
                .addHeader("token",token)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);


    }
}

