package com.freshjesh.answerme.Threads;

import com.freshjesh.answerme.Fragments.HostFragment;
import com.freshjesh.answerme.Utils.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by joshc on 7/25/2017.
 */

public class ServerConnectionThread extends Thread {
    static final int SocketServerPORT = 8080;
//    public static HashMap<Socket, String> socketUserMap = new HashMap();
    public static ServerSocket serverSocket;
    public static boolean serverStarted = false;
    public static boolean allPlayersJoined = false;

    public ServerConnectionThread() {

    }

    @Override
    public void run() {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                serverStarted = true;
                while (true) { //might change to !allPlayersJoined
                    Socket socket = serverSocket.accept();
                    if (!allPlayersJoined) {
                        SocketHandler.setSocketMap(socket, null); //moved from line 39
                        Thread socketListenThread = new Thread(new ServerListenerThread(socket));
                        socketListenThread.start();
                        ServerSenderThread sendGameName = new ServerSenderThread(socket, HostFragment.gameName.getText().toString());
                        sendGameName.start();

                        if (SocketHandler.getSocketMap().size() == HostFragment.numberPlayers) {
                            allPlayersJoined = true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
