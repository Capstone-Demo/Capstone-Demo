package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VisitPayRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
<<<<<<< HEAD
    final static private String URL = "http://192.168.35.21/VisitPay.php";
    final static private String URL2 = "http://192.168.35.21/Amount.php";
=======
    final static private String URL = "http://192.168.219.101/VisitPay.php";
    final static private String URL2 = "http://192.168.219.101/Amount.php";
>>>>>>> 5bd7f35bf814b416ca87b48f5a8433c9f0e9df8b
    private Map<String, String> map;

    public VisitPayRequest(String car_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("car_num",car_num+"");
    }

    public VisitPayRequest(String car_num,int amount,Response.Listener<String> listener){
        super(Method.POST, URL2, listener, null);
        map = new HashMap<>();
        map.put("car_num",car_num+"");
        map.put("amount", amount+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
