package com.yujianmeng.selp.grabble;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class ActivityPager extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        initPaging();
    }

    private void initPaging() {
        //code from http://stackoverflow.com/questions/9849138/android-pagerview-between-activities

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FragmentAchievement());
        pagerAdapter.addFragment(new FragmentStatistics());
        pagerAdapter.addFragment(new FragmentSettings());
        pagerAdapter.addFragment(new FragmentAbout());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);//Prevent Achievement List got destroyed
    }
}
