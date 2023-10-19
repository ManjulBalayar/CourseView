package com.example.navigation_screen.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        //need to add in logic to get the data and display it
        mText.setValue("User profile here");
    }

    public LiveData<String> getText() {
        return mText;
    }
}