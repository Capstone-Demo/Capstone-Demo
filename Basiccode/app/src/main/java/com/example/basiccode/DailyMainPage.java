package com.example.basiccode;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.basiccode.databinding.FragementMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DailyMainPage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager=getSupportFragmentManager();
    private DailyMainFragment dailyMainFragment=new DailyMainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        //NavigationBarView navigationBarView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_reservation:
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
//    public void onFragmentChange(int fragmentNum){
//        if(fragmentNum==1){
//            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();
//        }
//    }
}
