package com.morshed.bdshoppingzone;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMService extends FirebaseMessagingService {
    public MyFCMService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {

            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title").toString();
            String messages = data.get("body").toString();

            String message = remoteMessage.getData().get("body");
            String click_action = remoteMessage.getNotification().getClickAction();
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),click_action,messages);
        }
    }

    private void sendNotification(String body, String title, String click_action, String messages)
    {
        Bundle bundle = new Bundle();
        bundle.putString("body",messages);

//        Toast.makeText(getApplicationContext(),body,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(click_action);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = (PendingIntent) PendingIntent.getActivities(this,1, new Intent[]{intent},PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String Notification_ID = "ShoppingZoneBD";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(Notification_ID,
                    "ShoppingZoneBD",
                    NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Notification Chanel for test FCM");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,Notification_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setContentInfo("info");
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker("Hearty365");
        builder.setSound(defaultSoundUri);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(1,builder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
    }
}