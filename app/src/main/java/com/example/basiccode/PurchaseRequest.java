package com.example.basiccode;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PurchaseRequest extends StringRequest {
    final static private String URL = "http://10.0.18.214/purchase.php";
    private Map<String, String> map;

    public PurchaseRequest(String user_id, String status, String car_num, String entry, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("user_id",user_id);
        map.put("status",status);
        map.put("car_num",car_num);
        map.put("entry",entry);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
