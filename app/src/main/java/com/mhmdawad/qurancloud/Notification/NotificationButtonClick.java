package com.mhmdawad.qurancloud.Notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mhmdawad.qurancloud.Service.QuranService;

 class NotificationButtonClick {

     private static final int PENDING_INTENT_ID = 101;
     static PendingIntent playButton(Context context){
        Intent intent = new Intent(context, QuranService.class);
        intent.setAction(QuranService.PLAY_OR_PAUSE);

        return PendingIntent.getService(
                context,
                PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

     static PendingIntent closeButton(Context context) {
         Intent intent = new Intent(context, QuranService.class);
         intent.setAction(QuranService.STOP_SERVICE);

         return PendingIntent.getService(
                 context,
                 PENDING_INTENT_ID,
                 intent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );
     }

     static PendingIntent forwardButton(Context context) {
         Intent intent = new Intent(context, QuranService.class);
         intent.setAction(QuranService.FORWARD_SURA);

         return PendingIntent.getService(
                 context,
                 PENDING_INTENT_ID,
                 intent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );
     }

     static PendingIntent backButton(Context context) {
         Intent intent = new Intent(context, QuranService.class);
         intent.setAction(QuranService.BACK_SURA);

         return PendingIntent.getService(
                 context,
                 PENDING_INTENT_ID,
                 intent,
                 PendingIntent.FLAG_UPDATE_CURRENT
         );
     }


}
