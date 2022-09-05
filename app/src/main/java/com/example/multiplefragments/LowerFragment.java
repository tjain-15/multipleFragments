package com.example.multiplefragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multiplefragments.databinding.FragmentLowerBinding;

import java.util.Timer;
import java.util.TimerTask;

public class LowerFragment extends Fragment {
    private FragmentLowerBinding fragmentLowerBinding;
    private SharedViewModel sharedViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentLowerBinding = FragmentLowerBinding.inflate(inflater, container, false);
        View view = fragmentLowerBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setUsingBroadcastReceiver();
//        setUsingBundle();
        setUsingSharedViewModel();

        fragmentLowerBinding.button2.setOnClickListener(startTimerClicked);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentLowerBinding = null;
    }

    private View.OnClickListener startTimerClicked = v -> {
        runCountDownTimer();
//            runTimer();
//            runRunnable();
//            runUsingAsyncTask();
//            usingBackgroundThread();
    };

    private void setUsingBroadcastReceiver() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setTimerText(getActivity().getIntent().getStringExtra("data"));
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("com.example.multiplefragments.MY_NOTIFICATION"));

    }

    private void setUsingBundle() {
        String value = null;

        if (getArguments() != null)
            value = getArguments().getString("timer-text");

        if (value != null)
            setTimerText(value);
    }


    public void setReceivedData(String str) {
        setTimerText(str);
    }

    public void setUsingSingleton() {
        Singleton s = Singleton.getInstance();

        setTimerText(s.getData());
    }

    private void setUsingSharedViewModel() {
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            setTimerText(data);
        });
    }

    private String getTimerText() {
        return String.valueOf(fragmentLowerBinding.textView.getText());
    }

    private void setTimerText(String data) {
        fragmentLowerBinding.textView.setText(data);
    }

    private void runTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int timeLeft = Integer.parseInt(getTimerText());


            @Override
            public void run() {
                Log.i("denn", Thread.currentThread().getName());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("denn", Thread.currentThread().getName());

                        setTimerText(String.valueOf(timeLeft));
                        if (timeLeft == 0) {
                            setTimerText("Timer completed");
                            cancel();
                        }
                        timeLeft -= 1;
                    }
                });

            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void runCountDownTimer() {
        long timer = Integer.parseInt(getTimerText()) * 1000;
        CountDownTimer countDownTimer = new CountDownTimer(timer, 1000) {
            int timeLeft = Integer.parseInt(getTimerText());


            @Override
            public void onTick(long l) {
                setTimerText(String.valueOf(timeLeft--));
            }

            @Override
            public void onFinish() {
                setTimerText("Timer completed");

            }
        };
        countDownTimer.start();
    }

    private void runRunnable() {
        Handler handler = new Handler();

        Runnable task = new Runnable() {
            int timeLeft = Integer.parseInt(getTimerText());

            boolean paused = false;

            @Override
            public void run() {
                if (!paused) {
                    setTimerText(String.valueOf(timeLeft));
                    Log.i("denn", Thread.currentThread().getName());

                    if (timeLeft == 0) {
                        setTimerText("Timer completed");
                        paused = true;
                    }
                    timeLeft -= 1;
                    handler.postDelayed(this, 1000);
                }
            }
        };
        task.run();
    }

    private void runUsingAsyncTask() {
        AsyncTask.execute(new Runnable() {
            int timeLeft = Integer.parseInt(getTimerText());


            @Override
            public void run() {
                //TODO your background code
                while (timeLeft > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTimerText(String.valueOf(timeLeft--));
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void usingBackgroundThread() {
        Runnable runnable = new Runnable() {
            int timeLeft = Integer.parseInt(getTimerText());

            @Override
            public void run() {
                while (timeLeft >= 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTimerText(String.valueOf(timeLeft--));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

}
