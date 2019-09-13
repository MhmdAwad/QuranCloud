package com.mhmdawad.qurancloud.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteEntity.class}, exportSchema = false, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    static final String FAMILY_TABLE = "favorite_entity";
    private static FavoriteDatabase sInstance;

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, FavoriteDatabase.FAMILY_TABLE)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();


}
