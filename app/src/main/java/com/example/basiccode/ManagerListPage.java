package com.example.basiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerListPage extends AppCompatActivity {
    Button camerabutton, reportbutton, infobutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_list); //관리자 목록 페이지
        camerabutton=findViewById(R.id.camerabutton);
        reportbutton=findViewById(R.id.reportbutton);
        infobutton=findViewById(R.id.inforbutton);

        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisitPayPage.class);
                startActivity(intent);
            }
        });
        infobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ManagerInfo.class);
                startActivity(intent);
            }
        });
    }
}
