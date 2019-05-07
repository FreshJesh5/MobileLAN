package com.freshjesh.answerme.Model;

/**
 * Created by joshc on 7/25/2017.
 */

import java.io.Serializable;

public class Player implements Serializable {
    public int playerID;
    public String username;
    public boolean isActive;

    public Player(String username) {
        this.username = username;
    }

    public Player(int playerID, String username, boolean isActive) {
        this.playerID = playerID;
        this.username = username;
        this.isActive = isActive;
    }
}