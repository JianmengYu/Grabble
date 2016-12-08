package com.yujianmeng.selp.grabble;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yujianmeng.selp.grabble.MarkerDbSchema.MarkerTable;

/**
 * Created by YuJianmeng on 2016/12/8.
 */
public class MarkerBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "markerBase.db";

    public MarkerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MarkerTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                MarkerTable.Cols.UUID + ", " +
                MarkerTable.Cols.NAMES + ", " +
                MarkerTable.Cols.DESCRIPTION + ", " +
                MarkerTable.Cols.LAT + ", " +
                MarkerTable.Cols.LNG + ", " +
                MarkerTable.Cols.COLLECTED +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
