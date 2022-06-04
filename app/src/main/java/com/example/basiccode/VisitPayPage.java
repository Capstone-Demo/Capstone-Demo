package com.example.basiccode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
//                Intent intent=new Intent(getApplicationContext(),VisitPayPageResult.class);
//                startActivity(intent);

                //입력된 차량번호 가져오기(추가함 보기 편하라고)
                String car_num = carnumberText.getText().toString();

                System.out.println("입력된 차량 번호 : " + car_num);

                //출차시간 가져오기
                String departure_time=CameraTime.Cameratime().toString();

                System.out.println("입력된 출차 시간 : " + departure_time);

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                System.out.println("차량번호 넘기기 성공 차량번호 :  " + car_num);
                                System.out.println("출차시간 넘기기 성공 출차시간 :  " + departure_time);

                                Intent data=new Intent(getApplicationContext(),VisitPayPageResult.class);
                                data.putExtra("car_num",car_num);
                                startActivity(data);

                                Toast.makeText(getApplicationContext(),departure_time,Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(),"출차에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
//                DepartureTimeRequest departureTimeRequest=new DepartureTimeRequest(carnumberText.getText().toString(),departure_time,responseListener);
//                RequestQueue queue = Volley.newRequestQueue(VisitPayPage.this);
//                queue.add(departureTimeRequest);

                CarnumRequest carnumRequest=new CarnumRequest(car_num, departure_time, responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisitPayPage.this);
                queue.add(carnumRequest);

            }
        });
    }
}