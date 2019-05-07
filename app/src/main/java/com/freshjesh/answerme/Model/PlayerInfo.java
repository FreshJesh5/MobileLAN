package com.freshjesh.answerme.Model;

/**
 * Created by joshc on 7/24/2017.
 */

import java.io.Serializable;

public class PlayerInfo implements Serializable {
    public String username;

    public PlayerInfo(String username) {
        this.username = username;
    }
}
