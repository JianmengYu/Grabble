package com.yujianmeng.selp.grabble;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by YuJianmeng on 2016/10/6.
 */

public class FragmentMainPage extends Fragment {

    private TextView tStart;
    private TextView tGrab;
    private TextView tAchievement;
    private TextView tStatistics;
    private TextView tSetting;
    private TextView tHelp;
    private TextView tAbout;
    private TextView tShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);

        //TODO get placeholder button to work;
        tGrab = (TextView) view.findViewById(R.id.main_grab_button);
        tGrab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity().getApplicationContext(),MapActivity.class);
                startActivity(i);
                //Toast.makeText(getActivity().getApplicationContext(),
                //        getString(R.string.main_start_grabble)+" button clicked, yay!",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        tStart = (TextView) view.findViewById(R.id.main_start_button);
        tStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity().getApplicationContext(),ActivityScrabble.class);
                startActivity(i);
                //Toast.makeText(getActivity().getApplicationContext(),
                //        getString(R.string.main_start_assemble)+" button clicked, yay!",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        tAchievement = (TextView) view.findViewById(R.id.main_achievement_button);
        tAchievement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity().getApplicationContext(),ActivityAchievement.class);
                startActivity(i);
                //Toast.makeText(getActivity().getApplicationContext(),
                //        getString(R.string.main_achievements)+" button clicked, yay!",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        tStatistics = (TextView) view.findViewById(R.id.main_statistics_button);
        tStatistics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.main_statistics)+" button clicked, yay!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tSetting = (TextView) view.findViewById(R.id.main_settings_button);
        tSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.main_settings)+" button clicked, yay!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tHelp = (TextView) view.findViewById(R.id.main_help_button);
        tHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.main_help)+" button clicked, yay!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tAbout = (TextView) view.findViewById(R.id.main_about_button);
        tAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.main_about)+" button clicked, yay!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tShare = (TextView) view.findViewById(R.id.main_share_button);
        tShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.main_share)+" button clicked, yay!",
                        Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

}
