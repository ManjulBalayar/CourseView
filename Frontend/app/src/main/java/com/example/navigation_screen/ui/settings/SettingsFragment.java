package com.example.navigation_screen.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.navigation_screen.PreferencesUtil;
import com.example.navigation_screen.R;
import com.example.navigation_screen.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the switch
        Switch darkModeSwitch = root.findViewById(R.id.switch_dark_mode);

        // Set the switch to the saved preference
        boolean isDarkModeEnabled = PreferencesUtil.getDarkModePreference(getContext());
        darkModeSwitch.setChecked(isDarkModeEnabled);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                showConfirmationDialog(isChecked);
            }
        });

        return root;
    }

    private void showConfirmationDialog(boolean isEnabled) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Action")
                .setMessage("This action requires you to log in again. Continue?")
                .setPositiveButton("Continue", (dialog, which) -> setDarkMode(isEnabled))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void setDarkMode(boolean isEnabled) {
        // Save the user preference
        PreferencesUtil.saveDarkModePreference(getContext(), isEnabled);

         if (isEnabled) {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
         } else {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
         }
    }
}