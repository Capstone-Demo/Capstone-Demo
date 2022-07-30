package com.example.basiccode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {
    ArrayList<ReportList> items = new ArrayList<ReportList>();
    Context context;

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
        ReportList reportList = items.get(position);

        //report.xml을 inflate해서 convertview를 참조
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.report, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView tv_report_date = convertView.findViewById(R.id.tv_report_date);
        TextView tv_report_college = convertView.findViewById(R.id.tv_report_college);
        TextView tv_car_number = convertView.findViewById(R.id.tv_car_number);
        TextView tv_cause = convertView.findViewById(R.id.tv_cause);

        //데이터를 set
        tv_report_date.setText(reportList.getReport_date());
        tv_report_college.setText(reportList.getReport_college());
        tv_car_number.setText(reportList.getCar_number());
        tv_cause.setText(reportList.getCause());


        //처리완료 버튼 클릭 이벤트
        Button btn_com = convertView.findViewById(R.id.btn_com);

        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

    public void addItem(ReportList reportList){
        items.add(reportList);
    }

}
