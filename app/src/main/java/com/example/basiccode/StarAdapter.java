package com.example.basiccode;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;


public class StarAdapter extends BaseAdapter {
    ArrayList<StarList> items = new ArrayList<StarList>();
    Context context;
    AlertDialog dialog;

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

        //star.xml을 inflate해서 convertview를 참조
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

        //정보조회버튼 클릭 이벤트
        Button btn_info = convertView.findViewById(R.id.btn_info);

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpXml(items.get(position).getCollege_name(), items.get(position).getAddress(), items.get(position).getDetail_address(),
                        items.get(position).getEnabled(), items.get(position).getDate_accept(), items.get(position).getTotal_quantity());
            }
        });

        //예약하기버튼 클릭 이벤트
        Button btn_reserve = convertView.findViewById(R.id.btn_reserve);

        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return convertView;
    }

    public void addItem(StarList starList){
        items.add(starList);
    }

    public void popUpXml(String college_name, String address, String detail_address, String enabled, int date_accept, int total_quantity){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.info, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_address = view.findViewById(R.id.tv_address);
        TextView tv_detail = view.findViewById(R.id.tv_detail);
        TextView tv_enabled = view.findViewById(R.id.tv_enabled);
        TextView tv_date_accept = view.findViewById(R.id.tv_date_accept);
        TextView tv_total = view.findViewById(R.id.tv_total);

        tv_name.setText(college_name);
        tv_address.setText(address);
        tv_detail.setText(detail_address);
        tv_enabled.setText("사용가능여부 : " + enabled);
        tv_date_accept.setText("예약가능 : " + date_accept);
        tv_total.setText("총 : " + total_quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}