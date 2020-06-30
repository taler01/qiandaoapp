package com.example.sign_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class InitsignActivity extends AppCompatActivity {
    private Button btn1;
    private EditText edit1, edit2, edit3;
    private TextView txt1, txt2, sign_name, sign_message, time_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initsign);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt2 = (TextView) findViewById(R.id.txt2);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        edit3 = (EditText) findViewById(R.id.edit3);
        sign_name = (TextView) findViewById(R.id.sign_name);
        sign_message = (TextView) findViewById(R.id.sign_message);
        time_limit = (TextView) findViewById(R.id.time_limit);
    }

        public void initSign(View view) {
            SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
            String token = config.getString("token", "");
            String sign_name = edit1.getText().toString().trim();
            String sign_message = edit2.getText().toString().trim();
            if (sign_name.isEmpty() || sign_message.isEmpty()|| edit3.getText().toString().trim().isEmpty()) {
                Toast.makeText((getApplication()), "参数错误！", Toast.LENGTH_LONG).show();
            }
            String send_time = "1594107657";
            String param = "sign_name=" + sign_name + "&sign_message=" + sign_message + "&time_limit=" + send_time;//参数是这样的形式 根据实际调用的接口填入相应的参数就行
//POST请求
            String url = "Http://218.78.85.248:8888/v1/sign/init_sign_in";
            String return_json = HttpRequest.sendPost(url, param, token);
            Gson gson = new Gson();
            Init_sign_message init_sign_message = gson.fromJson(return_json, Init_sign_message.class);
            String state = init_sign_message.getState();
            if (state == "false"){
                Toast.makeText((getApplication()),"发起签到失败！", Toast.LENGTH_LONG).show();
                return;
            }
            String sign_code = init_sign_message.getPayload().getSign_code();
            Toast.makeText((getApplication()),"签到码："+sign_code, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(view.getContext(),HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);;
            startActivity(intent);
    }

    class Init_sign_message {
        private String state;
        private Payload_init_sign payload;

        public Payload_init_sign getPayload()
        {
            return payload;
        }

        public String getState() {
            return state;
        }
    }
    class Payload_init_sign{
        private String sign_code;
        public String getSign_code(){
            return sign_code;
        }
    }
}