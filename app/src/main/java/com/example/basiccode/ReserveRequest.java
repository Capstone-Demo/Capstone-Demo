package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReserveRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://172.30.1.46/Reserve.php";
    final static private String URL2 = "http://172.30.1.46/Reserve_college.php";
    final static private String URL3 = "http://172.30.1.46/Reserve_area.php";
    private Map<String, String> map;

    //예약정보입력
    public ReserveRequest(String college, String parking_area_name, int user_id, String status, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("college", college);
        map.put("parking_area_name", parking_area_name);
        map.put("user_id", user_id + "");
        map.put("status", status);
    }

    //예약가능주차장출력
    public ReserveRequest(Response.Listener<String> listener) {
        super(Method.POST, URL2, listener, null);
    }

    //주차장별주차구역
    public ReserveRequest(String college, Response.Listener<String> listener) {
        super(Method.POST, URL3, listener, null);

        map = new HashMap<>();
        map.put("college", college);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
