package com.yujianmeng.selp.grabble;

/**
 * Created by YuJianmeng on 2016/12/7.
 */

/** DISCLAIMER: the following code are adapted from
 * <<Android Programming: The Big Nerd Ranch Guide>> 2nd Edition
 * by Bill Phillips, Chris Stewart, Brian Hardy and Kristin Marsicano
 * copyright 2015 Big Nerd Ranch, LLC.
 */
public class AchievementDbSchema {
    public static final class AchievementTable {
        public static final String NAME = "achievements";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAMES = "name";
            public static final String DESCRIPTION = "description";
            public static final String HINT = "hint";
            public static final String TIME = "unlocktime";
        }
    }
}
