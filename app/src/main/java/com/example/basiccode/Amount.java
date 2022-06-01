package com.example.basiccode;

//사전결제 계산

//1. Amount에는 entry와 departure를 넣어줌 (단, 입차상태 결제 미완료인 아이들만 또한 같은 날짜라는 가정으로 구현)
// 1시간 이내 무료
// 이후 (기본요금)1000원 + 10분당 100원
// 종일 주차 7500원

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//단 정기권 구매자인 경우 패스 -> 이것도 다른 부분에서 구현 (정기권 마감 기한도 고려해야 함)
public class Amount {

    public static int amount(String entry, String departure){
        int amount =0; //요금
        
        String[] split_entry_time=entry.split(" ");
        System.out.println("entry time : "+split_entry_time[1]);//entry time
        String[] split_departure_time=departure.split(" ");
        System.out.println("departure time : "+split_departure_time[1]); //departure time

        // 1. 시간을 초로 바꾸기
        int entry_H= Integer.parseInt(split_entry_time[1].substring(0,2));
        int entry_M= Integer.parseInt(split_entry_time[1].substring(3,5));
        int entry_S= Integer.parseInt(split_entry_time[1].substring(6,8));

        int total_entry_time=entry_H*60*60+entry_M*60+entry_S; //58184

        int depart_H=Integer.parseInt(split_departure_time[1].substring(0,2));
        int depart_M=Integer.parseInt(split_departure_time[1].substring(3,5));
        int depart_S=Integer.parseInt(split_departure_time[1].substring(6,8));

        int total_depart_time=depart_H*60*60+depart_M*60+depart_S; //62879

        //62879-58184=4695  기본시간 3600분
        int time=total_depart_time-total_entry_time;
        if(time <=3600){
            amount=0; //1시간 이하 무료
            //System.out.println(amount);
        }else { //1시간 이상 유료
            amount=1000; //기본요금
            time=time-3600; //1095
            time=(time/60); //18분 초는 패스
            time=time/10;
            amount=amount+time*100;

            //System.out.println(amount);
        }
        return amount;
    }
}