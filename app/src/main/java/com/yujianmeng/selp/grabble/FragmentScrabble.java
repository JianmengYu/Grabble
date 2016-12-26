package com.yujianmeng.selp.grabble;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by YuJianmeng on 2016/10/6.
 */

public class FragmentScrabble extends Fragment{

    private static final String PREF_SAVE = "MySaveFile";

    private TextView mLevel;
    private TextView mExp;
    private TextView mScore;
    private TextView mDiscard;
    private ImageView mComplete;
    private ImageView mHelp;
    private ImageView mHelp2;
    private RelativeLayout mHelpLayout;

    private TextView mWarning1;
    private TextView mWarning2;

    private ImageView mInput1;
    private ImageView mInput2;
    private ImageView mInput3;
    private ImageView mInput4;
    private ImageView mInput5;
    private ImageView mInput6;
    private ImageView mInput7;

    private ImageView mButtonA;
    private ImageView mButtonB;
    private ImageView mButtonC;
    private ImageView mButtonD;
    private ImageView mButtonE;
    private ImageView mButtonF;
    private ImageView mButtonG;
    private ImageView mButtonH;
    private ImageView mButtonI;
    private ImageView mButtonJ;
    private ImageView mButtonK;
    private ImageView mButtonL;
    private ImageView mButtonM;
    private ImageView mButtonN;
    private ImageView mButtonO;
    private ImageView mButtonP;
    private ImageView mButtonQ;
    private ImageView mButtonR;
    private ImageView mButtonS;
    private ImageView mButtonT;
    private ImageView mButtonU;
    private ImageView mButtonV;
    private ImageView mButtonW;
    private ImageView mButtonX;
    private ImageView mButtonY;
    private ImageView mButtonZ;

    private Grabble grabble;
    private int mEagle;
    private int mGrabber;

    private int exp = 0;
    private int level = 0;
    private int score = 0;
    private int[] levelup = {200,400,600,900,1200,1500,1900,2300,2700,3200,4200,4800,5400,6000,6700,7400,
                                8100,8900,9700,10500,11400,12300,13200,14200,15200,16200,17300,18400,19500,
                                20700,21900,23100,24400,25700,27000,28400,29800,31200,32700,34200,35700,
                                37300,38900,40500,42200,43900,45600,47400,49200,51000};
    //Statistics
    private int[] lettersUsed = new int[26];
    private String favLetter;
    private int favLetterUsed;
    private String highScoreWord;
    private int highScore;
    private int wordCompleted;
    private String prevWord1;
    private String prevWord2;
    private String prevWord3;
    private int prevScore1;
    private int prevScore2;
    private int prevScore3;
    private boolean hardMode;
    private boolean hardModeWarning = false;
    private boolean leftHand;
    private boolean noHelp;

    private Typeface mFont;

