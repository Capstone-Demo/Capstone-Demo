package com.example.basiccode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class VisitPayPageResult extends AppCompatActivity {

    VisitPayPage visitPayPage;
    ListView listView;
    VisitPayAdapter visitPayAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_payresult);
        Intent data=getIntent();
        String car_num=data.getStringExtra("car_num");

        Toast.makeText(this,car_num,Toast.LENGTH_LONG).show();
        visitPayAdapter=new VisitPayAdapter();
    }

}

