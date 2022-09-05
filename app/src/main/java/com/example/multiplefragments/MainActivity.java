package com.example.multiplefragments;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MyListeners {
    private UpperFragment upperFragment;
    private LowerFragment lowerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragments();
        }
    }

    private void loadFragments() {
        upperFragment = new UpperFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_1, upperFragment, null).commit();

        lowerFragment = new LowerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_2, lowerFragment, null).commit();
    }


    @Override
    public void sendStr(String str) {
        lowerFragment.setReceivedData(str);
    }

    @Override
    public void btnClicked() {
        lowerFragment.setUsingSingleton();
    }

}