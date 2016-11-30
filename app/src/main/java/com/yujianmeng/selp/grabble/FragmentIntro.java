package com.yujianmeng.selp.grabble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentIntro extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        //  Following code adapted from :
        //  http://stackoverflow.com/questions/17357226/add-the-loading-screen-in-starting-of-the-android-application
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(4000);  //TODO Adjust Delay timer here (At least 3000 for map)
                } catch (Exception e)   {
                } finally {
                    Intent i = new Intent(getActivity().getApplicationContext(),MapActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        };
        welcomeThread.start();

        return view;
    }
}
