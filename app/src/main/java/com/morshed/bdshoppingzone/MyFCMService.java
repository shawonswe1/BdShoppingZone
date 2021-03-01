package com.morshed.bdshoppingzone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {
    public MyFCMService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String click_action = remoteMessage.getNotification().getClickAction();
        Log.e("Tag",title+"\n"+body);

        sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),click_action);
    }

    private void sendNotification(String body, String title, String click_action)
    {

        Intent intent = new Intent(click_action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = (PendingIntent) PendingIntent.getActivities(this,0, new Intent[]{intent},PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Offer");
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setSound(defaultSoundUri);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
    }
}