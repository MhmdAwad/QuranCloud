package com.mhmdawad.qurancloud.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mhmdawad.qurancloud.Activities.MediaPlayerActivity;
import com.mhmdawad.qurancloud.Activities.ReaderDetails;
import com.mhmdawad.qurancloud.MediaPlayer.QuranMediaPlayer;
import com.mhmdawad.qurancloud.Notification.QuranNotification;

public class QuranService extends Service {

    public static final String RUN_NEW_SURA = "runIt";
    public static final String PLAY_OR_PAUSE = "playPauseIt";
    public static final String STOP_SERVICE = "closeIt";
    public static final String FORWARD_SURA = "next";
    public static final String BACK_SURA = "back";
    public static final String CHECK_NOTIFY = "checkNotify";

    private static int currentPosition;
    public static final int SERVICE_FOREGROUND_ID = 102;
    private static String server;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        switch (intent.getAction()) {
            case RUN_NEW_SURA:
                QuranNotification.quranNotification(this, currentPosition);
                startForeground(SERVICE_FOREGROUND_ID, QuranNotification.buildNotification(this, currentPosition));
                if (!(ReaderDetails.getReciterName().equals(MediaPlayerActivity.getCheckReaderName()))
                || (!(ReaderDetails.reciterSurasNames.get(currentPosition)
                    .equals(MediaPlayerActivity.getCheckSuraName())))){
                        QuranMediaPlayer.runMediaPlayer(this, server, currentPosition);

                }
                break;
            case PLAY_OR_PAUSE:
                QuranMediaPlayer.resumeOrPauseMediaPlayer();
                QuranNotification.refreshNotification(currentPosition);
                break;
            case STOP_SERVICE:
                QuranMediaPlayer.stopMediaPlayer();
                stopSelf();
                break;
            case FORWARD_SURA:
                QuranMediaPlayer.forwardMediaPlayer(this, server);
                QuranNotification.refreshNotification(currentPosition);
                break;
            case BACK_SURA:
                QuranMediaPlayer.backMediaPlayer(this, server);
                QuranNotification.refreshNotification(currentPosition);
                break;
            case CHECK_NOTIFY:
                QuranNotification.refreshNotification(currentPosition);
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void setCurrentPosition(int currentPosition) {
        QuranService.currentPosition = currentPosition;
    }

    public static void setCurrentServer(String pServer) {
        server = pServer;
    }

    public static int getCurrentPosition() {
        return currentPosition;
    }
}
