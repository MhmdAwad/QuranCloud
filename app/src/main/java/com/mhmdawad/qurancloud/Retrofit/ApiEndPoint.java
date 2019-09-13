package com.mhmdawad.qurancloud.Retrofit;

import com.mhmdawad.qurancloud.Retrofit.Objects.QuranData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoint {

    @GET("_arabic.json")
    Call<QuranData> getQuranData();
}
