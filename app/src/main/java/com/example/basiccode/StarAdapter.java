package com.example.basiccode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class StarAdapter extends BaseAdapter {
    ArrayList<StarList> items = new ArrayList<StarList>();
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
        StarList starList = items.get(position);

        //bookmark.xml을 inflate해서 convertview를 참조
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.star, parent, false);
        }

        //화면에 보여질 데이터 참조
        TextView tv_college = convertView.findViewById(R.id.tv_college);
        TextView tv_accept = convertView.findViewById(R.id.tv_accept);

        //데이터를 set
        tv_college.setText(starList.getCollege_name());
        tv_accept.setText(starList.getDate_accept() + "");

        return convertView;
    }

    public void addItem(StarList starList){
        items.add(starList);
    }
}