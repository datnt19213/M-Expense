package com.example.mexpense.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mexpense.R;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.example.mexpense.mainfragments.TotalFragment;

public class AccountFragment extends Fragment {
    View view;

    Button backToSettings;

    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        backToSettings();

        return view;
    }

    public  void backToSettings() {
        backToSettings = view.findViewById(R.id.backToSettings);
        backToSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
                transaction.replace(R.id.frameMainContainer, SettingsFragment.class, null);
                transaction.commit();
            }
        });
    }
}