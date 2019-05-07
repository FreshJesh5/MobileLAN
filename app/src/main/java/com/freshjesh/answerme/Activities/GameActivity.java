package com.freshjesh.answerme.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.freshjesh.answerme.Fragments.MainFragment;
import com.freshjesh.answerme.Model.Game;
import com.freshjesh.answerme.R;
import com.freshjesh.answerme.Threads.ClientConnectionThread;
import com.freshjesh.answerme.Utils.ClientHandler;
import com.freshjesh.answerme.Utils.Constants;
import com.freshjesh.answerme.Utils.ServerHandler;
import com.freshjesh.answerme.Utils.SocketHandler;

import java.net.Socket;

/**
 * Created by joshc on 7/27/2017.
 */

public class GameActivity extends AppCompatActivity {

    private static Game gameObject;
    private Socket socket;
    private static Button[] myLayoutGrid = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_layout);

        Intent intent = getIntent();
        gameObject = (Game)intent.getSerializableExtra(Constants.MESSAGE_KEY);
        socket = SocketHandler.getSocket();



        int[] ids={R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9};

        for (int i = 0 ; i < myLayoutGrid.length ; i++)
        {
            myLayoutGrid[i] = (Button) findViewById(ids[i]);
            myLayoutGrid[i].setBackgroundColor(Color.BLUE);
            myLayoutGrid[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentIndex = 0;
                    switch (view.getId()){
                        case R.id.btn1: currentIndex = 0; break;
                        case R.id.btn2: currentIndex = 1; break;
                        case R.id.btn3: currentIndex = 2; break;
                        case R.id.btn4: currentIndex = 3; break;
                        case R.id.btn5: currentIndex = 4; break;
                        case R.id.btn6: currentIndex = 5; break;
                        case R.id.btn7: currentIndex = 6; break;
                        case R.id.btn8: currentIndex = 7; break;
                        case R.id.btn9: currentIndex = 8; break;
                    }
                    gameObject.buttonClicked(currentIndex);
                    updateButton(gameObject.getGrid()[currentIndex], myLayoutGrid[currentIndex]);
                    updateGameForAll();
                }
            });
        }
    }

    public static Game getGameObject(){
        return gameObject;
    }

    public static void setGameObject(Game gameObject){
        GameActivity.gameObject = gameObject;
    }

    public void updateButton(boolean bool, Button button) {
        if(bool){
            button.setBackgroundColor(Color.RED);
        }
        else {button.setBackgroundColor(Color.BLUE);}
    }

    public static void updateGrid (){
        for (int i = 0 ; i < gameObject.getGrid().length ; i++) {
            if(gameObject.getGrid()[i]){
                myLayoutGrid[i].setBackgroundColor(Color.RED);
            }
            else {myLayoutGrid[i].setBackgroundColor(Color.BLUE);}
        }
    }

    public void updateGameForAll() {
        if (ClientConnectionThread.serverStarted) {
            ClientHandler.sendToServer(gameObject);
        } else {
            if (Constants.isPlayerActive(MainFragment.userName.getText().toString(), gameObject)) {
                ServerHandler.sendToAll(gameObject);
            }
        }
    }

}
