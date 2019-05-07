package com.freshjesh.answerme.Threads;

import android.util.Log;

import com.freshjesh.answerme.Fragments.PlayerlistFragment;
import com.freshjesh.answerme.Model.Game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by joshc on 7/25/2017.
 */

public class ServerSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;

    public ServerSenderThread(Socket socket, Object message) {
        hostThreadSocket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
            if (message instanceof Game) {
                PlayerlistFragment.gameObject = (Game) message;
                Log.d("ServerSenderThread", "reset gameObject");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
