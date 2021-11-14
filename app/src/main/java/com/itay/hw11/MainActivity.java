package com.itay.hw11;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import com.itay.hw11.carView;

public class MainActivity extends AppCompatActivity {

    private carView carView;
    private Handler handler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carView = new carView(this);
        setContentView(carView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        carView.invalidate();
                    }
                });
            }
        },0,Interval);


    }
}
