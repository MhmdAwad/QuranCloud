package com.mhmdawad.qurancloud.MediaPlayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.mhmdawad.qurancloud.Notification.QuranNotification;
import com.mhmdawad.qurancloud.Service.QuranService;
import java.io.IOException;
import java.util.Locale;

import static com.mhmdawad.qurancloud.Activities.ReaderDetails.suraNumber;

public class QuranMediaPlayer {

    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private static String suraPosition;
    public static int realPosition;
    private static Uri nUri;

    public static void runMediaPlayer(final Context context, final String server, final int pos) {

        realPosition = pos;
        if (isNetworkConnected(context)) {
            getRealServerPosition(pos);
            Uri uri = Uri.parse(server + "/" + suraPosition + ".mp3");
            nUri = uri;
            playMediaPlayer(context, uri);
        } else {
            Toast.makeText(context, "No Internet!", Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                forwardMediaPlayer(context,server);
            }
        });
    }

    public static Uri getSuraUri(){
        return nUri;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private static String getRealServerPosition(int pos){
            suraPosition = String.format(Locale.getDefault(), "%03d",
                    Integer.parseInt(suraNumber[pos]));
        return suraPosition;
    }
    public static void resumeOrPauseMediaPlayer() {
        int length = mediaPlayer.getCurrentPosition();
        if (mediaPlayer.isPlaying() && length > 0) {
            mediaPlayer.pause();
            QuranNotification.changePlayOrPauseImage(false);


        } else if (!mediaPlayer.isPlaying() && length > 0) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
            QuranNotification.changePlayOrPauseImage(true);

        }
    }

    private static void checkPosition(){
        if(realPosition >= suraNumber.length || realPosition < 0) {
            realPosition = 0;
        }
    }



    public static void forwardMediaPlayer(Context context,String server) {
        realPosition++;
        checkPosition();
        QuranNotification.refreshNotification(realPosition);
        String position = getRealServerPosition(realPosition);
        Uri uri = Uri.parse(server + "/" + position + ".mp3");
        playMediaPlayer(context, uri);
        QuranNotification.changePlayOrPauseImage(true);
        QuranService.setCurrentPosition(realPosition);
    }


    public static void backMediaPlayer(Context context,String server) {
        realPosition--;
        checkPosition();
        QuranNotification.refreshNotification(realPosition);
        String position = getRealServerPosition(realPosition);
        Uri uri = Uri.parse(server + "/" + position + ".mp3");
        playMediaPlayer(context, uri);
        QuranNotification.changePlayOrPauseImage(true);
        QuranService.setCurrentPosition(realPosition);
    }

    public static void stopMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
    public static void pauseMediaPlayer() {
        mediaPlayer.pause();
    }

    private static void playMediaPlayer(Context context, Uri uri) {
        try {
            stopMediaPlayer();

            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    Log.v("HELLO","start");
                }
            });
            QuranNotification.changePlayOrPauseImage(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
