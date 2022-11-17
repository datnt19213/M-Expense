package com.example.mexpense.mainfragments;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.settings.AboutFragment;
import com.example.mexpense.settings.AccountFragment;
import com.example.mexpense.settings.FeedbackFragment;

public class SettingsFragment extends Fragment {
    View view;
    FragmentTransaction transaction;

    TextView accountBtn, feedbackBtn, aboutBtn, logoutBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        AccountSettingsTransaction();
        FeedbackSettingsTransaction();
        AboutSettingsTransaction();

        return view;
    }

    public void AccountSettingsTransaction(){
        accountBtn = view.findViewById(R.id.accountSettingsBtn);
        accountBtn.setOnClickListener(new View.OnClickListener(){

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
                transaction.replace(R.id.frameMainContainer, AccountFragment.class, null);
                transaction.commit();
            }
        });
    }

    public void FeedbackSettingsTransaction(){
        feedbackBtn = view.findViewById(R.id.feedbackSettingsBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener(){

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
                transaction.replace(R.id.frameMainContainer, FeedbackFragment.class, null);
                transaction.commit();
            }
        });
    }

    public void AboutSettingsTransaction(){
        aboutBtn = view.findViewById(R.id.aboutSettingsBtn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
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
                transaction.replace(R.id.frameMainContainer, AboutFragment.class, null);
                transaction.commit();
            }
        });
    }
}