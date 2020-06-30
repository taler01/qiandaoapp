package com.example.sign_in;


import android.widget.TextView;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;


public class SignupshowActivity extends AppCompatActivity {
    private Button btn2;
    private TextView txt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupshow);
        TextView txt3=(TextView)findViewById(R.id.myTestView5);
        TextView txt4=(TextView)findViewById(R.id.myTestView6);
        TextView txt5=(TextView)findViewById(R.id.myTestView7);
        TextView txt6=(TextView)findViewById(R.id.myTestView8);

        Bundle bundle=this.getIntent().getExtras();

        String str1 = bundle.getString("text1");
        String str2 = bundle.getString("text2");
        String str3 = bundle.getString("text4");
        int str4 = bundle.getInt("text3");


        txt3.setText("发起签到人：" + str1);
        txt4.setText("签到信息：" + str2);
        txt6.setText(  str3);
        txt5.setText( "过期时间（时间戳）："+ String.valueOf(str4));

        btn2=(Button)findViewById(R.id.myButton2);
        btn2.setOnClickListener(new btnclock2());
    }
    class btnclock2 implements OnClickListener{
        public void onClick(View v){
            Intent intent2=new Intent();
            intent2.setClass(SignupshowActivity.this, HomeActivity.class);
            startActivityForResult(intent2, 0);
        }
    }
}

//public class SecondActivity {


//}


