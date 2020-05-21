package com.yiann0s.mytapmetronome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//todo check this link https://developer.android.com/training/connect-devices-wirelessly/wifi-direct#kotlin
public class MainActivity extends AppCompatActivity {

    private Button tapButton;
    private boolean flag = true;
    private Long startTime, endTime, diff;
    private TextView diffTextView;
    private final String TAG = "TEST.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tapButton = findViewById(R.id.tap_button);

        diffTextView = findViewById(R.id.diff_text_view);

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    tapButton.setText("Stop");
                    startTime = System.currentTimeMillis();
                    Log.d(TAG, "onClick: startTime " + startTime);
                    flag = false;
                } else {
                    tapButton.setText("Tap");
                    endTime = System.currentTimeMillis();
                    flag = true;
                    Log.d(TAG, "onClick: endTime " + endTime);
                    diff = endTime - startTime;
                    Log.d(TAG, "onClick: diff " + diff + " ms ");
                    Double d = diff.doubleValue();
                    Log.d(TAG, "onClick: d " + d/1000 + " second");
                    Double d_sec = d / 1000;
                    Double delay = d_sec /16;
                    Log.d(TAG, "onClick: delay" + delay);
                    if ( diff > 0 ){
                        diffTextView.setText(getString(R.string.diff_text).replace("{x}",String.valueOf(d)).replace("{y}",String.valueOf(delay)));
                        diffTextView.setText("delay " + String.valueOf(delay));
                    }
                }

            }
        });



    }
}
