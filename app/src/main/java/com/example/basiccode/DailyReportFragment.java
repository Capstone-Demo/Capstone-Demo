package com.example.basiccode;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.type.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//정기권 사용자 신고 프래그먼트 페이지
public class DailyReportFragment extends Fragment {

    DailyMainPage dailyMainPage;

    private TextView tv_report_date;
    private EditText et_report_college, et_report_num;
    private RadioGroup rg_cause;
    private Button btn_report;


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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_report, container, false);

        //user_id값 가져오기
        int user_id = Integer.parseInt(getArguments().getString("user_id"));

        // 아이디 값 찾아주기
        tv_report_date = rootView.findViewById(R.id.tv_report_date);
        et_report_college = rootView.findViewById(R.id.et_report_college);
        et_report_num = rootView.findViewById(R.id.et_report_num);
        rg_cause = rootView.findViewById(R.id.rg_cause);
        btn_report = rootView.findViewById(R.id.btn_report);

        //날짜 계산
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        cal.setTime(mDate);
        tv_report_date.setText(simpleDate.format(cal.getTime())); //현재 날짜 TextView에 저장
        System.out.println(mDate);

        //신고하기 버튼 클릭 시
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String report_date = tv_report_date.getText().toString();

                String report_college = et_report_college.getText().toString();
                String car_number = et_report_num.getText().toString();
                String cause = "신고사유";

                //radio선택 가져오기
                int rb_id = rg_cause.getCheckedRadioButtonId();

                switch (rb_id){
                    case R.id.radio_button_1:{
                        cause = "예약위반";
                        break;
                    }
                    case R.id.radio_button_2:{
                        cause = "불법주차";
                        break;
                    }
                    case R.id.radio_button_3:{
                        cause = "주차방해";
                        break;
                    }
                    case R.id.radio_button_4:{
                        cause = "부당사용";
                        break;
                    }
                }

                System.out.println("cause : " + cause);
                System.out.println("college_name : " + report_college);
                System.out.println("car_number : " + car_number);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if (success) {

                                Toast.makeText(getActivity() , String.format("신고 완료"), Toast.LENGTH_SHORT).show();

                                //신고실패
                            } else {
                                Toast.makeText(getActivity() , "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReportRequest reportRequest = new ReportRequest(user_id, report_college, car_number, cause, report_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(reportRequest);

            }
        });

        return rootView;
    }
}
