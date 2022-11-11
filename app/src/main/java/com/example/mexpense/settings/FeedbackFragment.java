package com.example.mexpense.settings;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mexpense.R;
import com.example.mexpense.mainfragments.SettingsFragment;

public class FeedbackFragment extends Fragment {
    View view;

    Button backToSettings, sendFeedback;

    FragmentTransaction transaction;

    AlertDialog.Builder alertDialog;
    AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_feedback, container, false);

        backToSettings();
        sendFeedback();

        return view;
    }

    public void backToSettings() {
        backToSettings = view.findViewById(R.id.backFeedbackToSettings);
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

    public  void sendFeedback() {
        sendFeedback = view.findViewById(R.id.sendFeedback);
        sendFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialogSuccess();
            }
        });
    }

    public void AlertDialogSuccess(){
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Successfully sent");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> {

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
            dialogInterface.dismiss();
        });
        dialog = alertDialog.create();
        alertDialog.show();
    }
}