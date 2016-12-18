package com.yujianmeng.selp.grabble;

import android.support.v4.app.Fragment;

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new FragmentIntro();
    }

}
