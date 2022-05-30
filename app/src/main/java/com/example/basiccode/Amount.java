package com.example.basiccode;

//사전결제 계산

//1. Amount에는 entry와 departure를 넣어줌 (단, 입차상태 결제 미완료인 아이들만 또한 같은 날짜라는 가정으로 구현)
// 1시간 이내 무료
// 이후 1000원 + 10분당 100원
// 종일 주차 7500원

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//단 정기권 구매자인 경우 패스 -> 이것도 다른 부분에서 구현 (정기권 마감 기한도 고려해야 함)
public class Amount {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int amount(String entry, String departure){
        entry="2022.04.18. 16:09:44";
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
        //            Date entrydate=dateFormat.parse(entry);
        DateTimeFormatter entrydate=DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss");
        System.out.println("entrydate : "+entry.format(String.valueOf(entrydate)));

        return 0;
    }
}
