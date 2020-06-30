package com.example.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.security.acl.Group;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        final Button button = (Button) findViewById(R.id.login_button);
        button.setEnabled(Boolean.FALSE);
        button.setBackgroundResource(R.drawable.login_grey);
        final EditText accountText = (EditText) findViewById(R.id.login_phone);
        final EditText passwordText = (EditText) findViewById(R.id.login_password);
        TextWatcher accountWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String account_string = accountText.getText().toString();
                String password_string = passwordText.getText().toString();
                if ((account_string.length() > 0) & (password_string.length() > 0)){
                    button.setBackgroundResource(R.drawable.login);
                    button.setEnabled(Boolean.TRUE);
                }else {
                    button.setBackgroundResource(R.drawable.login_grey);
                    button.setEnabled(Boolean.FALSE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        accountText.addTextChangedListener(accountWatcher);
        passwordText.addTextChangedListener(accountWatcher);
    }
    public void account_login(View view){
        Button button = (Button) findViewById(R.id.login_button);
        final EditText accountText = (EditText) findViewById(R.id.login_phone);
        final EditText passwordText = (EditText) findViewById(R.id.login_password);
        String account = accountText.getText().toString();
        String password =passwordText.getText().toString();
        String param = "user_account="+account+"&passwd="+password;
        String return_json = HttpRequest.sendPost("http://218.78.85.248:8888/v1/user/login",param,"");
        if (return_json.length() <= 0){
            Toast.makeText((getApplication()),"网络连接失败，请检查网络！", Toast.LENGTH_LONG).show();
            button.setVisibility(View.VISIBLE);
            button.setBackgroundResource(R.drawable.login);
            button.setEnabled(Boolean.TRUE);
        }else{
            try {
                Gson gson = new Gson();
                user_token userToken = gson.fromJson(return_json, user_token.class);
                String state = userToken.getState();
                if (state == "true") {
                    String token = userToken.getPayload().getToken();
                    SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    editor.putString("token",token);
                    editor.apply();
                    Intent intent = new Intent(this,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    button.setVisibility(View.VISIBLE);
                    Toast.makeText((getApplication()),"用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                button.setVisibility(View.VISIBLE);
            }
        }

    }
    public void account_register(View view)
    {
        Intent intent = new Intent(this,RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

class user_token{
    private String state;
    private Payloads payload;
    public String getState(){
        return state;
    }
    public Payloads getPayload(){
        return payload;
    }
}
class Payloads{
    private String token;
    public String getToken(){
        return token;
    }
}

