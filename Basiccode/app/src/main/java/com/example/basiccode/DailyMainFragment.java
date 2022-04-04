package com.example.basiccode;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//정기권 사용자 페이지 프래그먼트 페이지
public class DailyMainFragment extends Fragment {


    //BottomNavigationView bottomNavigationView;
    //FragmentManager fragmentManager=getSupportFragementManager();
    //DailyReservationPage dailyReservationPage;

    //private DailyMainFragment dailyMainFragment=new DailyMainFragment();

    DailyMainPage dailyMainPage;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dailyMainPage = (DailyMainPage) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragement_main, container, false);
        //return inflater.inflate(R.layout.fragement_main,container,false);
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragement_main,container,false);
        return rootView;
    }
}
