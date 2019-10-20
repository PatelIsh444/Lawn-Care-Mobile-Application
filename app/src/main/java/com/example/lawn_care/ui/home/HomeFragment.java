package com.example.lawn_care.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lawn_care.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        String userType=com.example.lawn_care.localUserInfo.getUserType();
        View root;
        if(userType.equals("worker")){
            root = inflater.inflate(R.layout.fragment_search_jobs, container, false);
        }
        else if(userType.equals("owner")){
            root = inflater.inflate(R.layout.fragment_search_workers, container, false);
        }
        else{
            //change when admin stuff is added
            root = inflater.inflate(R.layout.fragment_search_workers, container, false);
        }
        //final TextView textView = root.findViewById(R.id.text_jobList);
        /*
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */
        return root;
    }
}