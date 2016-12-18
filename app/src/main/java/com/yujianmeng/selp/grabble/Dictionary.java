package com.yujianmeng.selp.grabble;

/**
 * Created by YuJianmeng on 2016/12/18.
 */
public class Dictionary {

    private String word;
    private int used;

    public Dictionary(String word, int used) {
        this.word = word;
        this.used = used;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
