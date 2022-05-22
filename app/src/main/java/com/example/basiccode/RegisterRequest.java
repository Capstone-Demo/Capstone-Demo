package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.102/Register.php";
    private Map<String, String> map;

    //회원가입
    public RegisterRequest(String id, String password, String name, String role, int age, String gender, String phone_number, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("password", password);
        map.put("name", name);
        map.put("role", role);
        map.put("age", age + "");
        map.put("gender", gender);
        map.put("phone_number", phone_number);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

