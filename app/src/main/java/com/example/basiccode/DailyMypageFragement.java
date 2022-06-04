package com.example.basiccode;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import org.json.JSONException;
import org.json.JSONObject;

//정기권 사용자 마이페이지 프래그먼트 페이지
public class DailyMypageFragement extends Fragment {

    DailyMainPage dailyMainPage;
    AlertDialog dialog;
    MyReserveAdapter myReserveAdapter;
    ListView listView;

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
        listView = rootView.findViewById(R.id.lv_my_reserve);

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

                        listView.setAdapter(myReserveAdapter);
                    }else{ //등록된 예약이 없을 때
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MypageRequest mypageRequest = new MypageRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(mypageRequest);

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
