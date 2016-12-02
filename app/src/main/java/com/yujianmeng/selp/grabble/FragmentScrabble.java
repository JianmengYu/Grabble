package com.yujianmeng.selp.grabble;

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

public class FragmentScrabble extends Fragment implements View.OnClickListener{

    private TextView mLevel;
    private TextView mExp;
    private TextView mScore;
    private TextView mDiscard;
    private ImageView mComplete;

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

    private Grabble grabble = new Grabble();

    private int exp = 3400;
    private int level = 20;
    private int score = grabble.getPoint();

    private Typeface mFont;

    //Temp stuff
    private RelativeLayout mInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrabble, container, false);

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
        mButtonA.setOnClickListener(this);
        mButtonB.setOnClickListener(this);
        mButtonC.setOnClickListener(this);
        mButtonD.setOnClickListener(this);
        mButtonE.setOnClickListener(this);
        mButtonF.setOnClickListener(this);
        mButtonG.setOnClickListener(this);
        mButtonH.setOnClickListener(this);
        mButtonI.setOnClickListener(this);
        mButtonJ.setOnClickListener(this);
        mButtonK.setOnClickListener(this);
        mButtonL.setOnClickListener(this);
        mButtonM.setOnClickListener(this);
        mButtonN.setOnClickListener(this);
        mButtonO.setOnClickListener(this);
        mButtonP.setOnClickListener(this);
        mButtonQ.setOnClickListener(this);
        mButtonR.setOnClickListener(this);
        mButtonS.setOnClickListener(this);
        mButtonT.setOnClickListener(this);
        mButtonU.setOnClickListener(this);
        mButtonV.setOnClickListener(this);
        mButtonW.setOnClickListener(this);
        mButtonX.setOnClickListener(this);
        mButtonY.setOnClickListener(this);
        mButtonZ.setOnClickListener(this);

        mComplete.setOnClickListener(this);
        mDiscard.setOnClickListener(this);
        mDiscard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                grabble.discardAll();
                updateUI();
                return false;
            }
        });

        //Temp stuff
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scrabble_input_a: grabble.enterLetter('a'); updateUI(); break;
            case R.id.scrabble_input_b: grabble.enterLetter('b'); updateUI(); break;
            case R.id.scrabble_input_c: grabble.enterLetter('c'); updateUI(); break;
            case R.id.scrabble_input_d: grabble.enterLetter('d'); updateUI(); break;
            case R.id.scrabble_input_e: grabble.enterLetter('e'); updateUI(); break;
            case R.id.scrabble_input_f: grabble.enterLetter('f'); updateUI(); break;
            case R.id.scrabble_input_g: grabble.enterLetter('g'); updateUI(); break;
            case R.id.scrabble_input_h: grabble.enterLetter('h'); updateUI(); break;
            case R.id.scrabble_input_i: grabble.enterLetter('i'); updateUI(); break;
            case R.id.scrabble_input_j: grabble.enterLetter('j'); updateUI(); break;
            case R.id.scrabble_input_k: grabble.enterLetter('k'); updateUI(); break;
            case R.id.scrabble_input_l: grabble.enterLetter('l'); updateUI(); break;
            case R.id.scrabble_input_m: grabble.enterLetter('m'); updateUI(); break;
            case R.id.scrabble_input_n: grabble.enterLetter('n'); updateUI(); break;
            case R.id.scrabble_input_o: grabble.enterLetter('o'); updateUI(); break;
            case R.id.scrabble_input_p: grabble.enterLetter('p'); updateUI(); break;
            case R.id.scrabble_input_q: grabble.enterLetter('q'); updateUI(); break;
            case R.id.scrabble_input_r: grabble.enterLetter('r'); updateUI(); break;
            case R.id.scrabble_input_s: grabble.enterLetter('s'); updateUI(); break;
            case R.id.scrabble_input_t: grabble.enterLetter('t'); updateUI(); break;
            case R.id.scrabble_input_u: grabble.enterLetter('u'); updateUI(); break;
            case R.id.scrabble_input_v: grabble.enterLetter('v'); updateUI(); break;
            case R.id.scrabble_input_w: grabble.enterLetter('w'); updateUI(); break;
            case R.id.scrabble_input_x: grabble.enterLetter('x'); updateUI(); break;
            case R.id.scrabble_input_y: grabble.enterLetter('y'); updateUI(); break;
            case R.id.scrabble_input_z: grabble.enterLetter('z'); updateUI(); break;

            case R.id.scrabble_complete_button:
                if (grabble.getPoint() == 7){
                    addScore(grabble.completeWord());
                };break;
            case R.id.scrabble_discard_button:
                grabble.discardLetter();
                updateUI();
                break;
        }
    }
}

