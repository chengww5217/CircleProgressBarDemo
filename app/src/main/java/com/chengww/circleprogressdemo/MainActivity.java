package com.chengww.circleprogressdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    CircleProgressBar cpProgress;
    TextView tvStatus;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initEvent();
    }

    private void initEvent() {
        cpProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleProgressBar.Status status = cpProgress.getStatus();
                switch (status) {
                    case Waiting:
                        onFakeLoading();
                        break;
                    case Loading:
                        onLoadPaused();
                        break;
                    case Pause:
                        onFakeLoading();
                        break;
                    case Error:
                        onFakeLoading();
                        break;
                    case Finish:
                        // Repeat
                        onLoadWaiting();
                        break;
                }
            }
        });
    }

    private void onLoadWaiting() {
        cpProgress.setProgress(0);
        cpProgress.setStatus(CircleProgressBar.Status.Waiting);
        tvStatus.setText(R.string.waiting);
    }

    private void onLoadFinished() {
        timer.cancel();
        cpProgress.setStatus(CircleProgressBar.Status.Finish);
        tvStatus.setText(R.string.finish);
    }

    private void onLoadPaused() {
        timer.cancel();
        cpProgress.setStatus(CircleProgressBar.Status.Pause);
        tvStatus.setText(R.string.pause);
    }

    private void onFakeLoading() {
        cpProgress.setStatus(CircleProgressBar.Status.Loading);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int progress = cpProgress.getProgress();
                if (progress >= 100) {
                    onLoadFinished();
                } else {
                    cpProgress.setProgress(++progress);
                    tvStatus.setText(String.format(getString(R.string.loading), progress + "%"));
                }
            }
        };
        timer.schedule(task, 0, 100);
    }

    private void bindView() {
        cpProgress = findViewById(R.id.cp_progress);
        tvStatus = findViewById(R.id.tv_status);
    }
}
