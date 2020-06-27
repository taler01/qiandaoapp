package com.example.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.network.uitls.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView2;
    private EditText editText;
    private Button button;
    private String usertoken;
    //String url = "http://218.78.85.248:8888/v1/sign/sign_in";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*textView2 = (TextView) findViewById(R.id.Textview2);
        editText = (EditText) findViewById(R.id.EditText);
        button = (Button)findViewById(R.id.Button);
        button.setOnClickListener((View.OnClickListener) this);
        //initRequest();*/
        initView();


    }

    /*private void initRequest() {
        String url = "http://218.78.85.248:8888/v1/sign/sign_in";
    }*/

    private void initView(){
        textView2 = (TextView) findViewById(R.id.Textview2);
        editText = (EditText) findViewById(R.id.EditText);
        button = (Button)findViewById(R.id.Button);
        //button.setOnClickListener((View.OnClickListener) this);
        button.setOnClickListener(new mClick());
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        String usertoken=sharedPreferences.getString("token","");




    }
    class mClick implements View.OnClickListener{
        public void onClick(View v){

            String signcode;
            String signaddress = "http://218.78.85.248:8888/v1/sign/sign_in";
            signcode = editText.getText().toString();
            if (signcode.length() == 0) textView2.setText("签到码不能为空！");
            else {

                post(signaddress, signcode, usertoken);

            }


        }
    }


     /*public void onClick(View v){

        //EditText edit=(EditText)findViewById(R.id.EditText);
        String signcode;
        signcode = editText.getText().toString();


        //Intent intent=new Intent();
        //intent.setClass(MainActivity.this, SecondActivity.class);

        if (signcode.length() == 0){
            textView2.setText("签到码不能为空！");
        }
        else {
            String signaddress = "http://218.78.85.248:8888/v1/sign/sign_in";
            post(signaddress, signcode);

        }
        //Bundle bundle=new Bundle();//传数据到第二个页面。
        //bundle.putString("text", edit.getText().toString());
        //intent.putExtras(bundle);



        /*switch (v.getId()){
            case R.id.Button:
                String signcode = editText.getText().toString();
                if (TextUtils.isEmpty(signcode)){
                    textView2.setText("签到码不能为空！");
                    //Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String signaddress = "http://218.78.85.248:8888/v1/sign/sign_in";
                    post(signaddress, signcode);
                }
                break;
        }

     }*/
    /*private void initRequest() {
        String url = "http://218.78.85.248:8888/v1/sign/sign_in";
        FormBody.Builder builder = new FormBody.Builder();
        builder使要post过去的参数，也就是请求的参数，也就是Textview里的文本内容。
        okhttp.post(url, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                textView2.setText("签到失败！");

            }

            //@Override
            //public void onResponse(Call call, Response response) throws IOException {
              // Log.i(tag: "111111", msg: "onResponse" + response.body().string());
                Log的使用方法有待确定，tag和msg始终报错，信息无法输出在控制台。

            }

        });

     }*/
    public <token> void post(String address, String sign_code, String token){
        okhttp.post(address, sign_code, token, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                textView2.setText("签到失败！");

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.length() == 0){
                            textView2.setText("NULL");

                            //在文本筐里显示老师未发布签到信息。
                        }
                        else{
                            Bundle bundle=new Bundle();//传数据到第二个页面。
                            bundle.putString("text", responseData);
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, SecondActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //在页面上显示签到信息。

                        }

                    }
                });

            }
        });

    }
}