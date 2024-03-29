package com.example.navigation_screen.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CourseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}