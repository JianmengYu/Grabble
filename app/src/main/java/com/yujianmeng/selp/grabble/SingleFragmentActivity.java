package com.yujianmeng.selp.grabble;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}