package com.yujianmeng.selp.grabble;

/**
 * Created by YuJianmeng on 2016/12/18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yujianmeng.selp.grabble.DictionaryDbSchema.DictionaryTable;
/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class DictionaryBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dictionary.db";

    public DictionaryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DictionaryTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                DictionaryTable.Cols.WORD + ", " +
                DictionaryTable.Cols.USED +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
