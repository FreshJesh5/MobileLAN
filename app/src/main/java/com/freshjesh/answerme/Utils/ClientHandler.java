package com.freshjesh.answerme.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.freshjesh.answerme.Activities.GameActivity;
import com.freshjesh.answerme.Fragments.ClientFragment;
import com.freshjesh.answerme.Model.Game;
import com.freshjesh.answerme.Threads.ClientConnectionThread;
import com.freshjesh.answerme.Threads.ClientSenderThread;

/**
 * Created by joshc on 7/24/2017.
 */

public class ClientHandler extends Handler {

    Bundle messageData;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        messageData = msg.getData();
        int value = messageData.getInt(Constants.ACTION_KEY);
        Object clientObject = messageData.getSerializable(Constants.DATA_KEY);
        if (value == Constants.UPDATE_GAME_NAME) {
            String gameName = (String) clientObject;
            if (ClientFragment.gameName != null)
            {
                ClientFragment.gameName.setText(gameName);
            }
        }

        if (clientObject instanceof Game) {
            if (GameActivity.getGameObject() != null) {
                if (((Game) clientObject).senderUsername.equals(String.valueOf(Constants.NEW_GAME))) {
                    ClientSenderThread.isActive = true;
//not my gray                    ((Game) clientObject).senderUsername = "";
                }
                GameActivity.setGameObject((Game) clientObject);
                GameActivity.updateGrid();
                Log.d("ClientHandler", "updateGrid");
//                GameFragment.updatePlayerStatus();
//                GameFragment.updateTable();
//                GameFragment.updateHand();
            } else {
                ClientFragment.gameObject = (Game) clientObject;
            }
        }
    }

    public static void sendToServer(Object gameObject) {
        ClientSenderThread sendGameChange = new ClientSenderThread(SocketHandler.getSocket(), gameObject);
        sendGameChange.start();
    }

}
