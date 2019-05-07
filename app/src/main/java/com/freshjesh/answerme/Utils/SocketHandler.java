package com.freshjesh.answerme.Utils;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by joshc on 7/27/2017.
 */

public class SocketHandler {

    private static Socket socket = null;
    private static HashMap<Socket, String> socketUserMap = new HashMap();

    public static void setSocket(Socket socket){
        SocketHandler.socket=socket;
    }

    public static void setSocketMap(Socket socket, String string){
        SocketHandler.socketUserMap.put(socket, string);
    }

    public static Socket getSocket(){
        return socket;
    }

    public static HashMap<Socket, String> getSocketMap(){
        return socketUserMap;
    }
}
