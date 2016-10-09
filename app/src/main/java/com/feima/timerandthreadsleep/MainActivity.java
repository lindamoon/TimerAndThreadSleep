package com.feima.timerandthreadsleep;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "test";

    private EditText mEtFrequency;
    private Timer mTimer;
    private boolean running;
    private int mPeriod;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtFrequency = (EditText) findViewById(R.id.et_frequency);
    }

    public void timerSend(View view) {
        int freq = Integer.parseInt(mEtFrequency.getText().toString());
        mPeriod = 1000 / freq;
        mTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "timer send");
            }
        };
        mTimer.schedule(task, 0, mPeriod);
    }

    public void threadSend(View view) {
        int freq = Integer.parseInt(mEtFrequency.getText().toString());
        mPeriod = 1000 / freq;
        running = true;
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(mPeriod);
                    if (running) {
                        Log.e(TAG, "thread send");
                    }
                }
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    public void handlerSend(View view) {
        int freq = Integer.parseInt(mEtFrequency.getText().toString());
        mPeriod = 1000 / freq;

        mRunnable = new Runnable(){
            @Override
            public void run() {
                Log.e(TAG, "handler send");
                mHandler.postDelayed(mRunnable, mPeriod);
            }
        };

    }

    public void stopSend(View view) {
        if (null!=mTimer) {
            mTimer.cancel();
        }

        running = false;

        mHandler.removeCallbacks(mRunnable);

    }
}
