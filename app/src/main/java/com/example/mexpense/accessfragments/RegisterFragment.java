package com.example.mexpense.accessfragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mexpense.R;
import com.example.mexpense.mainfragments.TotalFragment;


public class RegisterFragment extends Fragment {
    View view;

    TextView loginLinkButton;

    FragmentTransaction transaction;

    public RegisterFragment(){
        //Empty constructor
    }

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
        view = inflater.inflate(R.layout.fragment_register, container, false);

        LoginTransaction();

        return view;
    }

    public void LoginTransaction(){
        loginLinkButton = view.findViewById(R.id.linkLogin);
        loginLinkButton.setOnClickListener(new View.OnClickListener(){
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
                transaction.replace(R.id.frameMainContainer, LoginFragment.class, null);
                transaction.commit();
            }
        });
    }
}