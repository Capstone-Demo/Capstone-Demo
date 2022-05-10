package com.example.basiccode;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.basiccode.databinding.FragementMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DailyMainPage extends AppCompatActivity {
    DailyMainFragment dailyMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_main);

        dailyMainFragment=new DailyMainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,dailyMainFragment).commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_reservation:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        Toast.makeText(getApplicationContext(),"예약선택",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
