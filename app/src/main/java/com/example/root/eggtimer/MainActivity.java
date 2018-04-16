package com.example.root.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isActive = false;
    int max = 600;
    int currentProgress = 30;

    TextView showTimerTextView;
    SeekBar controlTimerSeekbar;
    Button playPauseButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        showTimerTextView.setText("0:30");
        controlTimerSeekbar.setProgress(30);
        countDownTimer.cancel();
        playPauseButton.setText("Go!");
        controlTimerSeekbar.setEnabled(true);
        isActive = false;
    }

    public void updateTimer(int secondLeft) {
        int minute = secondLeft / 60;
        int sec = secondLeft - minute * 60;

        String secondString = Integer.toString(sec);

        if (sec <= 9) {
            secondString = "0" + secondString;
        }

        showTimerTextView.setText(Integer.toString(minute) + ":" + secondString);
    }


    // play/pause timer onClick button
    public void playPauseTimer(View view) {

        if(!isActive) {

            isActive = true;
            controlTimerSeekbar.setEnabled(false);
            playPauseButton.setText("Stop");

            countDownTimer = new CountDownTimer(controlTimerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.crack);
                    mplayer.start();


                }
            }.start();

        } else {
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTimerTextView = findViewById(R.id.timerTextView);

        controlTimerSeekbar = findViewById(R.id.timerControlSeekBar);

        playPauseButton = findViewById(R.id.playPauseButton);

        controlTimerSeekbar.setMax(max);
        controlTimerSeekbar.setProgress(currentProgress);

        controlTimerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
