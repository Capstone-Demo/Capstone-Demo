package com.example.basiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DailyRegister extends AppCompatActivity {
    Button joinbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_register); //회원가입 페이지
        joinbutton=findViewById(R.id.joinbutton);
        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DailyLogin.class);
                startActivity(intent);
            }
        });
    }
}
