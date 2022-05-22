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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_payresult);
        Intent data=getIntent();
        String car_num=data.getStringExtra("car_num");
        listView=findViewById(R.id.lv_visitpay);

        //Toast.makeText(this,car_num,Toast.LENGTH_LONG).show();
        visitPayAdapter=new VisitPayAdapter();

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
                            String entry=item.getString("entry");
                            //String departure=item.getString("departure");
                            String status=item.getString("status");
                            //int amount=Integer.parseInt(item.getString("amount"));
                            //int amount=item.getInt("amount");

                            //visitPayAdapter.addItem(new VisitPayList(car_num,entry,departure,status,amount));
                            visitPayAdapter.addItem(new VisitPayList(car_num,entry,status));
                        }
                        listView.setAdapter(visitPayAdapter);
                    } else { // 즐겨찾기가 없는 경우
                        Toast.makeText(VisitPayPageResult.this,"즐겨찾기에 등록된 주차장이 없습니다.",Toast.LENGTH_LONG).show();
                        //System.out.println("결제요금 없음");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        VisitPayRequest visitPayRequest=new VisitPayRequest(car_num,responseListener);
        RequestQueue queue = Volley.newRequestQueue(VisitPayPageResult.this.getApplicationContext() );
        queue.add(visitPayRequest);
    }

}

