package com.yujianmeng.selp.grabble;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private int exp = 3400;
    private int level = 20;
    private int score;

    private Typeface mFont;

    //Temp stuff
    private RelativeLayout mInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrabble, container, false);

        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
        int[] letters = new int[26];
        for (int i = 0 ; i < 26 ; i++) {letters[i] = save.getInt("letter" + i,0);}
        grabble = new Grabble(letters);
        mEagle = save.getInt("eagle",0);
        mGrabber = save.getInt("grabber",0);
        score = grabble.getScore();

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
                    //TODO remake the enter letter system.
                    case R.id.scrabble_input_a: if(grabble.enterLetter('a')){constructString('a',2);}; updateUI(); break;
                    case R.id.scrabble_input_b: if(grabble.enterLetter('b')){constructString('b',2);}; updateUI(); break;
                    case R.id.scrabble_input_c: if(grabble.enterLetter('c')){constructString('c',2);}; updateUI(); break;
                    case R.id.scrabble_input_d: if(grabble.enterLetter('d')){constructString('d',2);}; updateUI(); break;
                    case R.id.scrabble_input_e: if(grabble.enterLetter('e')){constructString('e',2);}; updateUI(); break;
                    case R.id.scrabble_input_f: if(grabble.enterLetter('f')){constructString('f',2);}; updateUI(); break;
                    case R.id.scrabble_input_g: if(grabble.enterLetter('g')){constructString('g',2);}; updateUI(); break;
                    case R.id.scrabble_input_h: if(grabble.enterLetter('h')){constructString('h',2);}; updateUI(); break;
                    case R.id.scrabble_input_i: if(grabble.enterLetter('i')){constructString('i',2);}; updateUI(); break;
                    case R.id.scrabble_input_j: if(grabble.enterLetter('j')){constructString('j',2);}; updateUI(); break;
                    case R.id.scrabble_input_k: if(grabble.enterLetter('k')){constructString('k',2);}; updateUI(); break;
                    case R.id.scrabble_input_l: if(grabble.enterLetter('l')){constructString('l',2);}; updateUI(); break;
                    case R.id.scrabble_input_m: if(grabble.enterLetter('m')){constructString('m',2);}; updateUI(); break;
                    case R.id.scrabble_input_n: if(grabble.enterLetter('n')){constructString('n',2);}; updateUI(); break;
                    case R.id.scrabble_input_o: if(grabble.enterLetter('o')){constructString('o',2);}; updateUI(); break;
                    case R.id.scrabble_input_p: if(grabble.enterLetter('p')){constructString('p',2);}; updateUI(); break;
                    case R.id.scrabble_input_q: if(grabble.enterLetter('q')){constructString('q',2);}; updateUI(); break;
                    case R.id.scrabble_input_r: if(grabble.enterLetter('r')){constructString('r',2);}; updateUI(); break;
                    case R.id.scrabble_input_s: if(grabble.enterLetter('s')){constructString('s',2);}; updateUI(); break;
                    case R.id.scrabble_input_t: if(grabble.enterLetter('t')){constructString('t',2);}; updateUI(); break;
                    case R.id.scrabble_input_u: if(grabble.enterLetter('u')){constructString('u',2);}; updateUI(); break;
                    case R.id.scrabble_input_v: if(grabble.enterLetter('v')){constructString('v',2);}; updateUI(); break;
                    case R.id.scrabble_input_w: if(grabble.enterLetter('w')){constructString('w',2);}; updateUI(); break;
                    case R.id.scrabble_input_x: if(grabble.enterLetter('x')){constructString('x',2);}; updateUI(); break;
                    case R.id.scrabble_input_y: if(grabble.enterLetter('y')){constructString('y',2);}; updateUI(); break;
                    case R.id.scrabble_input_z: if(grabble.enterLetter('z')){constructString('z',2);}; updateUI(); break;

                    case R.id.scrabble_complete_button:
                        if (grabble.getPoint() == 7){
                            addScore(grabble.completeWord());
                        };break;
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
                    case R.id.scrabble_layout_help:
                        mHelpLayout.setVisibility(View.INVISIBLE);
                        mWarning1.setVisibility(View.VISIBLE);
                        mWarning2.setVisibility(View.VISIBLE);
                        break;
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
        mHelpLayout.setOnClickListener(shortListener);

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

        //TODO remove test cheat layout
        mInput = (RelativeLayout) view.findViewById(R.id.scrabble_layout_input);
        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(score);
                updateUI();
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences save = getActivity().getSharedPreferences(PREF_SAVE, 0);
        SharedPreferences.Editor editor = save.edit();
        int[] letters = grabble.getAll();
        for (int i = 0 ; i < 26 ; i++) {editor.putInt("letter" + i,letters[i]);}
        editor.putInt("eagle",mEagle);
        editor.putInt("grabber",mGrabber);
        editor.commit();
    }

    public void addScore(int score){
        int newScore = exp + score;
        //TODO add level details
        if (newScore/170>level){
            level = newScore/170;
            Toast.makeText(getActivity().getApplicationContext(),
                    "Leveru Apuda!",
                    Toast.LENGTH_SHORT).show();
        }
        exp = newScore;
        updateUI();
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
        if (point > 6 ){mInput7.setImageResource(grabble.getImage(input[6]));}
            else {mInput7.setImageResource(R.drawable.scrabble_button);}
        mLevel.setText("Level: "+level);
        mExp.setText("Exp: "+exp);
        mScore.setText("Total Score: "+score);
    }

    public void constructString(char c, int type){
        //Construct different hints, case 1 is check amount of letter left,
        //                           case 2 tells player there are no letter left;
        int amount = grabble.getAmount(c);
        switch (type){
            case 1: if (amount == 0){
                        mWarning2.setText("You have " + "no" +
                        " letter " + Character.toUpperCase(c) +
                        " Left.");
                    }else{
                        mWarning2.setText("You have " + grabble.getAmount(c) +
                        " letter " + Character.toUpperCase(c) +
                        " Left.");
                    }break;
            case 2: mWarning2.setText("You don't have any letter " +
                                        Character.toUpperCase(c) +
                                        " Left!");break;
        }

    }
}

