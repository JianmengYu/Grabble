package com.yujianmeng.selp.grabble;

/**
 * Created by YuJianmeng on 2016/12/18.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class DictionaryDbSchema {
    public static final class DictionaryTable {
        public static final String NAME = "dictionary";

        public static final class Cols{
            public static final String WORD = "word";
            public static final String USED = "used";
        }
    }
}
