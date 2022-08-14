package com.example.basiccode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManagerInfoResult extends AppCompatActivity {
    Button reportbutton, hostbutton;
    ManagerInoAdapter managerInoAdapter;
    String phone_num;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_info_result); //관리자 목록 페이지
        reportbutton=findViewById(R.id.reportbutton); //신고
        hostbutton=findViewById(R.id.hostbutton); //차량주 연락

        Intent intent=getIntent();
        String car_num=intent.getStringExtra("carnum");
        listView=findViewById(R.id.lv_visitpay);
        managerInoAdapter=new ManagerInoAdapter();
        
        //차량번호 확인
        System.out.println(car_num);

        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 차량번호가 존재하는 경우
                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String car_num=item.getString("user.name");
                            phone_num=item.getString("user.phone_number");
                            String status=item.getString("purchase.status");
                            String entry=item.getString("purchase.entry");

                            managerInoAdapter.addItem(new VisitPayList(car_num,status,entry));
                        }
                        listView.setAdapter(managerInoAdapter);

                    } else { // 차량번호가 존재하지 않는 경우
                        Toast.makeText(ManagerInfoResult.this,"조회된 번호가 없습니다.",Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ManagerInfoRequest managerInfoRequest=new ManagerInfoRequest(car_num,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ManagerInfoResult.this );
        queue.add(managerInfoRequest);

        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        hostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone_num));
                startActivity(intent1);
            }
        });
    }
}
