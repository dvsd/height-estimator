package io.github.dvsd.heightestimator;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.TimerTask;

import static io.github.dvsd.heightestimator.R.id.timer;

public class MainActivity extends AppCompatActivity {

    public TextView heightTextView;
    public TextView timeTextView;
    public Button startButton;
    public boolean startButtonPressed;
    public long startTime;
    long timeInMilliseconds = 0L;
    float secs;
    private Handler customHandler = new Handler();
    public double height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize views
        heightTextView = (TextView) findViewById(R.id.height);
        timeTextView = (TextView) findViewById(timer);
        startButton = (Button) findViewById(R.id.startButton);

        //timer button set to not touched
        startButtonPressed = false;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!startButtonPressed) {
                    startButtonPressed = true;
                    startButton.setText("Stop");
                    heightTextView.setText("0ft");
                    //begin timer runnable
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                } else {
                    customHandler.removeCallbacks(updateTimerThread);
                    startButtonPressed = false;
                    startButton.setText("Start");
                    height = Math.round((int) (32/2) * ((Math.pow(secs, 2))));
                    heightTextView.setText(String.valueOf(height) + "ft");
                }
            }
        });


    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            secs = (float) timeInMilliseconds / 1000;
            timeTextView.setText("Time: " + String.valueOf(secs) + "s");
            customHandler.postDelayed(this, 0);
        }

    };
}
