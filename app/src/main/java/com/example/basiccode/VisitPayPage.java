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

        //출차시간 가져오기
        String departure_time=CameraTime.Cameratime().toString();
        System.out.println("departure_time"+departure_time); //성공

        resultbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisitPayPageResult.class);
                startActivity(intent);

                Intent data=new Intent(getApplicationContext(),VisitPayPageResult.class);
                data.putExtra("car_num",carnumberText.getText().toString());
                data.putExtra("departure",departure_time);
                startActivity(data);


                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
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
                DepartureTimeRequest departureTimeRequest=new DepartureTimeRequest(carnumberText.getText().toString(),departure_time,responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisitPayPage.this);
                queue.add(departureTimeRequest);

                CarnumRequest carnumRequest=new CarnumRequest(carnumberText.getText().toString(),responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(VisitPayPage.this);
                queue2.add(carnumRequest);

            }
        });
    }
}
