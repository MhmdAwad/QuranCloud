package com.mhmdawad.qurancloud.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.mhmdawad.qurancloud.Activities.MediaPlayerActivity;
import com.mhmdawad.qurancloud.R;
import com.mhmdawad.qurancloud.Activities.ReaderDetails;
import com.mhmdawad.qurancloud.Service.QuranService;
import static com.mhmdawad.qurancloud.Notification.NotificationButtonClick.backButton;
import static com.mhmdawad.qurancloud.Notification.NotificationButtonClick.closeButton;
import static com.mhmdawad.qurancloud.Notification.NotificationButtonClick.forwardButton;
import static com.mhmdawad.qurancloud.Notification.NotificationButtonClick.playButton;

import androidx.core.app.NotificationCompat;

public class QuranNotification {

    private static final String BUILD_NOTIFICATION_ID = "buildIt";
    private static final String NOTIFICATION_SEQUENCE_NAME = "notificationName";
    private static final String NOTIFICATION_CHANNEL_ID = "channelId";
    private static final int CONTENT_INTENT_ID = 101;
    private static NotificationManager notificationManager;
    private static Notification notification;
    private static RemoteViews largeView;
    private static RemoteViews smallView;
    public static void quranNotification(Context context, int pos) {
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    BUILD_NOTIFICATION_ID
                    , NOTIFICATION_SEQUENCE_NAME
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O
                && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            buildNotification(context, pos);
        }
    }

    public static Notification buildNotification(Context context, int pos) {

        initializeLargeRemoteView(context,pos);
        initializeSmallRemoteView(context,pos);

        notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play_button)
                .setContentIntent(contentIntent(context))
                .setCustomBigContentView(largeView)
                .setCustomContentView(smallView)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        return notification;
    }

    private static void initializeSmallRemoteView(Context context, int pos){
        smallView = new RemoteViews(context.getPackageName(), R.layout.custom_small_notification);
        smallView.setTextViewText(R.id.suraNameTextView, changeSuraName(pos));
        smallView.setOnClickPendingIntent(R.id.playButton, playButton(context));
        smallView.setOnClickPendingIntent(R.id.closeButton,closeButton(context));
        smallView.setOnClickPendingIntent(R.id.forwardButton,forwardButton(context));
        smallView.setOnClickPendingIntent(R.id.backButton,backButton(context));
    }

    private static void initializeLargeRemoteView(Context context, int pos){
        largeView = new RemoteViews(context.getPackageName(), R.layout.custom_large_notification);
        largeView.setTextViewText(R.id.suraNameTextView, changeSuraName(pos));
        largeView.setOnClickPendingIntent(R.id.playButton, playButton(context));
        largeView.setOnClickPendingIntent(R.id.closeButton,closeButton(context));
        largeView.setOnClickPendingIntent(R.id.forwardButton,forwardButton(context));
        largeView.setOnClickPendingIntent(R.id.backButton,backButton(context));
    }

    public static void refreshNotification(int pos) {
        largeView.setTextViewText(R.id.suraNameTextView,changeSuraName(pos));
        smallView.setTextViewText(R.id.suraNameTextView,changeSuraName(pos));
        notification.contentView = largeView;
        notification.contentView = smallView;
        notificationManager.notify(QuranService.SERVICE_FOREGROUND_ID, notification);
    }

    private static String changeSuraName(int pos){
        return  "سـورة "+ReaderDetails.reciterSurasNames.get(pos);
    }

    public static void changePlayOrPauseImage(boolean isPlay){
        if(isPlay){
            largeView.setImageViewResource(R.id.playButton,R.drawable.ic_pause_button);
            smallView.setImageViewResource(R.id.playButton,R.drawable.ic_pause_button);
        }else{
            largeView.setImageViewResource(R.id.playButton,R.drawable.ic_play_button);
            smallView.setImageViewResource(R.id.playButton,R.drawable.ic_play_button);
        }
    }

    private static PendingIntent contentIntent(Context context) {
        Intent intent = new Intent(context, MediaPlayerActivity.class);
        return PendingIntent.getActivity(
                context,
                CONTENT_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }





}
