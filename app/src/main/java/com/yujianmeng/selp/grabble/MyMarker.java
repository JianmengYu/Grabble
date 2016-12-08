package com.yujianmeng.selp.grabble;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/8.
 */
public class MyMarker {

    private UUID mID;
    private char mName;
    private String mDescription;
    private double mLat;
    private double mLng;
    private boolean mCollected;

    public MyMarker(){
        this(UUID.randomUUID());
    }

    public MyMarker(UUID id){
        mID = id;
    }

    //Special Getter/Setter
    public LatLng getLatLng() {
        return new LatLng(mLat,mLng);
    }
    public void setLatLng(LatLng latLng){
        mLat = latLng.latitude;
        mLng = latLng.longitude;
    }

    //General Getter/Setter
    public UUID getmID() {
        return mID;
    }
    public void setmID(UUID mID) {
        this.mID = mID;
    }
    public String getmDescription() {
        return mDescription;
    }
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public char getmName() {
        return mName;
    }
    public void setmName(char mName) {
        this.mName = mName;
    }
    public boolean ismCollected() {
        return mCollected;
    }
    public void setmCollected(boolean mCollected) {
        this.mCollected = mCollected;
    }
}
