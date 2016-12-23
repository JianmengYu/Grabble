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
            case "A New Player in Town":return R.drawable.achievement_100;
            case "Grabbled": return R.drawable.achievement_1000;
            case "It's OVER 9000!": return R.drawable.achievement_9000;
            case "Obsessed": return R.drawable.achievement_50000;
            case "Maniac!": return R.drawable.achievement_100000;

            case "Walker": return R.drawable.achievement_1km;
            case "Runner" : return R.drawable.achievement_10km;
            case "Marathon Runner" : return R.drawable.achievement_42km;
            case "Flash": return R.drawable.achievement_100km;
            case "Traveler" : return R.drawable.achievement_500km;

            case "Collector" : return R.drawable.achievement_col_20;
            case "Scavenger" : return R.drawable.achievement_col_10;
            case "Pack Rat" : return R.drawable.achievement_col_500;
            case "Connoisseur" : return R.drawable.achievement_col_1000;
            case "Antiquarian" : return R.drawable.achievement_col_all;

            case "My First Word!" : return R.drawable.achievement_word_1;
            case "Out of Words.. Almost" : return R.drawable.achievement_word_10;
            case "Knows how to Google" : return R.drawable.achievement_word_100;
            case "Linguist" : return R.drawable.achievement_word_500;
            case "Living Dictionary" : return R.drawable.achievement_word_1000;

            case "Grabber User" : return R.drawable.achievement_grab5;
            case "Lazy Grabber User" : return R.drawable.achievement_grab50;
            case "Peeping Tom" : return R.drawable.achievement_eagle5;
            case "George Square have eyes" : return R.drawable.achievement_eagle50;
            case "Bona Fide Player" : return R.drawable.achievement_item200;

            case "Toddler": return R.drawable.achievement_toddler;
            case "Bachelor": return R.drawable.achievement_bachelor;
            case "Clueless" : return R.drawable.achievement_clueless;
            case "Still Clueless" : return R.drawable.achievement_grabble;
            case "Madness?": return R.drawable.achievement_spartan;
            case "Realm of Freedom": return R.drawable.achievement_america;
            case "Hillarious": return R.drawable.achievement_hill;
            case "President Musician": return R.drawable.achievement_trump;
            case "Monkey Lover": return R.drawable.achievement_monkey1;
            case "Gorillaphilia": return R.drawable.achievement_monkey2;

            case "Planet Apes: the Musical": return R.drawable.achievement_drzaius;
            case "The Gorilla Savior": return R.drawable.achievement_harambe;
            case "MLG gamer": return R.drawable.achievement_winston;
            case "Super Saiyan": return R.drawable.achievement_goku;
            case "Top of the Revolution": return R.drawable.achievement_primate;
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
