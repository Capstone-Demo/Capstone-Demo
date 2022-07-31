package com.example.basiccode;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainPage extends AppCompatActivity {

    Button visitbutton; //일반결제
    Button managerbutton;   //관리자
    Button dailybutton; //정기권이용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        //푸시알림
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d("IDService", msg);
                            Log.d("Ipconfig", "http://"+R.string.ipconfig+"/login.php");
                        }
                    });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        visitbutton=findViewById(R.id.visitbutton);
        managerbutton=findViewById(R.id.managerbutton);
        dailybutton=findViewById(R.id.dailybutton);


        visitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VisitPayPage.class);
                startActivity(intent);
            }
        });
        managerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ManagerListPage.class); //출차입차 카메라
                startActivity(intent);
            }
        });
        dailybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DailyLogin.class);
                startActivity(intent);
            }
        });
    }
}