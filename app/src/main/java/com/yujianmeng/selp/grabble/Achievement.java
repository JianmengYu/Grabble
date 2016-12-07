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
