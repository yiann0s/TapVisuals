package com.yiann0s.mytapmetronome;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private final String TAG = "Client";

    public Client(String host, int port) {
        try {
            String serverHostname = new String("127.0.0.1");

            Log.d(TAG, "Connecting to host " + serverHostname + " on port " + port + ".");

            Socket echoSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                echoSocket = new Socket(serverHostname, 8081);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            } catch (UnknownHostException e) {
                Log.d(TAG,"Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                Log.d(TAG,"Unable to get streams from server");
                System.exit(1);
            }

            /* {@link UnknownHost} object used to read from console */
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                Log.d(TAG,"client: ");
                String userInput = stdIn.readLine();
                /** Exit on 'q' char sent */
                if ("q".equals(userInput)) {
                    break;
                }
                out.println(userInput);
                Log.d(TAG,"server: " + in.readLine());
            }

            /* Closing all the resources */
            out.close();
            in.close();
            stdIn.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
