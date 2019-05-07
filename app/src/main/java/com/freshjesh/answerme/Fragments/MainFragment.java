package com.freshjesh.answerme.Fragments;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.freshjesh.answerme.R;
import com.freshjesh.answerme.Threads.ClientConnectionThread;
//import com.freshjesh.answerme.Utils.ClientHandler;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joshc on 7/24/2017.
 */

public class MainFragment extends Fragment {
//    public static ClientHandler clientHandler;
    public static MaterialEditText userName;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_screen, container, false);
        Button hostGame = (Button) rootView.findViewById(R.id.hostGame);
        Button joinGame = (Button) rootView.findViewById(R.id.joinGame);
        Button settingsButton = (Button) rootView.findViewById(R.id.settingsButton);
//        infoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, new SettingFragment()).addToBackStack(SettingFragment.class.getName())
//                        .commit();
//            }
//        });
        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        userName = (MaterialEditText) rootView.findViewById(R.id.userName);
        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    boolean isWifiAPEnabled = (Boolean) method.invoke(wifi);
                    final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if (isWifiAPEnabled) {
                        joinGame.setVisibility(View.GONE);
                        hostGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (userName.getText() != null && userName.getText().toString().trim().length() > 0) {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, new HostFragment()).addToBackStack(HostFragment.class.getName())
                                            .commit();
                                } else {
                                    Toast.makeText(getActivity(), "Please enter a UserName", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
//                        if (clientHandler == null) {
//                            clientHandler = new ClientHandler();
//                        }
                        hostGame.setVisibility(View.GONE);
                        joinGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ClientConnectionThread clientConnect = new ClientConnectionThread(MainFragment.userName.getText().toString());
                                clientConnect.start();
                                if (userName.getText() != null && userName.getText().toString().trim().length() > 0) {
//                                    if (ClientConnectionThread.serverStarted) {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, new ClientFragment()).addToBackStack(ClientFragment.class.getName())
                                            .commit();
//                                    } else {
//                                        Toast.makeText(getActivity(), "Game yet to be hosted", Toast.LENGTH_SHORT).show();
//                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Please enter a UserName", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return rootView;
    }
}
