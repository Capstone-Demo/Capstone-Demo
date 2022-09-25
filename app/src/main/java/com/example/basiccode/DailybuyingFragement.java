package com.example.basiccode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//정기권 사용자 마이페이지 프래그먼트 페이지
public class DailybuyingFragement extends Fragment {

    DailyMainPage dailyMainPage;
    private RadioGroup rg_buying;
    private Button btn_buying;

    private String charge_name;
    private int amount;
    private String deadline;
    private int user_id;

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
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragement_buying,container,false);

        rg_buying = rootView.findViewById(R.id.rg_buying);
        btn_buying = rootView.findViewById(R.id.btn_buying);

        //회원정보
        user_id = Integer.parseInt(getArguments().getString("user_id"));

        //날짜 계산
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        System.out.println(mDate);

        //구매하기 버튼 클릭 시
        btn_buying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //radio선택 가져오기
                int rb_id = rg_buying.getCheckedRadioButtonId();

                switch (rb_id){
                    case R.id.radio_A:{
                        charge_name = "정기권(1개월)";
                        amount = 1000;
                        cal.setTime(mDate);
                        cal.add(Calendar.MONTH, 1);
                        deadline = simpleDate.format(cal.getTime());
                        break;
                    }
                    case R.id.radio_B:{
                        charge_name = "정기권(2개월)";
                        amount = 2000;
                        cal.setTime(mDate);
                        cal.add(Calendar.MONTH, 2);
                        deadline = simpleDate.format(cal.getTime());
                        break;
                    }
                    case R.id.radio_C:{
                        charge_name = "정기권(3개월)";
                        amount = 3000;
                        cal.setTime(mDate);
                        cal.add(Calendar.MONTH, 3);
                        deadline = simpleDate.format(cal.getTime());
                        break;
                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if (success) {

                                //새로고침
                                Intent intent = getActivity().getIntent();
                                getActivity().finish(); //현재 액티비티 종료 실시
                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                getActivity().overridePendingTransition(0, 0); //효과 없애기

                                Toast.makeText(getActivity() , String.format("%s 구매 완료되었습니다.", charge_name), Toast.LENGTH_SHORT).show();

                                //구입 실패시
                            } else {
                                //새로고침
                                Intent intent = getActivity().getIntent();
                                getActivity().finish(); //현재 액티비티 종료 실시
                                getActivity().overridePendingTransition(0, 0); //효과 없애기
                                getActivity().startActivity(intent); //현재 액티비티 재실행 실시
                                getActivity().overridePendingTransition(0, 0); //효과 없애기

                                Toast.makeText(getActivity() , "정기권이 존재합니다.\n 마이페이지에서 확인해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                BuyingRequest buyingRequest = new BuyingRequest(charge_name, amount, deadline, user_id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(buyingRequest);

            }
        });



        return rootView;
    }
}
