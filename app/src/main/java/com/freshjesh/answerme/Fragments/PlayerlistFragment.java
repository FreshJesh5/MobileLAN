package com.freshjesh.answerme.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.freshjesh.answerme.Activities.GameActivity;
import com.freshjesh.answerme.Adapters.PlayerAdapter;
import com.freshjesh.answerme.R;
import com.freshjesh.answerme.Model.Game;
import com.freshjesh.answerme.Threads.ServerConnectionThread;
import com.freshjesh.answerme.Utils.Constants;
import com.freshjesh.answerme.Utils.ServerHandler;

import java.util.ArrayList;

/**
 * Created by joshc on 7/25/2017.
 */

public class PlayerlistFragment extends Fragment{

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    public static Game gameObject;
    public static ArrayList<String> deviceList = new ArrayList();

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mPlayerList;
    public static PlayerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.player_join_list, container, false);
        rootView.setTag(TAG);
        Button gameSettings = (Button) rootView.findViewById(R.id.gameSettings);
        Button playGame = (Button) rootView.findViewById(R.id.playGame);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

//        gameSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, new SettingsFragment()).addToBackStack(SettingsFragment.class.getName())
//                        .commit();
//            }
//        });

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ServerConnectionThread.allPlayersJoined) {
                    try {
                        initializeGame();
                        Intent activityIntent = new Intent(getActivity(), GameActivity.class);
                        activityIntent.putExtra(Constants.MESSAGE_KEY, gameObject);
                        startActivity(activityIntent);

//                        GameFragment gameFragment = new GameFragment();
//                        gameFragment.setParameters(gameObject, null);
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.container, gameFragment).addToBackStack(GameFragment.class.getName())
//                                .commit();
                    } catch (IllegalArgumentException exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Waiting for all players to Join the game", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPlayerList = (RecyclerView) rootView.findViewById(R.id.gameList);


        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new PlayerAdapter(deviceList);
        mPlayerList.setAdapter(mAdapter);
        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mPlayerList.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mPlayerList.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mPlayerList.setLayoutManager(mLayoutManager);
        mPlayerList.scrollToPosition(scrollPosition);
    }

    @Override
    //for jumping to and back from settings
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void initializeGame() {
        deviceList.add(MainFragment.userName.getText().toString());

        gameObject = new Game(deviceList, HostFragment.gameName.getText().toString());
//
//        if (SettingsFragment.selectedTableImage == -1) {
//            gameObject.gameBackground = R.drawable.table_back1;
//        } else {
//            gameObject.gameBackground = SettingsFragment.selectedTableImage;
//        }
//        if (SettingsFragment.selectedCardImage == -1) {
//            gameObject.cardBackImage = R.drawable.cardback1;
//        } else {
//            gameObject.cardBackImage = SettingsFragment.selectedCardImage;
//        }
        gameObject.senderUsername = String.valueOf(Constants.NEW_GAME);
        ServerHandler.sendToAll(gameObject);
    }

}
