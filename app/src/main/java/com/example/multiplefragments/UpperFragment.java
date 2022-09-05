package com.example.multiplefragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multiplefragments.databinding.FragmentUpperBinding;

public class UpperFragment extends Fragment {
    private FragmentUpperBinding fragmentUpperBinding;
    private MyListeners myListeners;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentUpperBinding = FragmentUpperBinding.inflate(inflater, container, false);
        View view = fragmentUpperBinding.getRoot();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myListeners = (MyListeners) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fragmentUpperBinding.button.setOnClickListener(setTimerClicked);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentUpperBinding = null;
    }

    private View.OnClickListener setTimerClicked = v->{
        String data = String.valueOf(fragmentUpperBinding.editTextNumber.getText());
//            usingBroadcastReceiver(data);
//            usingSingleton(data);
        usingSharedViewModel(data);
//            usingBundles(data);
//            usingmethod(data);
    };


    //method to send data from upper fragment to lower fragment using broadcast receiver
    private void usingBroadcastReceiver(String data) {
        Intent intent = new Intent();
        intent.setAction("com.example.multiplefragments.MY_NOTIFICATION");
        intent.putExtra("data", data);
        getActivity().sendBroadcast(intent);
    }

    //method to send data from upper fragment to lower fragment using singleton class
    private void usingSingleton(String data) {
        Singleton s = Singleton.getInstance();
        s.setData(data);

        myListeners.btnClicked();
    }

    //method to send data from upper fragment to lower fragment using shared viewmodel
    private void usingSharedViewModel(String data) {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setData(data);
    }

    //method to send bundle from upper fragment to lower fragment
    private void usingBundles(String data) {
        Bundle args = new Bundle();
        args.putString("timer-text", data);

        LowerFragment lowerFragment = new LowerFragment();
        lowerFragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_2, lowerFragment)
                .commit();
    }

    //method to send string data from upper fragment -> main activity -> lower fragment
    private void usingmethod(String data) {
        myListeners.sendStr(data);
    }


}
