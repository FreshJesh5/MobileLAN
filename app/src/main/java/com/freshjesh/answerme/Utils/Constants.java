package com.freshjesh.answerme.Utils;

import com.freshjesh.answerme.Model.Game;
import com.freshjesh.answerme.Model.Player;

/**
 * Created by joshc on 7/24/2017.
 */

public abstract class Constants {
    public static final int MOVE_FOLD = 0;
    public static final int UPDATE_GAME_NAME = 1;
    public static final int GAME_PLAY = 2;
    public static final int PLAYER_LIST_UPDATE = 3;
    public static final int NEW_GAME = 4;
    public static final int DEAL_CARD = 5;
    public static final String ACTION_KEY = "action";
    public static final String DATA_KEY = "data";
    public static final String MESSAGE_KEY = "message";

    public static boolean isPlayerActive(String userName, Game gameObject) {
        for (int i = 0; i < gameObject.getPlayers().size(); i++) {
            Player play = gameObject.getPlayers().get(i);
            if (play.username.equals(userName) && play.isActive) {
                return true;
            }
        }
        return false;
    }
}