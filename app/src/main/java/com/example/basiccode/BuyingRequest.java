package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuyingRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.0.10/Buying.php";
    private Map<String, String> map;

    //회원가입
    public BuyingRequest(String charge_name, int amount, String deadline, int user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("charge_name", charge_name);
        map.put("amount", amount + "");
        map.put("deadline", deadline);
        map.put("user_id", user_id + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
