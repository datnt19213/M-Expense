package com.example.mexpense.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class FeedbackFragment extends Fragment {
    View view;

    Button backToSettings, sendFeedback;
    FragmentTransaction transaction;
    MaterialAlertDialogBuilder alertDialog;
    EditText emailFeedback, descriptionFeedback;

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
        emailFeedback = view.findViewById(R.id.emailFeedback);
        descriptionFeedback = view.findViewById(R.id.feedbackInput);
        sendFeedback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (emailFeedback.getText().toString().isEmpty() || descriptionFeedback.getText().toString().isEmpty()){
                    AlertDialogEmpty();
                }
                else {
                    AlertDialogSuccess();
                }
            }
        });
    }

    public void AlertDialogSuccess(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Successfully Sent");
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
        alertDialog.create();
        alertDialog.show();
    }

    public void AlertDialogEmpty(){
        alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
        alertDialog.setMessage("Fields is empty");
        alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }
}