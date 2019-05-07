package com.freshjesh.answerme.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joshc on 7/24/2017.
 */

public class Game implements Serializable {
    private int numberOfPlayer;
    public ArrayList<Player> players;
    public String gameName;
    public String senderUsername;
    private boolean[] myGameGrid = new boolean[9];


    public Game(ArrayList<String> usernames, String gameName) {
        this.senderUsername = null;
        this.players = new ArrayList();
        this.numberOfPlayer = usernames.size();
        this.gameName = gameName;
        if (usernames.size() > 6) {
//            usernames.remove(usernames.size() - 1);
            throw new IllegalArgumentException("Number of players above the allowed limit (6)");
        }

        for (int i = 0; i < numberOfPlayer; i++) {
            players.add(new Player(i + 1, usernames.get(i), true));
        }
//        usernames.remove(usernames.size() - 1);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void buttonClicked (int i ) {
        myGameGrid[i] = !myGameGrid[i];
        Log.d("Game", "buttonClicked");
    }

    public boolean[] getGrid() {return myGameGrid;}

}
