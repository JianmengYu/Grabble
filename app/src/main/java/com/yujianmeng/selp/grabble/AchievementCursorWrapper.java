package com.yujianmeng.selp.grabble;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.yujianmeng.selp.grabble.AchievementDbSchema.AchievementTable;

import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/7.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class AchievementCursorWrapper extends CursorWrapper{
    public AchievementCursorWrapper(Cursor cursor) { super (cursor); }

    public Achievement getAchievement(){
        String uuid = getString(getColumnIndex(AchievementTable.Cols.UUID));
        String name = getString(getColumnIndex(AchievementTable.Cols.NAMES));
        String description = getString(getColumnIndex(AchievementTable.Cols.DESCRIPTION));
        String hint = getString(getColumnIndex(AchievementTable.Cols.HINT));
        String date = getString(getColumnIndex(AchievementTable.Cols.TIME));
        Achievement achievement = new Achievement(UUID.fromString(uuid));
        achievement.setmName(name);
        achievement.setmDescription(description);
        achievement.setmHint(hint);
        achievement.setmDate(date);
        return achievement;
    }
}
