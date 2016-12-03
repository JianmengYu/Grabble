package com.yujianmeng.selp.grabble;

import java.util.Date;
import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/3.
 */
public class Achievement {

    private UUID mID;
    private int mImage;
    private String mName;
    private String mHint;
    private String mDescription;
    private Date mDate;

    public Achievement(){
        this(UUID.randomUUID());
    }

    public Achievement(UUID id){
        mID = id;
    }

    //Special Getters
    public int getImageString(){
        switch (mImage){
            case 0:return R.drawable.achievement_icon_sample1;
            case 1:return R.drawable.achievement_icon_sample2;
            case 2:return R.drawable.achievement_icon_lock;
            default: return 0;
        }
    }

    //general Getter and setters
    public UUID getmID() {
        return mID;
    }
    public void setmID(UUID mID) {
        this.mID = mID;
    }
    public int getmImage() {
        return mImage;
    }
    public void setmImage(int mImage) {
        this.mImage = mImage;
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
    public Date getmDate() {
        return mDate;
    }
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
