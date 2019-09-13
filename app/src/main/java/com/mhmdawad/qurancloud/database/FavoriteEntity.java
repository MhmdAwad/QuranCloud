package com.mhmdawad.qurancloud.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteDatabase.FAMILY_TABLE)
public class FavoriteEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "sura_name")
    private String suraName;

    @ColumnInfo(name = "reader_name")
    private String readerName;

    public FavoriteEntity(String suraName, String readerName) {
        this.suraName = suraName;
        this.readerName = readerName;
    }

    public String getSuraName() {
        return suraName;
    }

    public String getReaderName() {
        return readerName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
