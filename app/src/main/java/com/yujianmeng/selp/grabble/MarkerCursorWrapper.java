package com.yujianmeng.selp.grabble;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.android.gms.maps.model.LatLng;
import com.yujianmeng.selp.grabble.MarkerDbSchema.MarkerTable;

import java.util.UUID;

/**
 * Created by YuJianmeng on 2016/12/8.
 */
public class MarkerCursorWrapper extends CursorWrapper {

    public MarkerCursorWrapper(Cursor cursor) { super (cursor); }

    public MyMarker getMarker(){
        String uuid = getString(getColumnIndex(MarkerTable.Cols.UUID));
        char name = getString(getColumnIndex(MarkerTable.Cols.NAMES)).charAt(0);
        String description = getString(getColumnIndex(MarkerTable.Cols.DESCRIPTION));
        double lat = Double.valueOf(getString(getColumnIndex(MarkerTable.Cols.LAT)));
        double lng = Double.valueOf(getString(getColumnIndex(MarkerTable.Cols.LNG)));
        int collected = getInt(getColumnIndex(MarkerTable.Cols.COLLECTED));

        MyMarker marker = new MyMarker(UUID.fromString(uuid));
        marker.setmName(name);
        marker.setmDescription(description);
        marker.setLatLng(new LatLng(lat,lng));
        marker.setmCollected(collected != 0);
        return marker;
    }
}
