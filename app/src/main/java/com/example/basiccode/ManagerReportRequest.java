package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ManagerReportRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.101/managerReport.php";
    final static private String URL2 = "http://192.168.219.101/managerReportUpdate.php";
    private Map<String, String> map;


    public ManagerReportRequest(Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
    }

    public ManagerReportRequest(int report_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL2, listener, null);
        map = new HashMap<>();

        map.put("report_id", report_id + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

