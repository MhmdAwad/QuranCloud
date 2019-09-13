
package com.mhmdawad.qurancloud.Retrofit.Objects;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class QuranData {

    @SerializedName("reciters")
    private List<Reciter> mReciters;

    public List<Reciter> getReciters() {
        return mReciters;
    }

    public void setReciters(List<Reciter> reciters) {
        mReciters = reciters;
    }



}
