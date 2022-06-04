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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


//정기권 사용자 즐겨찾기 프래그먼트 페이지
public class DailyStarFragement extends Fragment{

    DailyMainPage dailyMainPage;
    DailyStarFragement dailyStarFragement;
    ListView listView;
    StarAdapter starAdapter;
    AlertDialog dialog;

    ArrayList<String> college_items = new ArrayList<>(Arrays.asList("전체 주차장 목록"));
    String college_name;


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
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragement_star,container,false);

        //StarList참조
        listView = rootView.findViewById(R.id.lv_bookmark);
        starAdapter = new StarAdapter();

        int user_id = Integer.parseInt(getArguments().getString("user_id"));

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 즐겨찾기가 존재하는 경우
                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String college_name = item.getString("college_name");
                            String address = item.getString("address");
                            String detail_address = item.getString("detail_address");
                            String enabled = item.getString("enabled");
                            int date_accept = Integer.parseInt(item.getString("date_accept"));
                            int total_quantity = Integer.parseInt(item.getString("total_quantity"));

                            starAdapter.addItem(new StarList(user_id, college_name, address, detail_address, enabled, date_accept, total_quantity));
                        }
                        listView.setAdapter(starAdapter);

                    } else { // 즐겨찾기가 없는 경우
                        Toast.makeText(getActivity(),"즐겨찾기에 등록된 주차장이 없습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StarRequest starRequest = new StarRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext() );
        queue.add(starRequest);

        Button btn_star_plus = rootView.findViewById(R.id.btn_star_plus);
        btn_star_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("즐겨찾기 등록 버튼 클릭");
                popUpXml(user_id);
            }
        });

        Button btn_star_minus = rootView.findViewById(R.id.btn_star_minus);
        btn_star_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("즐겨찾기 삭제 버튼 클릭");
                popUpXml2(user_id);
            }
        });
        return rootView;
    }

    public void popUpXml(int user_id){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.star_add, null);
        Spinner sp_star_college = view.findViewById(R.id.sp_star_college);

        //college값 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );

                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 즐겨찾기 등록 가능한 주차장이 있는 경우
                        college_items.clear();
                        college_items.add("전체 주차장 목록");

                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String college_name = item.getString("college_name");
                            college_items.add(college_name);
                        }

                    } else { //없는 경우
                        college_items.set(0, "이용 가능한 주차장이 없습니다.");
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
        sp_star_college.setAdapter(adapter);

        //college spineer item 클릭 시
        sp_star_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                college_name = adapter.getItem(position);
                System.out.println("입력될 주차장: " + college_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("즐겨찾기 등록").setView(view);


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

                                if(success) { //즐겨찾기 등록 성공시
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    dialog = builder.setMessage("등록이 완료되었습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                } else{
                                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                    dialog=builder.setMessage("이미 등록된 주차장입니다.")
                                            .setNegativeButton("확인",null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    StarRequest starRequest = new StarRequest(1, user_id, college_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(starRequest);


                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();

    }

    public void popUpXml2 (int user_id){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.star_add, null);
        Spinner sp_star_college = view.findViewById(R.id.sp_star_college);

        //즐겨찾기에 등록된 college값 불러오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("hongchul" + response);
                    JSONObject jsonObject = new JSONObject( response );

                    JSONArray jsonArray =  jsonObject.getJSONArray("response");

                    int length = jsonArray.length();
                    if (length>0) { // 즐겨찾기 등록된 주차장이 있는 경우
                        college_items.clear();
                        college_items.add("즐겨찾기 주차장 목록");

                        for(int i=0;i<length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);

                            String college_name = item.getString("college_name");
                            college_items.add(college_name);
                        }

                    } else { //없는 경우
                        college_items.set(0, "즐겨찾기에 등록된 주차장이 없습니다.");
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StarRequest starRequest = new StarRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(starRequest);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, college_items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sp_star_college.setAdapter(adapter);

        //college spineer item 클릭 시
        sp_star_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                college_name = adapter.getItem(position);
                System.out.println("입력될 주차장: " + college_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("즐겨찾기 삭제").setView(view);


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

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                dialog = builder.setMessage("삭제가 완료되었습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    StarRequest starRequest = new StarRequest(2, user_id, college_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(starRequest);


                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();

    }
}