
package com.mhmdawad.qurancloud.Retrofit.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Reciter {

    @SerializedName("count")
    private String mCount;
    @SerializedName("id")
    private String mId;
    @SerializedName("letter")
    private String mLetter;
    @SerializedName("name")
    private String mName;
    @SerializedName("rewaya")
    private String mRewaya;
    @SerializedName("Server")
    private String mServer;
    @SerializedName("suras")
    private String mSuras;

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLetter() {
        return mLetter;
    }

    public void setLetter(String letter) {
        mLetter = letter;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRewaya() {
        return mRewaya;
    }

    public void setRewaya(String rewaya) {
        mRewaya = rewaya;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(String server) {
        mServer = server;
    }

    public String getSuras() {
        return mSuras;
    }

    public void setSuras(String suras) {
        mSuras = suras;
    }

}
