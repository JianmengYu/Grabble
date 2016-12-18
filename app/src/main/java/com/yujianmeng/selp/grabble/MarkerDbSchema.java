package com.yujianmeng.selp.grabble;

/**
 * Created by YuJianmeng on 2016/12/8.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class MarkerDbSchema {

    public static final class MarkerTable{
        public static final String NAME = "markers";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAMES = "name";
            public static final String DESCRIPTION = "description";
            public static final String LAT = "latitude";
            public static final String LNG = "longitude";
            public static final String COLLECTED = "collected";
        }
    }
}
