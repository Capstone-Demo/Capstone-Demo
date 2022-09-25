package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReportRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.101/Report.php";
    private Map<String, String> map;

    //신고하기
    public ReportRequest(int user_id, String report_college, String car_number, String cause, String report_date, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id + "");
        map.put("report_college", report_college);
        map.put("car_number", car_number);
        map.put("cause", cause);
        map.put("report_date", report_date);
        map.put("status", "처리필요");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

