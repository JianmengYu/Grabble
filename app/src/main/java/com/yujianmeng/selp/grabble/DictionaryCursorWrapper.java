package com.yujianmeng.selp.grabble;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.yujianmeng.selp.grabble.DictionaryDbSchema.DictionaryTable;
/**
 * Created by YuJianmeng on 2016/12/18.
 */
public class DictionaryCursorWrapper extends CursorWrapper{

    public DictionaryCursorWrapper(Cursor cursor) { super (cursor); }

    public Dictionary getDictionary(){
        String word  = getString(getColumnIndex(DictionaryTable.Cols.WORD));
        int used = getInt(getColumnIndex(DictionaryTable.Cols.USED));
        return new Dictionary(word,used);
    }
}
