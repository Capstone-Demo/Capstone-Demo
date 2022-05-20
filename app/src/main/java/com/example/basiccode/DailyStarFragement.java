package com.example.basiccode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//정기권 사용자 즐겨찾기 프래그먼트 페이지
public class DailyStarFragement extends Fragment {

    DailyMainPage dailyMainPage;
    ListView listView;
    StarAdapter starAdapter;

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
        //adapter참조
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
                            int date_accept = Integer.parseInt(item.getString("date_accept"));

                            starAdapter.addItem(new StarList(college_name, date_accept));
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

        return rootView;
    }
}