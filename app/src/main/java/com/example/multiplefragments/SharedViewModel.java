package com.example.multiplefragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> data = new MutableLiveData<String>("");

    public void setData(String str) {
        data.setValue(str);
    }

    public LiveData<String> getData() {
        return data;
    }
}
