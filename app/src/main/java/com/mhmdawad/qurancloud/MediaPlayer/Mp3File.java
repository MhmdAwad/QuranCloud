package com.mhmdawad.qurancloud.MediaPlayer;

public class Mp3File {

    private String suraName;
    private String readerName;

    public String getSuraPath() {
        return suraPath;
    }

    private String suraPath;

    public String getSuraName() {
        return suraName;
    }

    public String getReaderName() {
        return readerName;
    }




    public Mp3File(String sura,String reader, String path) {
        suraName =sura;
        readerName = reader;
        suraPath = path;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getArtist() {
//        return artist;
//    }
//
//    public void setArtist(String artist) {
//        this.artist = artist;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public String getSongDuration() {
//        return songDuration;
//    }
//
//    public void setSongDuration(String songDuration) {
//        this.songDuration = songDuration;
//    }
//
//
//    public Long getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Long album) {
//        this.album = album;
//    }

}
