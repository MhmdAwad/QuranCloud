package com.mhmdawad.qurancloud.MediaPlayer;


import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;


public class ListOfMp3FromStorage {


    public static ArrayList<Mp3File> getMp3Files() {
        return mp3Files;
    }

    private static ArrayList<Mp3File> mp3Files;
    public static String path;

    public static void scanDeviceForMp3Files() {
        path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Android/data/com.mhmdawad.qurancloud/files/";
        createFolder(path);
        Log.v("path", path);
       // Log.d("Files", "Path: " + "/storage/emulated/0/Download/");
        File f = new File(path);
        File file[] = f.listFiles();
//        Log.d("Files", "Size: " + file.length);
        mp3Files = new ArrayList<>();

        for (File value : file) {
            try {
                String fileName = value.getName();
                path = value.getPath();
                Log.v("path", path);

                if (path.endsWith(".mp3")) {
                    String[] splitName = fileName.split(",");
                    String readerName = splitName[0];
                    String suraName = splitName[1];
                    mp3Files.add(new Mp3File(suraName, readerName, path));
                }
            } catch (RuntimeException ex) {
                Log.d("Files", "FileName:" + value.getName());
            }

        }
    }

    private static void createFolder( String rootPath) {
        try {

            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(rootPath + ".nomedia");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream out = new FileOutputStream(f);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
