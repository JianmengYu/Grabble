package com.yujianmeng.selp.grabble;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSettings extends Fragment {

    private static final String PREF_SAVE = "MySaveFile";

    //SX means Xth setting, N/D/B means Name,Description,Button
    private TextView mS1N;
    private TextView mS1D;
    private ImageView mS1B;
    private TextView mS2N;
    private TextView mS2D;
    private ImageView mS2B;
    private TextView mS3N;
    private TextView mS3D;
    private ImageView mS3B;
    private TextView mS4N;
    private TextView mS4D;
    private ImageView mS4B;
    private TextView mS5N;
    private TextView mS5D;
    private ImageView mS5B;

    private RelativeLayout mPrompt;
    private RelativeLayout mPromptImage;
    private ImageView mPromptYes;

    private boolean leftHand = false;
    private boolean levelBonus = true;
    private boolean noHelp = false;
    private boolean hardMode = false;

    private int promptIsOn = 0;

    private Typeface mFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);

        Log.i("SET","OnCreateView called");

        leftHand = save.getBoolean("leftHand",false);
        levelBonus = save.getBoolean("levelBonus",true);
        noHelp = save.getBoolean("noHelp",false);
        hardMode = save.getBoolean("hardMode",false);

        mFont = Typeface.createFromAsset(getActivity().getAssets(), "generica_bold.otf");

        mS1N = (TextView) view.findViewById(R.id.settings_name_1);
        mS2N = (TextView) view.findViewById(R.id.settings_name_2);
        mS3N = (TextView) view.findViewById(R.id.settings_name_3);
        mS4N = (TextView) view.findViewById(R.id.settings_name_4);
        mS5N = (TextView) view.findViewById(R.id.settings_name_5);

        mS1D = (TextView) view.findViewById(R.id.setting_description_1);
        mS2D = (TextView) view.findViewById(R.id.setting_description_2);
        mS3D = (TextView) view.findViewById(R.id.setting_description_3);
        mS4D = (TextView) view.findViewById(R.id.setting_description_4);
        mS5D = (TextView) view.findViewById(R.id.setting_description_5);

        mS1N.setTypeface(mFont);mS1D.setTypeface(mFont);
        mS2N.setTypeface(mFont);mS2D.setTypeface(mFont);
        mS3N.setTypeface(mFont);mS3D.setTypeface(mFont);
        mS4N.setTypeface(mFont);mS4D.setTypeface(mFont);
        mS5N.setTypeface(mFont);mS5D.setTypeface(mFont);

        mS1B = (ImageView) view.findViewById(R.id.settings_button_1);
        mS2B = (ImageView) view.findViewById(R.id.settings_button_2);
        mS3B = (ImageView) view.findViewById(R.id.settings_button_3);
        mS4B = (ImageView) view.findViewById(R.id.settings_button_4);
        mS5B = (ImageView) view.findViewById(R.id.settings_button_5);

        mPrompt = (RelativeLayout) view.findViewById(R.id.prompt_layout);
        mPromptImage = (RelativeLayout) view.findViewById(R.id.prompt_image);
        mPromptYes = (ImageView) view.findViewById(R.id.prompt_yes);

        mS1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftHand){leftHand = false;updateUI();}
                else{leftHand = true;updateUI();}
            }
        });
        mS2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelBonus){levelBonus = false;updateUI();}
                else{levelBonus = true;updateUI();}
            }
        });
        mS3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noHelp){noHelp = false;updateUI();}
                else{noHelp = true;updateUI();}
            }
        });
        mS4B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                if(!hardMode) {
                    promptIsOn = 1;
                    mPromptImage.setBackgroundResource(R.drawable.prompt_hardmode);
                    mPrompt.setVisibility(View.VISIBLE);
                }else{
                    hardMode = false;
                    updateUI();
                }
            }
        });
        mS5B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptIsOn = 2;
                mPromptImage.setBackgroundResource(R.drawable.prompt_cleardata);
                mPrompt.setVisibility(View.VISIBLE);
            }
        });
        mPromptYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (promptIsOn == 1){
                    hardMode = true;
                    updateUI();
                }
                if (promptIsOn == 2){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "DATA CLEARED, EXITING GAME.",
                            Toast.LENGTH_SHORT).show();
                    //Clear Database
                    MarkerLab markerLab = MarkerLab.get(getActivity().getApplicationContext());
                    markerLab.deleteAll();
                    AchievementLab achievementLab = AchievementLab.get(getActivity().getApplicationContext());
                    achievementLab.deleteAll();
                    //Clear Shared preference
                    SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
                    SharedPreferences.Editor editor = save.edit();
                    editor.clear();
                    editor.commit();
                    leftHand = false;
                    levelBonus = true;
                    noHelp = false;
                    hardMode = false;
                    //Exit Game.
                    getActivity().finishAffinity();
                }
            }
        });
        mPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptIsOn = 0;
                mPrompt.setVisibility(View.INVISIBLE);
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onPause(){
        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
        SharedPreferences.Editor editor = save.edit();
        editor.putBoolean("leftHand",leftHand);
        editor.putBoolean("levelBonus",levelBonus);
        editor.putBoolean("noHelp",noHelp);
        editor.putBoolean("hardMode",hardMode);
        editor.commit();
        Log.i("SET","onPause called");
        super.onPause();
    }
    //TODO delete Experimental Code?
    @Override
    public void onResume(){
        Log.i("SET","onResume called");
        super.onResume();
    }
    @Override
    public void onStart(){
        Log.i("SET","onStart called");
        super.onStart();
    }
    @Override
    public void onStop(){
        Log.i("SET","onStop called");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i("SET","onDestroy called");
        super.onDestroy();
    }

    private void updateUI(){
        if(leftHand){mS1B.setImageResource(R.drawable.settings_button_on);}
                else{mS1B.setImageResource(R.drawable.settings_button_off);}
        if(levelBonus){mS2B.setImageResource(R.drawable.settings_button_on);}
                else{mS2B.setImageResource(R.drawable.settings_button_off);}
        if(noHelp){mS3B.setImageResource(R.drawable.settings_button_on);}
                else{mS3B.setImageResource(R.drawable.settings_button_off);}
        if(hardMode){mS4B.setImageResource(R.drawable.settings_button_on);}
                else{mS4B.setImageResource(R.drawable.settings_button_off);}
        promptIsOn = 0;
        mPrompt.setVisibility(View.INVISIBLE);
    }

}
