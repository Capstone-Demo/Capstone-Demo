package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StatusRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL3 = "http://172.30.1.46/Status.php";
    private Map<String, String> map;

    public StatusRequest(String car_num, Response.Listener<String> listener) {
        super(Method.POST, URL3, listener, null);
        map = new HashMap<>();
        map.put("car_num",car_num+"");
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
