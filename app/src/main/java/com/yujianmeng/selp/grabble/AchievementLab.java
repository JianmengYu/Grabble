package com.yujianmeng.selp.grabble;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/3.
 */

//TODO yet another copyright stuff
public class AchievementLab {

    private static AchievementLab sAchievementLab;
    private List<Achievement> mAchievements;

    public static AchievementLab get(Context context){
        if(sAchievementLab == null){
            sAchievementLab = new AchievementLab(context);
        }
        return sAchievementLab;
    }

    private AchievementLab(Context context){
        mAchievements = new ArrayList<>();
        //TODO remove random achievement generator and implement database
        for (int i=0;i<100;i++){
            Achievement sb = new Achievement();
            sb.setmImage(i % 3);
            if(i%2 == 0){sb.setmDate(new Date());}
            sb.setmName("Name Here");
            sb.setmHint("Hint: One of your ancestor is fat, not your da");
            sb.setmDescription("OyeaUgotIt! Wubba Lubba Dub Dub!Long!");
            mAchievements.add(sb);
        }
    }

    public List<Achievement> getAchievements(){
        return mAchievements;
    }

    public Achievement getAchievement(UUID id){
        for (Achievement a : mAchievements){
            if (a.getmID().equals(id)){
                return a;
            }
        }
        return null;
    }
}
