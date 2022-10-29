package com.example.mexpense.accessfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mexpense.R;

public class LoginFragment extends Fragment {

    View view;

    public LoginFragment() {
        //Empty Constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        getParentFragmentManager().setFragmentResultListener("loginForm", this, new FragmentResultListener()
        {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

            }
        });

        return view;

    }
}