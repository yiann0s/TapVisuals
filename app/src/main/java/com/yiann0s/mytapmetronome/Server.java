package com.yiann0s.mytapmetronome;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private final String TAG = "Server";

    protected Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
        Log.d(TAG, "New client connected from : " + socket.getInetAddress().getHostAddress());
        start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String request;
            while ((request = br.readLine()) != null) {
                Log.d(TAG, "Message received: " + request);
                request += '\n';
                out.write(request.getBytes());
            }

        } catch (IOException ex) {
            Log.d(TAG, "Unable to get streams from client, with error" + ex.getLocalizedMessage());
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                Log.d(TAG, "run: exception " + ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }

//    public void main() {
//        System.out.println("SocketServer Example");
//        ServerSocket server = null;
//        try {
//            server = new ServerSocket(PORT_NUMBER);
//            while (true) {
//                /*
//                 * create a new {@link SocketServer} object for each connection
//                 * this will allow multiple client connections
//                 */
//                new Server(server.accept());
//            }
//        } catch (IOException ex) {
//            Log.d(TAG, "main: Unable to start server, with error : " + ex.getLocalizedMessage());
//        } finally {
//            try {
//                if (server != null)
//                    server.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                Log.d(TAG, "main: Unable to start server, with error : " + ex.getLocalizedMessage());
//            }
//        }
//    }
}