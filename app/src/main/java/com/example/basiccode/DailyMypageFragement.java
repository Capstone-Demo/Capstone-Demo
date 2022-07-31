package com.example.basiccode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//정기권 사용자 마이페이지 프래그먼트 페이지
public class DailyMypageFragement extends Fragment {

    DailyMainPage dailyMainPage;
    AlertDialog dialog;
    MyReserveAdapter myReserveAdapter;
    ListView reserveListView;
    ListView chargeListVeiw;

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
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragement_mypage,container,false);

        int user_id = Integer.parseInt(getArguments().getString("user_id"));

        TextView tv_my_carnum = rootView.findViewById(R.id.tv_my_carnum);
        TextView tv_my_car_enabled = rootView.findViewById(R.id.tv_my_car_enabled);
        Button btn_my_car_register = rootView.findViewById(R.id.btn_my_car_register);
        Button btn_my_car_delete = rootView.findViewById(R.id.btn_my_car_delete);

        myReserveAdapter = new MyReserveAdapter();
        reserveListView = rootView.findViewById(R.id.lv_my_reserve);
        chargeListVeiw = rootView.findViewById(R.id.lv_my_charge);

        //my정기권 정보 저장
        final ArrayList<String> chargeItems = new ArrayList<String>();

        //My page 내용 조회
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );
                    boolean car_success=jsonObject.getBoolean("car_success");

                    //my car 조회
                    if(car_success) { //등록 신청된 차량이 있을 때
                        tv_my_carnum.setText(jsonObject.getString("car_number"));
                        tv_my_car_enabled.setText(jsonObject.getString("status"));
                    } else{ //등록 신청된 차량이 없을 때
                        tv_my_carnum.setText("등록된 차량 없음");
                        tv_my_car_enabled.setText(" ");
                    }


                    boolean reserve_success=jsonObject.getBoolean("reserve_success");
                    //my 예약 조회
                    if(reserve_success){ //등록된 예약이 있을 때
                        String college = jsonObject.getString("college_name");
                        String parking_area = jsonObject.getString("parking_area");

                        myReserveAdapter.addItem(user_id, college, parking_area);

                        reserveListView.setAdapter(myReserveAdapter);
                    }else{ //등록된 예약이 없을 때
                        myReserveAdapter.addItem(user_id, "등록된 예약 없음", " ");

                        reserveListView.setAdapter(myReserveAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MypageRequest mypageRequest = new MypageRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(mypageRequest);

        //My page 내용 조회(정기권파트)
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );

                    //my 정기권 조회
                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();

                    if (length>0) { // 정기권이 존재하는 경우
                        chargeItems.add("정기권이름                          사용기한");
                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String charge_name = item.getString("charge_name");
                            String deadline = item.getString("deadline");

                            chargeItems.add(charge_name + "                     " + deadline);
                        }

                    } else { // 정기권이 없는 경우
                        chargeItems.add("구매한 정기권 없음");
                    }

                    final ArrayAdapter<String> chargeAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_list_item_1, chargeItems
                    );
                    chargeListVeiw.setAdapter(chargeAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MypageRequest mypageRequest2 = new MypageRequest("정기권 조회", user_id, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue2.add(mypageRequest2);

        //car 등록 버튼 클릭
        btn_my_car_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("차량 등록")
                        .setMessage("차량 번호를 입력하세요.")
                        .setView(et)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String car_number = et.getText().toString();
                                String status = "미_등록";

                                //차량 등록
                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            System.out.println("hongchul" + response);
                                            JSONObject jsonObject = new JSONObject( response );
                                            boolean success=jsonObject.getBoolean("success");

                                            //등록 성공
                                            if(success) {
                                                tv_my_carnum.setText(car_number);
                                                tv_my_car_enabled.setText(status);
                                                Toast.makeText(getActivity() , "차량 등록 신청되었습니다.", Toast.LENGTH_SHORT).show();

                                                //새로고침
                                                Intent intent = getActivity().getIntent();
                                                getActivity().finish(); //현재 액티비티 종료 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                            } else{ //등록실패
                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                dialog = builder.setMessage("등록(신청)된 차량이 있습니다. 삭제 후 재등록(신청) 해주세요.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                MypageRequest mypageRequest = new MypageRequest(1, user_id, car_number, status, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                queue.add(mypageRequest);

                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
            }
        });

        //car 삭제 버튼 클릭
        btn_my_car_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("차량 삭제")
                        .setMessage("등록(신청)된 차량을 삭제 합니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String car_number = tv_my_carnum.getText().toString();
                                String status = tv_my_car_enabled.getText().toString();

                                //차량 삭제
                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            System.out.println("hongchul" + response);
                                            JSONObject jsonObject = new JSONObject( response );
                                            boolean success=jsonObject.getBoolean("success");

                                            //삭제 성공
                                            if(success) {
                                                tv_my_carnum.setText("등록된 차량 없음");
                                                tv_my_car_enabled.setText(" ");
                                                Toast.makeText(getActivity() , "등록(신청)된 차량이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                //새로고침
                                                Intent intent = getActivity().getIntent();
                                                getActivity().finish(); //현재 액티비티 종료 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                                getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                            } else{ //삭제실패
                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                dialog = builder.setMessage("등록(신청)된 차량이 없습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                MypageRequest mypageRequest = new MypageRequest(2, user_id, car_number, status, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                queue.add(mypageRequest);

                                dialog.dismiss();
                            }
                        });

                dialog = builder.create();
                dialog.show();
            }
        });



        return rootView;
    }
}
