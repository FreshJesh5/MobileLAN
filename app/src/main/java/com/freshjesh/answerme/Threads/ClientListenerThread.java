package com.freshjesh.answerme.Threads;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.freshjesh.answerme.Fragments.ClientFragment;
//import com.freshjesh.answerme.Fragments.MainFragment;
import com.freshjesh.answerme.Model.Game;
import com.freshjesh.answerme.Utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by joshc on 7/25/2017.
 */

public class ClientListenerThread extends Thread {

    Socket socket;

    ClientListenerThread(Socket soc) {
        socket = soc;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream objectInputStream;
                InputStream inputStream = null;
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Bundle data = new Bundle();
                Object serverObject = (Object) objectInputStream.readObject();
                if (serverObject != null) {
                    if (serverObject instanceof String) {
                        data.putSerializable(Constants.DATA_KEY, (String) serverObject);
                        data.putInt(Constants.ACTION_KEY, Constants.UPDATE_GAME_NAME);
                    }
                    if (serverObject instanceof Game) {
                        data.putSerializable(Constants.DATA_KEY, (Game) serverObject);
                    }
                    Message msg = new Message();
                    msg.setData(data);
                    ClientFragment.clientHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}