package com.example.basiccode;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyReserveAdapter extends BaseAdapter {

    int user_id;
    String my_college = "등록된 예약 없음";
    String my_parking_area = " ";

    Context context;
    AlertDialog dialog;

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(int user_id, String my_college, String my_parking_area){
        this.user_id = user_id;
        this.my_college = my_college;
        this.my_parking_area = my_parking_area;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        //my_reserve.xml을 inflate해서 convertview를 참조
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_resrve, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView tv_my_college = convertView.findViewById(R.id.tv_my_college);
        TextView tv_my_parking_area = convertView.findViewById(R.id.tv_my_parking_area);

        //데이터를 set
        tv_my_college.setText(my_college);
        tv_my_parking_area.setText(my_parking_area);

        //예약취소 버튼 클릭 이벤트
        Button btn_my_reserve_delete = convertView.findViewById(R.id.btn_my_reserve_delete);

        btn_my_reserve_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("예약취소버튼눌림");

                //예약 취소
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success=jsonObject.getBoolean("success");

                            //삭제 성공
                            if(success) {
                                tv_my_college.setText("등록된 예약 없음");
                                tv_my_parking_area.setText(" ");
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                dialog = builder.setMessage("예약이 취소되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } else{ //삭제실패
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                dialog = builder.setMessage("예약 취소 실패입니다. \n다시 시도해주세요")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MypageRequest mypageRequest = new MypageRequest(3, user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(mypageRequest);
            }
        });

        return convertView;
    }
}
