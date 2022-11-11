package com.example.basiccode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class VisitPayAdapter extends BaseAdapter {
    ArrayList<VisitPayList> items = new ArrayList<VisitPayList>();
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
        VisitPayList visitPayList = items.get(position);

        //bookmark.xml을 inflate해서 convertview를 참조
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.visit, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView visitpay_car_num = convertView.findViewById(R.id.visitpay_car_num);
        TextView visitpay_entry = convertView.findViewById(R.id.visitpay_entry);
        TextView visitpay_departure = convertView.findViewById(R.id.visitpay_departure);
        TextView visitpay_status = convertView.findViewById(R.id.visitpay_status);
        TextView visitpay_amount= convertView.findViewById(R.id.visitpay_amount);

        //데이터를 set
        visitpay_car_num.setText(visitPayList.getCar_num());
        visitpay_entry.setText(visitPayList.getEntry()+"");
        visitpay_departure.setText(visitPayList.getDeparture()+"");
        visitpay_status.setText(visitPayList.getStatus()+"");
        visitpay_amount.setText(visitPayList.getAmount()+"");

        return convertView;
    }

    public void addItem(VisitPayList visitPayList){
        items.add(visitPayList);
    }
}