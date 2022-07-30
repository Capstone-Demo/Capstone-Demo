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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

//정기권 사용자 예약 프래그먼트 페이지
public class DailyMainFragment extends Fragment {

    DailyMainPage dailyMainPage;
    Button btn_reserve;
    ArrayList<String> college_items = new ArrayList<>(Arrays.asList("원하는 주차장을 선택하세요."));
    ArrayList<String> parking_area_items = new ArrayList<>(Arrays.asList("원하는 주차구역을 선택하세요."));
    AlertDialog dialog;

    String college;
    int user_id;
    String parking_area_name;
    String status = "COMP";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dailyMainPage = (DailyMainPage) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragement_main, container, false);

        //user_id값 받아오기
        user_id = Integer.parseInt(getArguments().getString("user_id"));


        //college spinner에 item 넣기
        Spinner spinner = rootView.findViewById(R.id.sp_college);

        //college값 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );

                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 예약 가능 주차장이 존재하는 경우
                        college_items.clear();
                        college_items.add("원하는 주차장을 선택하세요.");

                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String college_name = item.getString("college_name");
                            college_items.add(college_name);
                        }

                    } else { // 예약 가능한 주차장이 없는 경우
                        college_items.set(0, "예약 가능한 주차장이 없습니다.");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ReserveRequest reserveRequest = new ReserveRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(reserveRequest);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, college_items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //college spineer item 클릭 시
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                college = adapter.getItem(position);
                System.out.println("입력될 주차장: " + college);

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
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(reserveRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //parking_area spinner에 item 넣기
        Spinner spinner2 = rootView.findViewById(R.id.sp_parking_area);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, parking_area_items);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //parking_area spineer item 클릭 시
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parking_area_name = adapter2.getItem(position);
                System.out.println("입력될 주차 구역: " + parking_area_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btn_reserve = (Button)rootView.findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 입력
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject( response );
                            boolean charge_success = jsonObject.getBoolean("charge_success");
                            boolean car_success = jsonObject.getBoolean("car_success");
                            boolean success=jsonObject.getBoolean("success");

                            if(charge_success){
                                if(car_success){
                                    if(success) { //예약 성공시
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        dialog = builder.setMessage("예약이 완료되었습니다.")
                                                .setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) { //새로고침
                                                        Intent intent = getActivity().getIntent();
                                                        getActivity().finish(); //현재 액티비티 종료 실시
                                                        getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                        getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                        getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                    }
                                                })
                                                .create();
                                        dialog.show();
                                    } else{
                                        AlertDialog.Builder builder=new AlertDialog.Builder( getActivity() );
                                        dialog=builder.setMessage("예약이 존재합니다. \n예약 취소 후 다시 시도해주세요.")
                                                .setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) { //새로고침
                                                        Intent intent = getActivity().getIntent();
                                                        getActivity().finish(); //현재 액티비티 종료 실시
                                                        getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                        getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                        getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                    }
                                                })
                                                .create();
                                        dialog.show();
                                    }
                                }else{
                                    AlertDialog.Builder builder=new AlertDialog.Builder( getActivity() );
                                    dialog=builder.setMessage("등록된 차량이 없습니다. \n마이페이지>차량등록 후 다시 시도해주세요.")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) { //새로고침
                                                    Intent intent = getActivity().getIntent();
                                                    getActivity().finish(); //현재 액티비티 종료 실시
                                                    getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                    getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                    getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                }
                                            })
                                            .create();
                                    dialog.show();
                                }
                            }else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( getActivity() );
                                dialog=builder.setMessage("구매하신 정기권이 없습니다. \n정기권 구매 후 다시 시도해주세요.")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) { //새로고침
                                                Intent intent = getActivity().getIntent();
                                                getActivity().finish(); //현재 액티비티 종료 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ReserveRequest reserveRequest = new ReserveRequest(college, parking_area_name, user_id, status, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(reserveRequest);
            }});

        return rootView;
    }
}
