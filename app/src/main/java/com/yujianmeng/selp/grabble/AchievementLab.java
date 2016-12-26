package com.yujianmeng.selp.grabble;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.yujianmeng.selp.grabble.AchievementDbSchema.AchievementTable;
/**
 * Created by YuJianmeng on 2016/12/3.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class AchievementLab {

    private static AchievementLab sAchievementLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static AchievementLab get(Context context){
        if(sAchievementLab == null){
            sAchievementLab = new AchievementLab(context);
        }
        return sAchievementLab;
    }

    private AchievementLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new AchievementBaseHelper(mContext).getWritableDatabase();
        try {
            InputStream inputStream = mContext.getAssets().open("achievements.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                Achievement achievement = new Achievement();
                achievement.setmName(row[0]);
                achievement.setmDescription(row[1]);
                achievement.setmHint(row[2]);
                achievement.setmDate("No Unlocked Yet");
                addAchievement(achievement);
            }
            inputStream.close();
        } catch (Exception e) {
        }
    }

    public List<Achievement> getAchievements(){
        List<Achievement> achievements = new ArrayList<>();
        AchievementCursorWrapper cursor = queryAchievements(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                achievements.add(cursor.getAchievement());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return achievements;
    }

    public Achievement getAchievement(UUID id){
        AchievementCursorWrapper cursor = queryAchievements(
                AchievementTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAchievement();
        } finally {
            cursor.close();
        }
    }

    public boolean updateAchievement(String achievementName){
        AchievementCursorWrapper cursor = queryAchievements(
                AchievementTable.Cols.NAMES + " = ?",
                new String[] {achievementName});
        boolean completed;
        try {
            cursor.moveToFirst();
            Achievement change = cursor.getAchievement();
            completed = change.getmDate().equals("No Unlocked Yet");
            if (completed) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss,yyyy-MM-dd");
                change.setmDate("Unlocked at: " + dateFormat.format(date));
                ContentValues values = getContentValues(change);
                mDatabase.update(AchievementTable.NAME, values, AchievementTable.Cols.UUID + " = ?",
                        new String[]{change.getmID().toString()});
            }else{
                return false;
            }
        } finally {
            cursor.close();
        }
        return true;
    }

    private static ContentValues getContentValues(Achievement achievement){
        ContentValues ret = new ContentValues();
        ret.put(AchievementTable.Cols.UUID, achievement.getmID().toString());
        ret.put(AchievementTable.Cols.NAMES, achievement.getmName());
        ret.put(AchievementTable.Cols.DESCRIPTION, achievement.getmDescription());
        ret.put(AchievementTable.Cols.HINT,achievement.getmHint());
        ret.put(AchievementTable.Cols.TIME,achievement.getmDate());
        return ret;
    }

    private AchievementCursorWrapper queryAchievements(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AchievementTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return  new AchievementCursorWrapper(cursor);
    }

    private void addAchievement(Achievement a){
        //If achievement is in database, don't add it.
        //Move this to a more efficient place, if possible;
        //Since there wasn't many achievements..
        String acName = a.getmName();
        AchievementCursorWrapper cursor = queryAchievements(
                AchievementTable.Cols.NAMES + " = ?",
                new String[] {acName}
        );
        if (cursor.getCount() == 0) {
            ContentValues values = getContentValues(a);
            mDatabase.insert(AchievementTable.NAME,null,values);
            //Log.i("error!","NEW ACHIEVEMENT ADDED!");
        }
        cursor.close();
    }

    public void deleteAll(){
        //Drop all marker record.. and set static marker lab to null?
        sAchievementLab = null;
        mDatabase.execSQL("delete from "+ AchievementTable.NAME);
    }
}
