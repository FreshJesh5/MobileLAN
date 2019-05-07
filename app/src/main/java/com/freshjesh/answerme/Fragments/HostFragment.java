package com.freshjesh.answerme.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.freshjesh.answerme.R;

import com.freshjesh.answerme.Threads.ServerConnectionThread;
import com.freshjesh.answerme.Utils.ServerHandler;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by joshc on 7/24/2017.
 */

public class HostFragment extends Fragment {

    public static ServerHandler serverHandler;
    public static MaterialEditText gameName;
    public MaterialEditText numberOfPlayers;
    public static int numberPlayers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serverHandler = new ServerHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.server_host_game, container, false);
        Button startGame = (Button) rootView.findViewById(R.id.startGame);
        gameName = (MaterialEditText) rootView.findViewById(R.id.gameName);
        numberOfPlayers = (MaterialEditText) rootView.findViewById(R.id.numberOfPlayers);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameName.getText() != null && numberOfPlayers.getText() != null
                        && numberOfPlayers.getText().toString().trim().length() > 0 && gameName.getText().toString().trim().length() > 0) {
                    numberPlayers = Integer.valueOf(numberOfPlayers.getText().toString());
                    if (numberPlayers > 5 || numberPlayers < 1) {
                        Toast.makeText(getActivity(), "Maximum 5 players allowed ", Toast.LENGTH_SHORT).show();
                    } else {
                        ServerConnectionThread startServerThread = new ServerConnectionThread();
                        startServerThread.start();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new PlayerlistFragment()).addToBackStack(PlayerlistFragment.class.getName())
                                .commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "Missing game name or number of players", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

}
