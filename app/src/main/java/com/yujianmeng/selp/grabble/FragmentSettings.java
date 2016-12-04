package com.yujianmeng.selp.grabble;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSettings extends Fragment {

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

    private boolean mB1isOn = false;
    private boolean mB2isOn = true;
    private boolean mB3isOn = false;
    private boolean mB4isOn = false;

    private Typeface mFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

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

        mS1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mB1isOn){mB1isOn = false;updateUI();}
                else{mB1isOn = true;updateUI();}
            }
        });
        mS2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mB2isOn){mB2isOn = false;updateUI();}
                else{mB2isOn = true;updateUI();}
            }
        });
        mS3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mB3isOn){mB3isOn = false;updateUI();}
                else{mB3isOn = true;updateUI();}
            }
        });
        mS4B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mB4isOn){Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry, no deal.",
                        Toast.LENGTH_SHORT).show();}
                else{mB4isOn = true;updateUI();}
            }
        });
        mS5B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "BOOM!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        updateUI();
        return view;
    }

    private void updateUI(){
        if(mB1isOn){mS1B.setImageResource(R.drawable.settings_button_on);}
                else{mS1B.setImageResource(R.drawable.settings_button_off);}
        if(mB2isOn){mS2B.setImageResource(R.drawable.settings_button_on);}
                else{mS2B.setImageResource(R.drawable.settings_button_off);}
        if(mB3isOn){mS3B.setImageResource(R.drawable.settings_button_on);}
                else{mS3B.setImageResource(R.drawable.settings_button_off);}
        if(mB4isOn){mS4B.setImageResource(R.drawable.settings_button_on);}
                else{mS4B.setImageResource(R.drawable.settings_button_off);}
    }

}
