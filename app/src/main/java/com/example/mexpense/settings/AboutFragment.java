package com.example.mexpense.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mexpense.R;
import com.example.mexpense.mainfragments.SettingsFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AboutFragment extends Fragment {
    View view;
    FragmentTransaction transaction;

    Button checkUpdateBtn, backAboutToSettings;

    MaterialAlertDialogBuilder alertDialog;
    AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);

        CheckToUpdate();
        BackToSettingTransaction();


        return view;
    }

    public void BackToSettingTransaction(){
        backAboutToSettings = view.findViewById(R.id.backAboutToSettingsBtn);
        backAboutToSettings.setOnClickListener(new View.OnClickListener() {
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

    public void CheckToUpdate(){
        checkUpdateBtn = view.findViewById(R.id.checkUpdateBtn);
        checkUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new MaterialAlertDialogBuilder(getActivity(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
                alertDialog.setMessage("The application is the latest version");
                alertDialog.setNegativeButton(R.string.OK, (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.create();
                alertDialog.show();
            }
        });
    }
}