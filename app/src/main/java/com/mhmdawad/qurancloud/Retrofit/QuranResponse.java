package com.mhmdawad.qurancloud.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuranResponse {

    private static Retrofit retrofitInstance = null;
    private final static String BASE_URL = "https://mp3quran.net/api/";

    public static Retrofit createResponse(){
        if(retrofitInstance == null){
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
