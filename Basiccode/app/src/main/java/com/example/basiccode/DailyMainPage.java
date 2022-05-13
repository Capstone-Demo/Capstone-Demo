package com.example.basiccode;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.basiccode.databinding.FragementMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DailyMainPage extends AppCompatActivity {
    private DailyMainFragment dailyMainFragment;
    private DailyMypageFragement dailyMypageFragement;
    private DailybuyingFragement dailybuyingFragement;
    private DailyStarFragement dailyStarFragement;


    FragmentManager fragmentManager=getSupportFragmentManager();

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_main);

        dailyMainFragment=new DailyMainFragment();
        dailyMypageFragement=new DailyMypageFragement();
        dailybuyingFragement=new DailybuyingFragement();
        dailyStarFragement=new DailyStarFragement();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, dailyMainFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_reservation:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        //getSupportActionBar().setTitle("예약하기");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();
                        return true;
                    case R.id.bottom_buying:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        //getSupportActionBar().setTitle("정기권구매");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailybuyingFragement).commit();
                        return true;
                    case R.id.bottom_star:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        //getSupportActionBar().setTitle("즐겨찾기");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyStarFragement).commit();
                        return true;
                    case R.id.bottom_mypage:
                        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        //getSupportActionBar().setTitle("마이페이지");
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMypageFragement).commit();
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onFragmentChange(int fragmentNum){
        if(fragmentNum==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyStarFragement).commit();
        }else if(fragmentNum==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailybuyingFragement).commit();
        }else if(fragmentNum==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMainFragment).commit();
        }else if(fragmentNum==4){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,dailyMypageFragement).commit();
        }
    }
}
