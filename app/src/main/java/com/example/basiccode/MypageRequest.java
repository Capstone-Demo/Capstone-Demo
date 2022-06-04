package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.35.21/mypage.php";
    final static private String URL2 = "http://192.168.35.21/mycharge.php";
    final static private String URL3 = "http://192.168.35.21/car.php";
    final static private String URL4 = "http://192.168.35.21/myreserve.php";
    private Map<String, String> map;


    public MypageRequest(int user_id, Response.Listener<String> listener) {

        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");
    }

    public MypageRequest(String string, int user_id, Response.Listener<String> listener) {

        super(Request.Method.POST, URL2, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");
    }

    public MypageRequest(int menu, int user_id, String car_number, String status, Response.Listener<String> listener) {
        super(Request.Method.POST, URL3, listener, null);

        map = new HashMap<>();
        map.put("menu",menu + "");
        map.put("user_id",user_id + "");
        map.put("car_number",car_number);
        map.put("status",status);

    }

    public MypageRequest(int menu, int user_id, Response.Listener<String> listener) {

        super(Request.Method.POST, URL4, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
