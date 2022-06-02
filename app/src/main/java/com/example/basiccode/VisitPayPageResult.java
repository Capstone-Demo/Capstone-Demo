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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisitPayPageResult extends AppCompatActivity {

    ListView listView;
    VisitPayAdapter visitPayAdapter;
    Button paybutton;
    String entry;
    String departure;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_payresult);

        Intent data=getIntent();
        String car_num=data.getStringExtra("car_num");

        listView=findViewById(R.id.lv_visitpay);
        visitPayAdapter=new VisitPayAdapter();
        paybutton=findViewById(R.id.button);

        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 즐겨찾기가 존재하는 경우
                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String car_num=item.getString("car_num");
                            entry=item.getString("entry");
                            departure=item.getString("departure");
                            String status=item.getString("status");
                            amount=Amount.amount(entry,departure);
                            visitPayAdapter.addItem(new VisitPayList(car_num,entry,departure,status,amount));

                        }
                        listView.setAdapter(visitPayAdapter);

                    } else { // 즐겨찾기가 없는 경우
                        Toast.makeText(VisitPayPageResult.this,"조회된 번호가 없습니다.",Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
//        System.out.println("entry"+entry);
//        System.out.println("departure"+departure);
//        amount=Amount.amount(entry,departure);
//        Response.Listener<String> responseListener2=new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    System.out.println("hongchul" + response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//                    if (success) {
//                        Toast.makeText(getApplicationContext(),"요금계산성공",Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(),"요금 계산 실패",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };

        paybutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
            }
        });

        VisitPayRequest visitPayRequest=new VisitPayRequest(car_num,responseListener);
        RequestQueue queue = Volley.newRequestQueue(VisitPayPageResult.this );
        queue.add(visitPayRequest);

        VisitPayRequest visitPayRequest1=new VisitPayRequest(car_num,amount,responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(VisitPayPageResult.this );
        queue2.add(visitPayRequest1);

//        System.out.println(amount);
//
//        AmountRequest amountRequest=new AmountRequest(car_num,amount,responseListener2);
//        RequestQueue queue2 = Volley.newRequestQueue(VisitPayPageResult.this );
//        queue2.add(amountRequest);
    }
}