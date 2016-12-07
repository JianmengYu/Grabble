package com.yujianmeng.selp.grabble;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yujianmeng.selp.grabble.AchievementDbSchema.AchievementTable;
/**
 * Created by YuJianmeng on 2016/12/7.
 */
public class AchievementBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "achievementBase.db";

    public AchievementBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AchievementTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                AchievementTable.Cols.UUID + ", " +
                AchievementTable.Cols.NAMES + ", " +
                AchievementTable.Cols.DESCRIPTION + ", " +
                AchievementTable.Cols.HINT + ", " +
                AchievementTable.Cols.TIME +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
