package com.yujianmeng.selp.grabble;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yujianmeng.selp.grabble.MarkerDbSchema.MarkerTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/8.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class MarkerLab {

    private static final String PREF_SAVE = "MySaveFile";

    private static MarkerLab sMarkerLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MarkerLab get(Context context){
        if (sMarkerLab == null){
            sMarkerLab = new MarkerLab(context);
        }
        return sMarkerLab;
    }

    private MarkerLab (Context context){
        mContext = context.getApplicationContext();
        mDatabase = new MarkerBaseHelper(mContext).getWritableDatabase();
        SharedPreferences save = mContext.getSharedPreferences(PREF_SAVE, 0);
        if (save.getBoolean("deleteFlag",false)){
            deleteAll();
            SharedPreferences.Editor editor = save.edit();
            editor.putBoolean("deleteFlag",false);
            editor.commit();
        }
        //Skip input if there exist databse?
        MarkerCursorWrapper cursor = queryMarkers(null,null);
        if (cursor.getCount() == 0) {
            //http://stackoverflow.com/questions/1140144/read-and-parse-kml-in-java
            //Read kml as strings, and only take description and coordinates.
            try {
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                Calendar calendar = Calendar.getInstance();
                String weekDay = dayFormat.format(calendar.getTime()).toLowerCase();
                Log.i("READFILE","started");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Android/data/com.yujianmeng.selp.grabble/" + weekDay + ".kml");
                FileInputStream fis = new FileInputStream(file);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String str;
                Character name = null;
                String description = null;
                while ((str = in.readLine()) != null) {
                    if (str.contains("<description>")) {
                        str = str.replace("<description>", "").replace("</description>", "").replace(" ", "");
                        name = str.charAt(0);
                    }
                    if (str.contains("<name>")) {
                        description = str.replace("<name>", "").replace("</name>", "").replace(" ", "");
                    }
                    if (str.contains("<coordinates>")) {
                        str = str.replace("<coordinates>", "").replace("</coordinates>", "").replace(" ", "");
                        String[] coord = str.split(",");
                        ContentValues values = new ContentValues();
                        values.put(MarkerTable.Cols.UUID, UUID.randomUUID().toString());
                        values.put(MarkerTable.Cols.NAMES, name.toString());
                        values.put(MarkerTable.Cols.DESCRIPTION, description);
                        values.put(MarkerTable.Cols.LAT, Double.valueOf(coord[1]));
                        values.put(MarkerTable.Cols.LNG, Double.valueOf(coord[0]));
                        values.put(MarkerTable.Cols.COLLECTED,false);
                        addMarker(values);
                        Log.i("READFILE",description + ": " + name + " added!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<MyMarker> getMarkers(){
        List<MyMarker> markers = new ArrayList<>();
        MarkerCursorWrapper cursor = queryMarkers(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                markers.add(cursor.getMarker());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return markers;
    }

    public MyMarker getMarker(UUID id){
        MarkerCursorWrapper cursor = queryMarkers(
                MarkerTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMarker();
        } finally {
            cursor.close();
        }
    }

    public void updateMarkers(String description){
        //Set the marker at location to collected?
        MarkerCursorWrapper cursor = queryMarkers(
                MarkerTable.Cols.DESCRIPTION + " = ?",
                new String[] {description });
        MyMarker updateMarker;
        try {
            cursor.moveToFirst();
            updateMarker = cursor.getMarker();
        } finally {
            cursor.close();
        }
        String uuid  = updateMarker.getmID().toString();
        updateMarker.setmCollected(true);
        ContentValues values = getContentValues(updateMarker);
        mDatabase.update(MarkerTable.NAME, values,
                MarkerTable.Cols.UUID + " = ?",
                new String[] {uuid});
    }

    private static ContentValues getContentValues(MyMarker marker){
        ContentValues ret = new ContentValues();
        ret.put(MarkerTable.Cols.UUID, marker.getmID().toString());
        ret.put(MarkerTable.Cols.NAMES, marker.getmName() + "");
        ret.put(MarkerTable.Cols.DESCRIPTION, marker.getmDescription());
        ret.put(MarkerTable.Cols.LAT, marker.getLatLng().latitude);
        ret.put(MarkerTable.Cols.LNG,marker.getLatLng().longitude);
        ret.put(MarkerTable.Cols.COLLECTED,marker.ismCollected());
        return ret;
    }

    private MarkerCursorWrapper queryMarkers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MarkerTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return  new MarkerCursorWrapper(cursor);
    }

    private void addMarker(ContentValues values){
        //If marker is in database, don't add it.
        //Move this to a more efficient place, if possible
            mDatabase.insert(MarkerTable.NAME,null,values);
    }

    public void deleteAll(){
        //Drop all marker record.. and set static marker lab to null?
        sMarkerLab = null;
        mDatabase.execSQL("delete from "+ MarkerTable.NAME);
    }
}
