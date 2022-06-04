package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StarRequest extends StringRequest {
    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.35.21/bookmark.php";
    final static private String URL2 = "http://192.168.35.21/bookmark_plus.php";
    private Map<String, String> map;


    public StarRequest(int user_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");

    }

    public StarRequest(int user_id, String college_name, Response.Listener<String> listener) {
        super(Request.Method.POST, URL2, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");
        map.put("college_name",college_name);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
