package com.example.basiccode;

import android.content.Context;
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

//정기권 사용자 페이지 프래그먼트 페이지
public class DailyMainFragment extends Fragment {

    DailyMainPage dailyMainPage;
    Button btn_reserve;
    String[] college_items = {"원하는 주차장을 선택하세요.", "한울관 주차장", "송암관 주차장"};
    String[] parking_area_items = {"원하는 주차구역을 선택하세요.", "전기차 전용 구역", "장애인 전용 구역", "어르신 전용 구역"};
    AlertDialog dialog;

    String college = "";
    int user_id = 0;
    String parking_area_name = "";
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
                System.out.println(college);
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
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //parking_area spineer item 클릭 시
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parking_area_name = adapter2.getItem(position);
                System.out.println(parking_area_name);
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
                            boolean success = jsonObject.getBoolean( "success" );

                            if (success) {  //예약 성공시
                                AlertDialog.Builder builder=new AlertDialog.Builder( getActivity() );
                                dialog=builder.setMessage("예약이 완료되었습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            } else { //예약 실패시
                                AlertDialog.Builder builder=new AlertDialog.Builder( getActivity());
                                dialog=builder.setMessage("예약 실패입니다. 다시시도해주세요.")
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
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(reserveRequest);
            }});

        return rootView;
    }
}
