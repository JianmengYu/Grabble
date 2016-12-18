package com.yujianmeng.selp.grabble;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.yujianmeng.selp.grabble.DictionaryDbSchema.DictionaryTable;
/**
 * Created by YuJianmeng on 2016/12/18.
 */
public class DictionaryLab {

    private static DictionaryLab sDictionaryLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DictionaryLab get(Context context){
        if(sDictionaryLab == null){
            sDictionaryLab = new DictionaryLab(context);
        }
        return sDictionaryLab;
    }

    private DictionaryLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DictionaryBaseHelper(mContext).getWritableDatabase();
        DictionaryCursorWrapper cursor = queryDictionary(null,null);
        if (cursor.getCount() == 0) {
            try {
                int i = 0;
                int j = 0;
                InputStream inputStream = mContext.getAssets().open("grabble.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    i++;
                    if (i == 99){j++;Log.i("TA",j+"00");i=0;}
                    line = line.toLowerCase();
                    ContentValues d = new ContentValues();
                    d.put("word",line);
                    d.put("used",0);
                    addDictionary(d);
                }
                inputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public List<String> getDictionary(){
        //Get all words.
        List<String> dictionary = new ArrayList<>();
        DictionaryCursorWrapper cursor = queryDictionary(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dictionary.add(cursor.getDictionary().getWord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return dictionary;
    }

    public int incrementDictUse(String word){
        DictionaryCursorWrapper cursor = queryDictionary(
                DictionaryTable.Cols.WORD + " = ?",
                new String[] {word });
        Dictionary match;
        try {
            cursor.moveToFirst();
            match = cursor.getDictionary();
        } finally {
            cursor.close();
        }
        match.setUsed(match.getUsed() + 1);
        ContentValues values = getContentValues(match);
        mDatabase.update(DictionaryTable.NAME,values,
                DictionaryTable.Cols.WORD + " = ?",
                new String[] {word});
        return match.getUsed();
    }

    private static ContentValues getContentValues(Dictionary dictionary){
        ContentValues ret = new ContentValues();
        ret.put(DictionaryTable.Cols.WORD, dictionary.getWord());
        ret.put(DictionaryTable.Cols.USED, dictionary.getUsed());
        return ret;
    }

    private DictionaryCursorWrapper queryDictionary(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DictionaryTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return  new DictionaryCursorWrapper(cursor);
    }

    private void addDictionary(ContentValues values){
        mDatabase.insert(DictionaryTable.NAME,null,values);
    }
}
