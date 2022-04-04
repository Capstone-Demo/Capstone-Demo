package com.example.basiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    Button visitbutton;
    Button dailybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        visitbutton=findViewById(R.id.visitbutton);
        dailybutton=findViewById(R.id.dailybutton);


        visitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisitPayPage.class);
                startActivity(intent);
            }
        });
        dailybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
