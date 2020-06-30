package com.example.sign_in;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private EditText HomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bt_1 = findViewById(R.id.bt_1);
        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InitsignActivity.class);
                startActivity(intent);
                // 跳转到发起签到页面
            }
        });
        bt_2 = findViewById(R.id.bt_2);
        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到查看签到页面
            }
        });
        bt_3 = findViewById(R.id.bt_3);
        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到个人中心
            }
        });

    }
    public void sign_in(View view){
        Intent intent = new Intent(view.getContext(),SignupActivity.class);
        startActivity(intent);
    }
}
