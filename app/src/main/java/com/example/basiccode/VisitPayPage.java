package com.example.basiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VisitPayPage extends AppCompatActivity {
    Button resultbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_pay);
        resultbutton=findViewById(R.id.resultbutton);
        EditText carnumberText=findViewById(R.id.textView2);


        resultbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisitPayPageResult.class);
                startActivity(intent);

                //사전결제 차량 번호 String값으로 받아오기
                String carNumber=carnumberText.getText().toString();
                //System.out.println(carNumber);
            }
        });
    }
}
