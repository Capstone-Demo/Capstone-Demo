package com.example.basiccode;

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

public class ManagerReport extends AppCompatActivity {

    ListView listView;
    ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_report); //관리자 신고목록 페이지


        //ReportList 참조
        listView = findViewById(R.id.lv_report_list);
        reportAdapter = new ReportAdapter();


        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 미완료 신고목록이 존재하는 경우
                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String report_date = item.getString("report_date");
                            String report_college = item.getString("college_name");
                            String car_number = item.getString("car_number");
                            String cause = item.getString("cause");

                            reportAdapter.addItem(new ReportList(report_date, report_college, car_number, cause));
                        }
                        listView.setAdapter(reportAdapter);

                    } else { // 미완료 신고목록이 없는 경우
                        Toast.makeText(getApplicationContext(),"처리되지 않은 신고 목록이 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ManagerReportRequest managerReportRequest = new ManagerReportRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(ManagerReport.this);
        queue.add(managerReportRequest);
    }
}
