package com.example.basiccode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DailyLogin extends AppCompatActivity {
    Button loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_login); //로그인 페이지
        loginbutton=findViewById(R.id.loginbutton);
        EditText idText=findViewById(R.id.ID);
       // @SuppressLint("WrongViewCast") EditText pwText=findViewById(R.id.PW);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DailyMainPage.class);
                startActivity(intent);

            }
        });
    }
}
