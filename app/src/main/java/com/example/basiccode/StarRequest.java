package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StarRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://175.119.82.99/bookmark.php";
    private Map<String, String> map;


    public StarRequest(int user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
