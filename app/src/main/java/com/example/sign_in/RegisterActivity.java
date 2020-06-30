package com.example.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    String token;

    private EditText user_name, user_account, passwd, passwd_ag;//用户名，手机号，密码
    //用户名，电话，密码，再次输入的密码的控件的获取值
    private String userName,phone, psw, pswAgain,sex;
    private RadioButton rb1,rb2;

    private RadioGroup gender;//性别
    private Button btn_reg;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        user_name = findViewById(R.id.name);
        user_account = findViewById(R.id.telephone);
        rb1=findViewById(R.id.rButton1);
        rb2=findViewById(R.id.rButton2);

        passwd = findViewById(R.id.password);
        passwd_ag = findViewById(R.id.password_again);
        gender = findViewById(R.id.sex_radiogroup);
        btn_reg = findViewById(R.id.register);


        //选中状态改变的监听器
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                RadioButton r = (RadioButton) findViewById(checkId);
            }
        });


        btn_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //同一时间只能有一个单选按钮被选中
                for (int i = 0; i < gender.getChildCount(); i++) {
                    RadioButton r = (RadioButton) gender.getChildAt(i);
                    if (r.isChecked()) {


                        //Toast.makeText(RegisterActivity.this,r.getText(),Toast.LENGTH_SHORT).show();
                        break;
                    }
                }


                //获取控件中的字符串
                int selectId = gender.getCheckedRadioButtonId();
                //获取被选中的单选按钮id
                RadioButton r = findViewById(selectId);


                String t = r.getText().toString().trim();
                if(t=="男")
                {
                    sex="male";
                }
                else sex="female";



                userName = user_name.getText().toString().trim();
                phone = user_account.getText().toString().trim();
                psw = passwd.getText().toString().trim();
                pswAgain = passwd_ag.getText().toString().trim();

                if(phone.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (!psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();


                    /*原本的连接服务器请求
                    new Thread() {
                        public void run() {

                            try {
                                //设置路径
                                String path = "http://218.78.85.248:8888/v1/user/register";

                                //创建URL对象
                                URL url = new URL(path);
                                //创建一个HttpURLconnection对象
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                //设置请求方法
                                conn.setRequestMethod("POST");
                                //设置请求超时时间
                                conn.setReadTimeout(5000);
                                //conn.setConnectTimeout(5000);
                                //Post方式不能设置缓存，需要手动设置
                                //conn.setUseCaches(false);
                                //准备要发送的数据
                                String data = "useName=" + URLEncoder.encode(userName, "utf-8") + "&password=" + URLEncoder.encode(psw, "utf-8") +
                                        "&phone=" + URLEncoder.encode(phone, "utf-8") + "&sex=" + URLEncoder.encode(sex, "utf-8");
                                //String data ="id="+ username +"&password="+ pwd1 +"&email="+ email+"";
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");     //使用表单请求类型
                                conn.setRequestProperty("Content-Length", data.length() + "");
                                conn.setDoInput(true);
                                conn.setDoOutput(true);
                                //连接
                                // conn.connect();
                                //获得返回的状态码
                                conn.getOutputStream().write(data.getBytes());
                                int code = conn.getResponseCode();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        ;
                    }.start();

                     */

                    String param ="user_name="+userName+"&user_account="+phone+"&gender="+sex+"&passwd="+psw;
                    Log.d("TAG",param);

                    String return_json= HttpRequest.sendPost("http://218.78.85.248:8888/v1/user/register",param,"");

                    Gson gson=new Gson();
                    UserInfo userInfo=gson.fromJson(return_json,UserInfo.class);
                    token=userInfo.getPayload().getToken();

                    //通过Log测试
                    //Log.d("YU",userInfo.getPayload().getToken());


                   // Log.d("HaHa",return_json);



                    //config表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
                    SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);

                    //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token",token);

                    /*
                    editor.putString("user_name", userName);
                    editor.putString("passwd", psw);
                    editor.putString("user_account", phone);
                    editor.putString("gender", sex);
                    */

                    //提交修改
                    editor.apply();
                    //editor.apply();

                    // savedRegisterToken(userName,psw);


                    // 跳转到HomeActivity
                    Intent data = new Intent(RegisterActivity.this,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);;
                    startActivity(data);
                    //data.setClass(RegisterActivity.this,MainActivity.class);
                    //data.putExtra("user_name", userName);
                    //data.putExtra("passwd", psw);
                    //setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                   // RegisterActivity.this.finish();
                }

            }
        });
    }

}

