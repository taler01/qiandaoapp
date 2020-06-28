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
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private String signaddress;
    private String signcode;
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





    }
    class mClick implements View.OnClickListener{
        public void onClick(View v){

            String signcode;
            String signaddress = "http://218.78.85.248:8888/v1/sign/sign_in";
            SharedPreferences sharedPreferences= getSharedPreferences("config", Context.MODE_PRIVATE);
            String usertoken = sharedPreferences.getString("token","");
            signcode = editText.getText().toString();
            /*if (usertoken.length() == 0){
                Intent intent3=new Intent();
                //intent3.setClass(MainActivity.this, MainActivity.class);
                //startActivityForResult(intent3, 0);
            }*/

            /**
             * 合并的时候把if前面的/*和对应大括号后面的星号/去掉；
             * 合并的时候要把上两行的//去除掉；
             * 在合并的时候，在if（usertoken.length == 0）的条件成立的情况下由本页面跳转到登陆界面；
             *即把代码intent3.setClass(MainActivity.this, MainActivity.class);的第二个MainActivity改为登陆界面；
             */
            if (signcode.length() == 0 ) {
                Toast.makeText(MainActivity.this, "签到码不能为空", Toast.LENGTH_SHORT).show();
                //return;
            }

            /*if (usertoken.isEmpty()){
              textView2.setText("请先登录");
            }*/

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
                textView2.setText("签到失败，请重试");
                //Toast.makeText(MainActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
                //return;

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
                            parseJSONWithJSONObject(responseData);

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

            /*private void parseJSONWithJSONObject(String jsonData) {
                try {
                    JsonArray jsonArray = new JsonArray(int.valueof(jsonData));
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJsonObject(i);



                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }*/
            private void parseJSONWithJSONObject(String jsonData) {
               try {
                   JSONObject jsonObject1 = new JSONObject(jsonData);
                   JSONArray jsonArray = jsonObject1.getJSONArray("payload");

                   for (int i = 0; i < jsonArray.length(); i++){
                       JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                       String sign_user = jsonObject.getString("sign_user");
                       String sign_message = jsonObject.getString("sign_message");
                       //JSONArray jarray1 = jsonObject.getJSONArray("time_limit");
                       String time_limit = jsonObject.getString("time_limit");
                       Log.d("MainActivity", "sign_user:" + sign_user);
                       Log.d("MainActivity", "sign_message:" + sign_message);
                       Log.d("MainActivity", "time_limit:" + time_limit);
                       /*Bundle bundle1 = new Bundle();
                       Bundle bundle2=new Bundle();//传数据到第二个页面。
                       Bundle bundle3=new Bundle();//传数据到第二个页面。
                       bundle2.putString("text2", sign_message);
                       bundle1.putString("text1", sign_user);
                       bundle3.putString("text3", time_limit);*/





                   }

               }catch (Exception e){
                   e.printStackTrace();

               }


            }

        });

    }
}