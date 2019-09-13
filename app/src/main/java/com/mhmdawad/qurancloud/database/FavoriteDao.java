package com.mhmdawad.qurancloud.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite_entity")
    List<FavoriteEntity> getAll();

    @Insert
    void insert(FavoriteEntity... sura);
}
