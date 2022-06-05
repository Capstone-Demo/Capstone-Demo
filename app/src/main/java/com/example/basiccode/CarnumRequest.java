package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CarnumRequest extends StringRequest {
    //mysql -uroot -p -hlocalhost

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.101/CarNumUpdate.php";
    private Map<String, String> map;

    //회원가입
    public CarnumRequest(String car_num, String departure_time, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("car_num",car_num);
        map.put("departure_time",departure_time+"");
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

