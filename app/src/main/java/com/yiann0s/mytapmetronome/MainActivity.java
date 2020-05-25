package com.yiann0s.mytapmetronome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
//                frequencySniffing();
//                retroGetUsers("64394");
                String host = "192.168.1.91";
                //String host = "127.0.0.1";
//                StartClient(host,8081);
                new RetrieveFeedTask().execute();
            }
        });



    }

    public void StartClient(String serverHostname, int port) {
        try {

            Log.i(TAG, "StartClient: Connecting to host " + serverHostname + " on port " + port + ".");

            Socket echoSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                echoSocket = new Socket(serverHostname, 8081);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            } catch (UnknownHostException e) {
                Log.e(TAG, "Client: Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                Log.e(TAG, "Client: " + e.getLocalizedMessage() );
                System.exit(1);
            }

            /** {@link UnknownHost} object used to read from console */
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("client: ");
                String userInput = "hahahaha";
                /** Exit on 'q' char sent */
                if ("q".equals(userInput)) {
                    break;
                }
                out.println(userInput);
                System.out.println("server: " + in.readLine());
            }

            /** Closing all the resources */
            out.close();
            in.close();
            stdIn.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Client 2 " + e.getLocalizedMessage() );
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Integer,Integer> {

        private Exception exception;

        protected Integer doInBackground(String... urls) {
            try {
                String host = "192.168.1.91";
                //String host = "127.0.0.1";
                StartClient(host, 8081);
                return 1;
            } catch (Exception e) {
                this.exception = e;
                Log.e(TAG, "doInBackground: " + e.getLocalizedMessage());
                return 0;
            }
        }

        protected void onPostExecute(String result) {
        }
    }


    public void frequencySniffing(){
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
}
