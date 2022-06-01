package com.example.basiccode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class StarAdapter extends BaseAdapter {
    ArrayList<StarList> items = new ArrayList<StarList>();
    ArrayList<String> parking_area_items = new ArrayList<>(Arrays.asList("원하는 주차구역을 선택하세요."));

    int user_id;
    String status = "COMP";
    String parking_area_name;

    Context context;
    AlertDialog dialog;
    OnItemClick mCallback;

    StarAdapter(OnItemClick listener){
        this.mCallback = listener;
    }

    @Override
    public int getCount() { //ArrayList의 크기 반환
        return items.size();
    }

    @Override
    public Object getItem(int position) { //해당 포지션 위치의 아이템 반환
        return items.get(position);
    }

    @Override
    public long getItemId(int position) { //포지션을 반환
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        StarList starList = items.get(position);

        //star.xml을 inflate해서 convertview를 참조
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.star, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView tv_college = convertView.findViewById(R.id.tv_college);
        TextView tv_accept = convertView.findViewById(R.id.tv_accept);

        //데이터를 set
        tv_college.setText(starList.getCollege_name());
        tv_accept.setText(starList.getDate_accept() + "");

        //정보조회버튼 클릭 이벤트
        Button btn_info = convertView.findViewById(R.id.btn_info);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpXml(items.get(position).getCollege_name(), items.get(position).getAddress(), items.get(position).getDetail_address(),
                        items.get(position).getEnabled(), items.get(position).getDate_accept(), items.get(position).getTotal_quantity());
            }
        });

        //예약하기버튼 클릭 이벤트
        Button btn_reserve = convertView.findViewById(R.id.btn_reserve);

        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("예약하기 버튼 누름");
//                mCallback.onClick(items.get(position).getCollege_name());
                popUpXml(items.get(position).getCollege_name(), items.get(position).getUser_id());
            }
        });

        return convertView;
    }

    public void addItem(StarList starList){
        items.add(starList);
    }


    //정보조회 팝업
    public void popUpXml(String college_name, String address, String detail_address, String enabled, int date_accept, int total_quantity){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.info, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_address = view.findViewById(R.id.tv_address);
        TextView tv_detail = view.findViewById(R.id.tv_detail);
        TextView tv_enabled = view.findViewById(R.id.tv_enabled);
        TextView tv_date_accept = view.findViewById(R.id.tv_date_accept);
        TextView tv_total = view.findViewById(R.id.tv_total);

        tv_name.setText(college_name);
        tv_address.setText(address);
        tv_detail.setText(detail_address);
        tv_enabled.setText("사용가능여부 : " + enabled);
        tv_date_accept.setText("예약가능 : " + date_accept);
        tv_total.setText("총 : " + total_quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    //예약하기 팝업
    public void popUpXml(String college, int user_id){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.star_reserve, null);
        TextView tv_reserve_name = view.findViewById(R.id.tv_reserve_name);
        Spinner sp_reserve_parking_area = view.findViewById(R.id.sp_reserve_parking_area);

        tv_reserve_name.setText(college);

        //parking_area 값 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );

                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 예약 가능 주차구역이 존재하는 경우
                        parking_area_items.clear();
                        parking_area_items.add("원하는 주차구역을 선택하세요.");

                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String parking_area = item.getString("parking_area_name");
                            parking_area_items.add(parking_area);
                        }

                    } else { // 예약 가능한 주차구역이 없는 경우
                        parking_area_items.set(0, "예약 가능한 주차구역이 없습니다.");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ReserveRequest reserveRequest = new ReserveRequest(college, responseListener);
        RequestQueue queue = Volley.newRequestQueue(StarAdapter.this.context);
        queue.add(reserveRequest);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context.getApplicationContext(), android.R.layout.simple_spinner_item, parking_area_items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sp_reserve_parking_area.setAdapter(adapter);

        //parking_area spineer item 클릭 시
        sp_reserve_parking_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parking_area_name = adapter.getItem(position);
                System.out.println("입력될 주차 구역: " + parking_area_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);


        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null){

                    //DB에 입력
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println("hongchul" + response);
                                JSONObject jsonObject = new JSONObject( response );
                                boolean success=jsonObject.getBoolean("success");

                                if(success) { //예약 성공시
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    dialog = builder.setMessage("예약이 완료되었습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                } else{
                                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                    dialog=builder.setMessage("예약이 존재합니다. \n예약 취소 후 다시 시도해주세요.")
                                            .setNegativeButton("확인",null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ReserveRequest reserveRequest = new ReserveRequest(college, parking_area_name, user_id, status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(reserveRequest);


                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}