package com.yujianmeng.selp.grabble;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YuJianmeng on 2016/10/6.
 */

public class FragmentMainPage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        return view;
    }
}