    AchievementLab achievementLab;
    List<Achievement> achievements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrabble, container, false);
        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
        int[] letters = new int[26];
        for (int i = 0 ; i < 26 ; i++) {letters[i] = save.getInt("letter" + i,0);}
        for (int i = 0 ; i < 26 ; i++) {lettersUsed[i] = save.getInt("letterUsed" + i,0);}
        grabble = new Grabble(letters);
        mEagle = save.getInt("eagle",0);
        mGrabber = save.getInt("grabber",0);
        exp = save.getInt("exp",0);
        level = save.getInt("level",0);
        score = grabble.getScore();

        favLetter = save.getString("favLetter","N/A");
        favLetterUsed = save.getInt("favLetterUsed",0);
        highScoreWord = save.getString("highScoreWord","N/A");
        highScore = save.getInt("highScore",0);
        wordCompleted = save.getInt("wordCompleted",0);
        prevWord1 = save.getString("prevWord1","");
        prevWord2 = save.getString("prevWord2","");
        prevScore1 = save.getInt("prevScore1",50);//Prevent achievement on first word
        prevScore2 = save.getInt("prevScore2",70);
        hardMode = save.getBoolean("hardMode",false);
        noHelp = save.getBoolean("noHelp",false);
        leftHand = save.getBoolean("leftHand",false);

        achievementLab = AchievementLab.get(getActivity());
        achievements = achievementLab.getAchievements();

        mFont = Typeface.createFromAsset(getActivity().getAssets(), "generica_bold.otf");

        mLevel = (TextView) view.findViewById(R.id.scrabble_level_button);
        mLevel.setTypeface(mFont);
        mExp = (TextView) view.findViewById(R.id.scrabble_exp_button);
        mExp.setTypeface(mFont);
        mScore = (TextView) view.findViewById(R.id.scrabble_score_button);
        mScore.setTypeface(mFont);
        mDiscard = (TextView) view.findViewById(R.id.scrabble_discard_button);
        mDiscard.setTypeface(mFont);
        mWarning1 = (TextView) view.findViewById(R.id.scrabble_warning1);
        mWarning1.setTypeface(mFont);
        mWarning2 = (TextView) view.findViewById(R.id.scrabble_warning2);
        mWarning2.setTypeface(mFont);
        mComplete = (ImageView) view.findViewById(R.id.scrabble_complete_button);
        mHelp = (ImageView) view.findViewById(R.id.scrabble_help_button);
        mHelp2 = (ImageView) view.findViewById(R.id.scrabble_help_button2);
        mHelpLayout = (RelativeLayout) view.findViewById(R.id.scrabble_layout_help);

        mInput1 = (ImageView) view.findViewById(R.id.scrabble_input_1);
        mInput1.setImageResource(R.drawable.scrabble_button);
        mInput2 = (ImageView) view.findViewById(R.id.scrabble_input_2);
        mInput2.setImageResource(R.drawable.scrabble_button);
        mInput3 = (ImageView) view.findViewById(R.id.scrabble_input_3);
        mInput3.setImageResource(R.drawable.scrabble_button);
        mInput4 = (ImageView) view.findViewById(R.id.scrabble_input_4);
        mInput4.setImageResource(R.drawable.scrabble_button);
        mInput5 = (ImageView) view.findViewById(R.id.scrabble_input_5);
        mInput5.setImageResource(R.drawable.scrabble_button);
        mInput6 = (ImageView) view.findViewById(R.id.scrabble_input_6);
        mInput6.setImageResource(R.drawable.scrabble_button);
        mInput7 = (ImageView) view.findViewById(R.id.scrabble_input_7);
        mInput7.setImageResource(R.drawable.scrabble_button);

        mButtonA = (ImageView) view.findViewById(R.id.scrabble_input_a);
        mButtonB = (ImageView) view.findViewById(R.id.scrabble_input_b);
        mButtonC = (ImageView) view.findViewById(R.id.scrabble_input_c);
        mButtonD = (ImageView) view.findViewById(R.id.scrabble_input_d);
        mButtonE = (ImageView) view.findViewById(R.id.scrabble_input_e);
        mButtonF = (ImageView) view.findViewById(R.id.scrabble_input_f);
        mButtonG = (ImageView) view.findViewById(R.id.scrabble_input_g);
        mButtonH = (ImageView) view.findViewById(R.id.scrabble_input_h);
        mButtonI = (ImageView) view.findViewById(R.id.scrabble_input_i);
        mButtonJ = (ImageView) view.findViewById(R.id.scrabble_input_j);
        mButtonK = (ImageView) view.findViewById(R.id.scrabble_input_k);
        mButtonL = (ImageView) view.findViewById(R.id.scrabble_input_l);
        mButtonM = (ImageView) view.findViewById(R.id.scrabble_input_m);
        mButtonN = (ImageView) view.findViewById(R.id.scrabble_input_n);
        mButtonO = (ImageView) view.findViewById(R.id.scrabble_input_o);
        mButtonP = (ImageView) view.findViewById(R.id.scrabble_input_p);
        mButtonQ = (ImageView) view.findViewById(R.id.scrabble_input_q);
        mButtonR = (ImageView) view.findViewById(R.id.scrabble_input_r);
        mButtonS = (ImageView) view.findViewById(R.id.scrabble_input_s);
        mButtonT = (ImageView) view.findViewById(R.id.scrabble_input_t);
        mButtonU = (ImageView) view.findViewById(R.id.scrabble_input_u);
        mButtonV = (ImageView) view.findViewById(R.id.scrabble_input_v);
        mButtonW = (ImageView) view.findViewById(R.id.scrabble_input_w);
        mButtonX = (ImageView) view.findViewById(R.id.scrabble_input_x);
        mButtonY = (ImageView) view.findViewById(R.id.scrabble_input_y);
        mButtonZ = (ImageView) view.findViewById(R.id.scrabble_input_z);

        //Set the detail function of the short clicks.
        View.OnClickListener shortListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //Prompt the player if the letter is not Entered
                    //The enterLetter is called inside constructString
                    case R.id.scrabble_input_a: constructString('a',2); updateUI(); break;
                    case R.id.scrabble_input_b: constructString('b',2); updateUI(); break;
                    case R.id.scrabble_input_c: constructString('c',2); updateUI(); break;
                    case R.id.scrabble_input_d: constructString('d',2); updateUI(); break;
                    case R.id.scrabble_input_e: constructString('e',2); updateUI(); break;
                    case R.id.scrabble_input_f: constructString('f',2); updateUI(); break;
                    case R.id.scrabble_input_g: constructString('g',2); updateUI(); break;
                    case R.id.scrabble_input_h: constructString('h',2); updateUI(); break;
                    case R.id.scrabble_input_i: constructString('i',2); updateUI(); break;
                    case R.id.scrabble_input_j: constructString('j',2); updateUI(); break;
                    case R.id.scrabble_input_k: constructString('k',2); updateUI(); break;
                    case R.id.scrabble_input_l: constructString('l',2); updateUI(); break;
                    case R.id.scrabble_input_m: constructString('m',2); updateUI(); break;
                    case R.id.scrabble_input_n: constructString('n',2); updateUI(); break;
                    case R.id.scrabble_input_o: constructString('o',2); updateUI(); break;
                    case R.id.scrabble_input_p: constructString('p',2); updateUI(); break;
                    case R.id.scrabble_input_q: constructString('q',2); updateUI(); break;
                    case R.id.scrabble_input_r: constructString('r',2); updateUI(); break;
                    case R.id.scrabble_input_s: constructString('s',2); updateUI(); break;
                    case R.id.scrabble_input_t: constructString('t',2); updateUI(); break;
                    case R.id.scrabble_input_u: constructString('u',2); updateUI(); break;
                    case R.id.scrabble_input_v: constructString('v',2); updateUI(); break;
                    case R.id.scrabble_input_w: constructString('w',2); updateUI(); break;
                    case R.id.scrabble_input_x: constructString('x',2); updateUI(); break;
                    case R.id.scrabble_input_y: constructString('y',2); updateUI(); break;
                    case R.id.scrabble_input_z: constructString('z',2); updateUI(); break;

                    case R.id.scrabble_complete_button:
                        //Achievement Check First;
                        if (grabble.getPoint() == 7){
                            //Enter a none-available word still count as entered.
                            prevWord3 = prevWord2;
                            prevWord2 = prevWord1;
                            prevWord1 = String.valueOf(grabble.getInput());
                        }
                        //Then the word check;
                        if (hardModeCheck() && grabble.getPoint() == 7 && isWord(String.valueOf(grabble.getInput()))){
                            //update favourate word.
                            char[] c = grabble.getInput();
                            int score = 0;
                            for (char cha: c){
                                int index = Grabble.charToInt(cha);
                                if((++lettersUsed[index])>favLetterUsed){
                                    favLetter = Character.toUpperCase(cha) + "";
                                    favLetterUsed = lettersUsed[index];
                                }
                                score += grabble.getCharScore(cha);
                            }
                            Log.i("TAG",score+"");
                            prevScore3 = prevScore2;
                            prevScore2 = prevScore1;
                            prevScore1 = score;
                            if (score > highScore){
                                highScore = score;
                                highScoreWord = String.valueOf(grabble.getInput());
                            }
                            addScore(grabble.completeWord());
                            wordCompleted++;
                        }
                        if (hardModeWarning) {
                            mWarning2.setText("HARD MODE: Score Range (" +
                                    (prevScore1 - 10) + "-" + (prevScore1 + 10) + ") and (" +
                                    (prevScore2 - 10) + "-" + (prevScore2 + 10) + ") unavailable" );
                            hardModeWarning = false;
                        }
                        checkAchievement();
                        break;
                    case R.id.scrabble_discard_button:
                        grabble.discardLetter();
                        updateUI();
                        break;
                    case R.id.scrabble_help_button:
                        if (mHelpLayout.getVisibility() == View.INVISIBLE){
                            mHelpLayout.setVisibility(View.VISIBLE);
                            mWarning1.setVisibility(View.INVISIBLE);
                            mWarning2.setVisibility(View.INVISIBLE);
                        }else{
                            mHelpLayout.setVisibility(View.INVISIBLE);
                            mWarning1.setVisibility(View.VISIBLE);
                            mWarning2.setVisibility(View.VISIBLE);
                        }break;
                    case R.id.scrabble_help_button2:
                        if (mHelpLayout.getVisibility() == View.INVISIBLE){
                            mHelpLayout.setVisibility(View.VISIBLE);
                            mWarning1.setVisibility(View.INVISIBLE);
                            mWarning2.setVisibility(View.INVISIBLE);
                        }else{
                            mHelpLayout.setVisibility(View.INVISIBLE);
                            mWarning1.setVisibility(View.VISIBLE);
                            mWarning2.setVisibility(View.VISIBLE);
                        }break;
                    case R.id.scrabble_layout_help:
                        mHelpLayout.setVisibility(View.INVISIBLE);
                        mWarning1.setVisibility(View.VISIBLE);
                        mWarning2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.scrabble_level_button: constructString(0);break;
                    case R.id.scrabble_exp_button: constructString(1);break;
                    case R.id.scrabble_score_button: constructString(2);break;
                }
            }
        };
        mButtonA.setOnClickListener(shortListener);
        mButtonB.setOnClickListener(shortListener);
        mButtonC.setOnClickListener(shortListener);
        mButtonD.setOnClickListener(shortListener);
        mButtonE.setOnClickListener(shortListener);
        mButtonF.setOnClickListener(shortListener);
        mButtonG.setOnClickListener(shortListener);
        mButtonH.setOnClickListener(shortListener);
        mButtonI.setOnClickListener(shortListener);
        mButtonJ.setOnClickListener(shortListener);
        mButtonK.setOnClickListener(shortListener);
        mButtonL.setOnClickListener(shortListener);
        mButtonM.setOnClickListener(shortListener);
        mButtonN.setOnClickListener(shortListener);
        mButtonO.setOnClickListener(shortListener);
        mButtonP.setOnClickListener(shortListener);
        mButtonQ.setOnClickListener(shortListener);
        mButtonR.setOnClickListener(shortListener);
        mButtonS.setOnClickListener(shortListener);
        mButtonT.setOnClickListener(shortListener);
        mButtonU.setOnClickListener(shortListener);
        mButtonV.setOnClickListener(shortListener);
        mButtonW.setOnClickListener(shortListener);
        mButtonX.setOnClickListener(shortListener);
        mButtonY.setOnClickListener(shortListener);
        mButtonZ.setOnClickListener(shortListener);

        mComplete.setOnClickListener(shortListener);
        mDiscard.setOnClickListener(shortListener);
        mHelp.setOnClickListener(shortListener);
        mHelp2.setOnClickListener(shortListener);
        mHelpLayout.setOnClickListener(shortListener);

        mLevel.setOnClickListener(shortListener);
        mExp.setOnClickListener(shortListener);
        mScore.setOnClickListener(shortListener);

        //Set the detail function of the long clicks.
        View.OnLongClickListener longListener = new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                switch (v.getId()) {
                    // ConstructString sets the text of mWarning2 to string "You have n letter x left"
                    // taking the letter x as input, the 1 is used to distinguish different use.
                    case R.id.scrabble_input_a: constructString('a',1); updateUI(); break;
                    case R.id.scrabble_input_b: constructString('b',1); updateUI(); break;
                    case R.id.scrabble_input_c: constructString('c',1); updateUI(); break;
                    case R.id.scrabble_input_d: constructString('d',1); updateUI(); break;
                    case R.id.scrabble_input_e: constructString('e',1); updateUI(); break;
                    case R.id.scrabble_input_f: constructString('f',1); updateUI(); break;
                    case R.id.scrabble_input_g: constructString('g',1); updateUI(); break;
                    case R.id.scrabble_input_h: constructString('h',1); updateUI(); break;
                    case R.id.scrabble_input_i: constructString('i',1); updateUI(); break;
                    case R.id.scrabble_input_j: constructString('j',1); updateUI(); break;
                    case R.id.scrabble_input_k: constructString('k',1); updateUI(); break;
                    case R.id.scrabble_input_l: constructString('l',1); updateUI(); break;
                    case R.id.scrabble_input_m: constructString('m',1); updateUI(); break;
                    case R.id.scrabble_input_n: constructString('n',1); updateUI(); break;
                    case R.id.scrabble_input_o: constructString('o',1); updateUI(); break;
                    case R.id.scrabble_input_p: constructString('p',1); updateUI(); break;
                    case R.id.scrabble_input_q: constructString('q',1); updateUI(); break;
                    case R.id.scrabble_input_r: constructString('r',1); updateUI(); break;
                    case R.id.scrabble_input_s: constructString('s',1); updateUI(); break;
                    case R.id.scrabble_input_t: constructString('t',1); updateUI(); break;
                    case R.id.scrabble_input_u: constructString('u',1); updateUI(); break;
                    case R.id.scrabble_input_v: constructString('v',1); updateUI(); break;
                    case R.id.scrabble_input_w: constructString('w',1); updateUI(); break;
                    case R.id.scrabble_input_x: constructString('x',1); updateUI(); break;
                    case R.id.scrabble_input_y: constructString('y',1); updateUI(); break;
                    case R.id.scrabble_input_z: constructString('z',1); updateUI(); break;
                    case R.id.scrabble_discard_button:
                        grabble.discardAll();
                        updateUI(); break;
                }
                return true;
            }
        };
        mButtonA.setOnLongClickListener(longListener);
        mButtonB.setOnLongClickListener(longListener);
        mButtonC.setOnLongClickListener(longListener);
        mButtonD.setOnLongClickListener(longListener);
        mButtonE.setOnLongClickListener(longListener);
        mButtonF.setOnLongClickListener(longListener);
        mButtonG.setOnLongClickListener(longListener);
        mButtonH.setOnLongClickListener(longListener);
        mButtonI.setOnLongClickListener(longListener);
        mButtonJ.setOnLongClickListener(longListener);
        mButtonK.setOnLongClickListener(longListener);
        mButtonL.setOnLongClickListener(longListener);
        mButtonM.setOnLongClickListener(longListener);
        mButtonN.setOnLongClickListener(longListener);
        mButtonO.setOnLongClickListener(longListener);
        mButtonP.setOnLongClickListener(longListener);
        mButtonQ.setOnLongClickListener(longListener);
        mButtonR.setOnLongClickListener(longListener);
        mButtonS.setOnLongClickListener(longListener);
        mButtonT.setOnLongClickListener(longListener);
        mButtonU.setOnLongClickListener(longListener);
        mButtonV.setOnLongClickListener(longListener);
        mButtonW.setOnLongClickListener(longListener);
        mButtonX.setOnLongClickListener(longListener);
        mButtonY.setOnLongClickListener(longListener);
        mButtonZ.setOnLongClickListener(longListener);
        mDiscard.setOnLongClickListener(longListener);

        updateUI();
        return view;
    }

    @Override
    public void onPause(){
        grabble.discardAll();
        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
        SharedPreferences.Editor editor = save.edit();
        int[] letters = grabble.getAll();
        for (int i = 0 ; i < 26 ; i++) {editor.putInt("letter" + i,letters[i]);}
        for (int i = 0 ; i < 26 ; i++) {editor.putInt("letterUsed" + i,lettersUsed[i]);}
        editor.putInt("eagle",mEagle);
        editor.putInt("grabber",mGrabber);
        editor.putInt("exp",exp);
        editor.putInt("level",level);
        editor.putString("favLetter",favLetter);
        editor.putInt("favLetterUsed",favLetterUsed);
        editor.putString("highScoreWord",highScoreWord);
        editor.putInt("highScore",highScore);
        editor.putInt("wordCompleted",wordCompleted);
        editor.putString("prevWord1",prevWord1);
        editor.putString("prevWord2",prevWord2);
        editor.putInt("prevScore1",prevScore1);
        editor.putInt("prevScore2",prevScore2);
        //Commit save before closing
        editor.commit();
        super.onPause();
    }




    public void addScore(int score){
        //Calculate item reward.
        int randomD201 = (int) ((Math.random() * 20) + 1);
        int randomD202 = (int) ((Math.random() * 20) + 1);
        int eagleGain = 0;
        int grabberGain = 0;
        if (score <= 40){
            if (randomD201 > 19){eagleGain++;}
            if (randomD202 > 19){grabberGain++;}}
        if (score > 40 && score <= 80){
            if (randomD201 > 10){eagleGain++;}
            if (randomD202 > 10){grabberGain++;}}
        if (score > 80 && score <= 100){
            eagleGain++;grabberGain++;
            if (randomD201 > 10){eagleGain++;}
            if (randomD202 > 10){grabberGain++;}}
        if (score > 100) {
            eagleGain = eagleGain + 1 + randomD201/6;
            grabberGain = grabberGain + 1 + randomD202/6;}
        //Level Up
        int newScore = exp + score;
        int newLevel = level;
        if (newLevel < levelup.length && newScore>=levelup[newLevel]){//If not max level
            newLevel++;
            eagleGain = eagleGain + newLevel/10 + 1;
            grabberGain = grabberGain + newLevel/10 +1;
        }
        mGrabber = mGrabber + grabberGain;
        mEagle = mEagle + eagleGain;
        //Construct reward string
        String point = "You completed a score " + score + " word";
        String lvUp = "";
        String grUp = "";
        String eaUp = "";
        if (newLevel != level){lvUp = "\nLevel UP!";}
        if (grabberGain > 0){grUp = "\nYou gained " + grabberGain + "x\"Grabber\"";}
        if (eagleGain > 0){eaUp = "\nYou gained " + eagleGain + "x\"Eagle Eye\"";}
        Toast t = new Toast(getActivity().getApplicationContext());
        t.setGravity(Gravity.CENTER,0,-80);
        //http://stackoverflow.com/questions/6888664/android-toast-doesnt-fit-text
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tl = inflater.inflate(R.layout.toast_reward,null);
        TextView textView = (TextView) tl.findViewById(R.id.text);
        textView.setText(point + lvUp + grUp +eaUp);
        textView.setTypeface(mFont);
        t.setView(tl);
        level = newLevel;
        exp = newScore;
        updateUI();
        t.show();
    }

    public boolean isWord(String word){
        int start = 0;
        int end = 23869 - 1; //There are 23869 words;
        int half;
        word = word.toLowerCase();
        String word2;
        try{
            InputStream inputStream;
            BufferedReader reader;
            while (start <= end){//It will complete the search in 15 times. 2^15 = 32768
                half = (start + end)/2;
                inputStream = getActivity().getApplicationContext().getAssets().open("grabble.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream));
                for (int i = 0; i< half;i++){reader.readLine();}//Skip all of the not read lines;
                word2 = reader.readLine();//Store the half into word2
                word2 = word2.toLowerCase();
                Log.i("TAG",word + "<end>");
                Log.i("TAG",word2 + "<end>");
                Log.i("TAG",half + "");
                half = Grabble.compare(word,word2);//Use half for storing compare result temporarily;
                Log.i("TAG",half + "");
                switch (half){
                    case 0: return true;//Find the word successfully
                    case 1: end = (start + end)/2 - 1;break;
                    case 2: start = (start + end)/2 + 1;break;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public void updateUI(){
        //Update all the UI elements.
        score = grabble.getScore();
        char[] input = grabble.getInput();
        int point = grabble.getPoint();
        if (point > 0 ){mInput1.setImageResource(grabble.getImage(input[0]));}
            else {mInput1.setImageResource(R.drawable.scrabble_button);}
        if (point > 1 ){mInput2.setImageResource(grabble.getImage(input[1]));}
            else {mInput2.setImageResource(R.drawable.scrabble_button);}
        if (point > 2 ){mInput3.setImageResource(grabble.getImage(input[2]));}
            else {mInput3.setImageResource(R.drawable.scrabble_button);}
        if (point > 3 ){mInput4.setImageResource(grabble.getImage(input[3]));}
            else {mInput4.setImageResource(R.drawable.scrabble_button);}
        if (point > 4 ){mInput5.setImageResource(grabble.getImage(input[4]));}
            else {mInput5.setImageResource(R.drawable.scrabble_button);}
        if (point > 5 ){mInput6.setImageResource(grabble.getImage(input[5]));}
            else {mInput6.setImageResource(R.drawable.scrabble_button);}
        if (point > 6 ){mInput7.setImageResource(grabble.getImage(input[6]));
                        mComplete.setImageResource(R.drawable.scrabble_button_complete);}
            else {mInput7.setImageResource(R.drawable.scrabble_button);
                   mComplete.setImageResource(R.drawable.scrabble_button_complete_n);}
        mLevel.setText("Level: "+level);
        mExp.setText("Exp: "+exp);
        mScore.setText("Total Score: "+score);
        mWarning1.setText("");
        if(leftHand){
            mHelp.setVisibility(View.INVISIBLE);
            mHelp2.setVisibility(View.VISIBLE);
        }
        if(noHelp){
            mHelp.setVisibility(View.INVISIBLE);
            mHelp2.setVisibility(View.INVISIBLE);
        }
    }

    public void constructString(char c, int type){
        //Construct different hints, case 1 is check amount of letter left,
        //                           case 2 warns player the letter is not inputted;
        int amount = grabble.getAmount(c);
        int type2 = -1;
        if (type == 2){type2 = grabble.enterLetter(c);}
        switch (type){
            case 1: if (amount == 0){mWarning2.setText("You have " + "no" +
                                                          " letter " + Character.toUpperCase(c) +
                                                          " Left.");
                    }else{mWarning2.setText("You have " + grabble.getAmount(c) +
                                              " letter " + Character.toUpperCase(c) +
                                              " Left.");}
                    break;
            case 2: if (type2 == 0) {mWarning2.setText("You have " + grabble.getAmount(c) +
                                                        " letter " + Character.toUpperCase(c) +
                                                        " Left.");}
                     if (type2 == 1) {mWarning2.setText("You don't have letter " +
                                                          Character.toUpperCase(c) +
                                                          " Left!");}
                     if (type2 == 2) {mWarning2.setText("You already used 7 letters!");}
                     break;
        }
    }

    public void constructString(int type){
        //Construct hint for warning1
        //0 for level button, 1 for exp button, 2 for score button
        switch (type){
            case 0: mWarning1.setText("Your Level gives +" + level + "% range bonus.");break;
            case 1: mWarning1.setText("Level Progress: (" + exp + "/" + levelup[level] + ").");break;
            case 2: String word = String.valueOf(grabble.getInput());
                    int pointer = grabble.getPoint();
                    String[] out = new String[7];
                    char temp;
                    for (int i=0;i<7;i++){
                        if (pointer > i ){
                            temp = word.charAt(i);
                            out[i] = Character.toUpperCase(temp) + ":" + grabble.getCharScore(temp) + " ";
                        } else {out[i] = "";}
                    }
                    mWarning1.setText(out[0]+out[1]+out[2]+out[3]+out[4]+out[5]+out[6]);
        }
    }

    private boolean hardModeCheck(){
        if (!hardMode) {return true;}else{
            char[] c = grabble.getInput();
            int score = 0;
            for (char cha: c){
                score += grabble.getCharScore(cha);
            }
            if (score <= prevScore1 + 10 && score >= prevScore1 - 10){
                hardModeWarning = true;
                return false;
            }
            if (score <= prevScore2 + 10 && score >= prevScore2 - 10){
                hardModeWarning = true;
                return false;
            }
        }
        return true;
    }

    private void checkAchievement(){
        String unlocked = "no";
        //Check achievement unlocked
        if (exp >= 100){if(achievementLab.updateAchievement("A New Player in Town")){
            unlocked = "A New Player in Town";}}
        if (exp >= 1000){if(achievementLab.updateAchievement("Grabbled")){
            unlocked = "Grabbled";}}
        if (exp >= 9000){if(achievementLab.updateAchievement("It's OVER 9000!")){
            unlocked = "It's OVER 9000!";}}
        if (exp >= 50000){if(achievementLab.updateAchievement("Obsessed")){
            unlocked = "Obsessed";}}
        if (exp >= 100000){if(achievementLab.updateAchievement("Maniac!")){
            unlocked = "Maniac!";}}
        if (wordCompleted >= 1){if(achievementLab.updateAchievement("My First Word!")){
            unlocked = "My First Word!";}}
        if (wordCompleted >= 10){if(achievementLab.updateAchievement("Out of Words.. Almost")){
            unlocked = "Out of Words.. Almost";}}
        if (wordCompleted >= 100){if(achievementLab.updateAchievement("Knows how to Google")){
            unlocked = "Knows how to Google";}}
        if (wordCompleted >= 500){if(achievementLab.updateAchievement("Linguist")){
            unlocked = "Linguist";}}
        if (wordCompleted >= 1000){if(achievementLab.updateAchievement("Living Dictionary")){
            unlocked = "Living Dictionary";}}
        if (mGrabber >= 100 && mEagle >= 100){if(achievementLab.updateAchievement("Bona Fide Player")){
            unlocked = "Bona Fide Player";}}

        if (prevScore1 <= 40 && prevScore2 <= 40 && prevScore3 <= 40){if(achievementLab.updateAchievement("Toddler")){
            unlocked = "Toddler";}}
        if (prevScore1 >= 80 && prevScore2 >= 80 && prevScore3 >= 80){if(achievementLab.updateAchievement("Bachelor")){
            unlocked = "Bachelor";}}
        if (prevWord1.equals("abcdefg")){if(achievementLab.updateAchievement("Clueless")){
            unlocked = "Clueless";}}
        if (prevWord1.equals("grabble")){if(achievementLab.updateAchievement("Still Clueless")){
            unlocked = "Still Clueless";}}
        if (prevWord1.equals("spartan")){if(achievementLab.updateAchievement("Madness?")){
            unlocked = "Madness?";}}
        if (prevWord1.equals("hillary")){if(achievementLab.updateAchievement("Hillarious")){
            unlocked = "Hillarious";}}
        if (prevWord1.equals("djtrump")){if(achievementLab.updateAchievement("President Musician")){
            unlocked = "President Musician";}}
        if (prevWord1.equals("drzaius")){if(achievementLab.updateAchievement("Planet Apes: the Musical")){
            unlocked = "Planet Apes: the Musical";}}
        if (prevWord1.equals("harambe")){if(achievementLab.updateAchievement("The Gorilla Savior")){
            unlocked = "The Gorilla Savior";}}
        if (prevWord1.equals("winston")){if(achievementLab.updateAchievement("MLG gamer")){
            unlocked = "MLG gamer";}}
        if (prevWord1.equals("songoku")){if(achievementLab.updateAchievement("Super Saiyan")){
            unlocked = "Super Saiyan";}}
        if (prevWord1.equals("primate")){if(achievementLab.updateAchievement("Top of the Revolution")){
            unlocked = "Top of the Revolution";}}

        if (prevWord1.equals("america") && prevWord2.equals("america") && prevWord3.equals("america")){
            if(achievementLab.updateAchievement("Realm of Freedom")){
                unlocked = "Realm of Freedom";}}
        if (prevWord1.equals("monkeys") && prevWord2.equals("monkeys") && prevWord3.equals("monkeys")){
            if(achievementLab.updateAchievement("Monkey Lover")){
                unlocked = "Monkey Lover";}}
        if (prevWord1.equals("gorilla") && prevWord2.equals("gorilla") && prevWord3.equals("gorilla")){
            if(achievementLab.updateAchievement("Gorillaphilia")){
                unlocked = "Gorillaphilia";}}
        //Start construct Toast
        if (!unlocked.equals("no")){
            //Prompt the Player if they unlocked an achievement.
            //Borrows the achievement Layout of achievement list item.
            Toast t = new Toast(getActivity().getApplicationContext());
            t.setGravity(Gravity.CENTER,0,150);
            LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View tl = inflater.inflate(R.layout.list_item_achievement,null);
            RelativeLayout rl2 = (RelativeLayout) tl.findViewById(R.id.achievement_brown_bg);
            RelativeLayout rl = (RelativeLayout) tl.findViewById(R.id.achievement_bg);
            rl.setBackgroundResource(R.drawable.design_achievement_bg_t);
            rl2.setBackgroundResource(R.color.colorTransparent);
            ImageView im = (ImageView) tl.findViewById(R.id.achievement_image);
            TextView tv1 = (TextView) tl.findViewById(R.id.achievement_name);
            TextView tv2 = (TextView) tl.findViewById(R.id.achievement_descript);
            TextView tv3 = (TextView) tl.findViewById(R.id.achievement_time);
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "generica_bold.otf");
            Achievement a = new Achievement();
            a.setmName(unlocked);
            im.setImageResource(a.getImageString());
            tv1.setTypeface(font);
            tv2.setTypeface(font);
            tv1.setText("Achievement Unlocked!");
            tv1.setTextSize(10);
            tv2.setText("\"" + unlocked + "\"");
            tv2.setTextSize(15);
            tv3.setText("");
            t.setView(tl);
            t.show();
        }
    }
}

