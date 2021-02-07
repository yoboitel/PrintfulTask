package com.yohan.printfultask.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.yohan.printfultask.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView simpleVideoView;
    private NumberProgressBar progressBarInsta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //Initialization
        initViews();

        //Set the video source and start it when it's prepared
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.story_video));
        simpleVideoView.setOnPreparedListener(mediaPlayer -> {
            simpleVideoView.start();

            //Set the progress bar the max duration of the video
            progressBarInsta.setMax(simpleVideoView.getDuration());

            //Start countdown with the video duration length to fill the progress bar according to the video
            new CountDownTimer(simpleVideoView.getDuration(), 1) {
                public void onTick(long millisUntilFinished) {
                    progressBarInsta.setProgress(simpleVideoView.getCurrentPosition());
                }

                public void onFinish() {
                    closeActivity();
                }
            }.start();
        });
    }

    private void initViews() {
        progressBarInsta = findViewById(R.id.number_progress_bar);
        simpleVideoView = findViewById(R.id.videoView);
    }

    public void closeActivity() {
        this.finish();
    }

}
