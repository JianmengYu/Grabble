package com.yujianmeng.selp.grabble;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class FragmentAbout extends Fragment {

    private TextView mA1N;
    private TextView mA2N;
    private TextView mA3N;
    private TextView mA5N;
    private TextView mA6N;
    private TextView mA7N;
    private TextView mA1V;
    private TextView mA2V;
    private TextView mA3V;

    private TextView mSystem;
    private TextView mAPI;
    private TextView mGoogle;
    private ImageView mShare;

    private Typeface mFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        mFont = Typeface.createFromAsset(getActivity().getAssets(), "generica_bold.otf");

        mA1N = (TextView) view.findViewById(R.id.about_1_name);
        mA2N = (TextView) view.findViewById(R.id.about_2_name);
        mA3N = (TextView) view.findViewById(R.id.about_3_name);
        mA5N = (TextView) view.findViewById(R.id.about_5_name);
        mA6N = (TextView) view.findViewById(R.id.about_6_name);
        mA7N = (TextView) view.findViewById(R.id.about_7_name);
        mA1V = (TextView) view.findViewById(R.id.about_1_value);
        mA2V = (TextView) view.findViewById(R.id.about_2_value);
        mA3V = (TextView) view.findViewById(R.id.about_3_value);
        mSystem = (TextView) view.findViewById(R.id.about_your_system);
        mAPI = (TextView) view.findViewById(R.id.about_your_api);
        mGoogle = (TextView) view.findViewById(R.id.about_your_google);
        mShare = (ImageView) view.findViewById(R.id.about_share_button);

        mA1N.setTypeface(mFont);mA1V.setTypeface(mFont);
        mA2N.setTypeface(mFont);mA2V.setTypeface(mFont);
        mA3N.setTypeface(mFont);mA3V.setTypeface(mFont);
        mA5N.setTypeface(mFont);mSystem.setTypeface(mFont);
        mA6N.setTypeface(mFont);mAPI.setTypeface(mFont);
        mA7N.setTypeface(mFont);mGoogle.setTypeface(mFont);
        mSystem.setText(Build.VERSION.RELEASE);
        mAPI.setText(String.valueOf(Build.VERSION.SDK_INT));
        int versionInt = GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        while (versionInt%10==0){versionInt = versionInt /10;}
        String versionName = String.valueOf(versionInt);
        mGoogle.setText(versionName.charAt(0) + "." +
                         versionName.charAt(1) + "." +
                         versionName.substring(2));

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/9948373/android-share-plain-text-using-intent-to-all-messaging-apps
                Intent intent1 = new Intent(); intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, "Grabble: the location based " +
                                                      "scrabble game. Get it now at: " +
                                                      "https://github.com/JianmengYu");
                startActivity(Intent.createChooser(intent1, "Share via"));
            }
        });

        return view;
    }

}
