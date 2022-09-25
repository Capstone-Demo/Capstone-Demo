package com.example.basiccode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraTime {
    public static String Cameratime(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", java.util.Locale.getDefault());
        String timestamp=dateFormat.format(new Date());
        return timestamp;
    }
}
