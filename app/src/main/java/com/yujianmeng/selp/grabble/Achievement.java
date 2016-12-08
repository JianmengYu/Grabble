package com.yujianmeng.selp.grabble;

import java.util.Date;
import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/3.
 */
public class Achievement {

    private UUID mID;
    private String mName;
    private String mHint;
    private String mDescription;
    private String mDate;
    //Store date as string of unlocked date, for easy use..

    public Achievement(){
        this(UUID.randomUUID());
    }

    public Achievement(UUID id){
        mID = id;
    }

    //Special Getters
    public int getImageString(){
        switch (mName){
            //TODO achievement Image
            case "A New Player in Town":return R.drawable.achievement_100;
            case "Grabbled": return R.drawable.achievement_1000;
            case "It's OVER 9000!": return R.drawable.achievement_9000;
            case "Obsessed": return R.drawable.achievement_50000;
            case "Maniac!": return R.drawable.achievement_100000;
            case "Toddler": return R.drawable.achievement_toddler;
            case "Bachelor": return R.drawable.achievement_bachelor;
            case "Madness?": return R.drawable.achievement_spartan;
            case "Realm of Freedom": return R.drawable.achievement_america;
            case "Monkey Lover": return R.drawable.achievement_monkey1;
            case "Gorillaphilia": return R.drawable.achievement_monkey2;
            case "Planet Apes: the Musical": return R.drawable.achievement_drzaius;
            case "The Gorilla Savior": return R.drawable.achievement_harambe;
            default: return R.drawable.achievement_icon_lock;
        }
    }

    //general Getter and setters
    public UUID getmID() {
        return mID;
    }
    public void setmID(UUID mID) {
        this.mID = mID;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public String getmHint() {
        return mHint;
    }
    public void setmHint(String mHint) {
        this.mHint = mHint;
    }
    public String getmDescription() {
        return mDescription;
    }
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public String getmDate() {
        return mDate;
    }
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
