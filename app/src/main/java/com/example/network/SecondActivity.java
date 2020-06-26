package com.example.network;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

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


public class SecondActivity extends AppCompatActivity {
    private Button btn2;
    private TextView txt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView txt3=(TextView)findViewById(R.id.myTestView5);

        Bundle bundle=this.getIntent().getExtras();

        String str = bundle.getString("text");
        txt3.setText(str);
        btn2=(Button)findViewById(R.id.myButton2);
        btn2.setOnClickListener(new btnclock2());
    }
    class btnclock2 implements OnClickListener{
        public void onClick(View v){
            Intent intent2=new Intent();
            intent2.setClass(SecondActivity.this, MainActivity.class);
            startActivityForResult(intent2, 0);
        }
    }
}

//public class SecondActivity {


//}

