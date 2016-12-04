package com.yujianmeng.selp.grabble;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentStatistics extends Fragment {

    // SX means Xth statistic, N/V stands for name/value
    private TextView mS1N;
    private TextView mS1V;
    private TextView mS2N;
    private TextView mS2V;
    private TextView mS3N;
    private TextView mS3V;
    private TextView mS4N;
    private TextView mS4V;
    private TextView mS5N;
    private TextView mS5V;
    private TextView mS6N;
    private TextView mS6V;
    private TextView mS7N;
    private TextView mS7V;
    private TextView mS8N;
    private TextView mS8V;
    private TextView mS9N;
    private TextView mS9V;
    private TextView mS10N;
    private TextView mS10V;
    private TextView mS11N;
    private TextView mS11V;
    private TextView mS12N;
    private TextView mS12V;
    private TextView mS13N;
    private TextView mS13V;

    private Typeface mFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mFont = Typeface.createFromAsset(getActivity().getAssets(), "generica_bold.otf");

        mS1N = (TextView) view.findViewById(R.id.statistics_01_name);
        mS2N = (TextView) view.findViewById(R.id.statistics_02_name);
        mS3N = (TextView) view.findViewById(R.id.statistics_03_name);
        mS4N = (TextView) view.findViewById(R.id.statistics_04_name);
        mS5N = (TextView) view.findViewById(R.id.statistics_05_name);
        mS6N = (TextView) view.findViewById(R.id.statistics_06_name);
        mS7N = (TextView) view.findViewById(R.id.statistics_07_name);
        mS8N = (TextView) view.findViewById(R.id.statistics_08_name);
        mS9N = (TextView) view.findViewById(R.id.statistics_09_name);
        mS10N = (TextView) view.findViewById(R.id.statistics_10_name);
        mS11N = (TextView) view.findViewById(R.id.statistics_11_name);
        mS12N = (TextView) view.findViewById(R.id.statistics_12_name);
        mS13N = (TextView) view.findViewById(R.id.statistics_13_name);

        mS1V = (TextView) view.findViewById(R.id.statistics_01_value);
        mS2V = (TextView) view.findViewById(R.id.statistics_02_value);
        mS3V = (TextView) view.findViewById(R.id.statistics_03_value);
        mS4V = (TextView) view.findViewById(R.id.statistics_04_value);
        mS5V = (TextView) view.findViewById(R.id.statistics_05_value);
        mS6V = (TextView) view.findViewById(R.id.statistics_06_value);
        mS7V = (TextView) view.findViewById(R.id.statistics_07_value);
        mS8V = (TextView) view.findViewById(R.id.statistics_08_value);
        mS9V = (TextView) view.findViewById(R.id.statistics_09_value);
        mS10V = (TextView) view.findViewById(R.id.statistics_10_value);
        mS11V = (TextView) view.findViewById(R.id.statistics_11_value);
        mS12V = (TextView) view.findViewById(R.id.statistics_12_value);
        mS13V = (TextView) view.findViewById(R.id.statistics_13_value);

        mS1N.setTypeface(mFont);mS1V.setTypeface(mFont);
        mS2N.setTypeface(mFont);mS2V.setTypeface(mFont);
        mS3N.setTypeface(mFont);mS3V.setTypeface(mFont);
        mS4N.setTypeface(mFont);mS4V.setTypeface(mFont);
        mS5N.setTypeface(mFont);mS5V.setTypeface(mFont);
        mS6N.setTypeface(mFont);mS6V.setTypeface(mFont);
        mS7N.setTypeface(mFont);mS7V.setTypeface(mFont);
        mS8N.setTypeface(mFont);mS8V.setTypeface(mFont);
        mS9N.setTypeface(mFont);mS9V.setTypeface(mFont);
        mS10N.setTypeface(mFont);mS10V.setTypeface(mFont);
        mS11N.setTypeface(mFont);mS11V.setTypeface(mFont);
        mS12N.setTypeface(mFont);mS12V.setTypeface(mFont);
        mS13N.setTypeface(mFont);mS13V.setTypeface(mFont);

        return view;
    }

}
