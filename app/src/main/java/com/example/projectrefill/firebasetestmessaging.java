package com.example.projectrefill;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebasetestmessaging extends FirebaseMessagingService {
    NotificationManager mnotification;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        System.out.println("inside onmsgrecieved");




            //if (notificationtype.equals("orderplaced")) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    r.setLooping(false);
                }
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {100, 300, 300, 300};
                v.vibrate(pattern, -1);


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");

                Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setLargeIcon(largeicon);
                } else {
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setLargeIcon(largeicon);
                }


               //Intent resultintent=null;

               // PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultintent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentTitle(message.getNotification().getTitle());
                builder.setContentText(message.getNotification().getBody());
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getNotification().getBody()));
                builder.setAutoCancel(true);
                builder.setPriority(Notification.PRIORITY_MAX);
                builder.setLargeIcon(largeicon);


                mnotification = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String Channel_id = "urchannelid";
                    NotificationChannel channel = new NotificationChannel(Channel_id, "title", NotificationManager.IMPORTANCE_HIGH);
                    mnotification.createNotificationChannel(channel);
                    builder.setChannelId(Channel_id);

                }

                mnotification.notify(100, builder.build());

           /* } else if (notificationtype.equals("accepted")) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    r.setLooping(false);
                }
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {100, 300, 300, 300};
                v.vibrate(pattern, -1);


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");

                Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setLargeIcon(largeicon);
                } else {
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setLargeIcon(largeicon);
                }


                Intent resultintent = new Intent(this, retailer_activity.class);


                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultintent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentTitle(message.getNotification().getTitle());
                builder.setContentText(message.getNotification().getBody());
                builder.setContentIntent(pendingIntent);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getNotification().getBody()));
                builder.setAutoCancel(true);
                builder.setPriority(Notification.PRIORITY_MAX);
                builder.setLargeIcon(largeicon);


                mnotification = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String Channel_id = "urchannelid";
                    NotificationChannel channel = new NotificationChannel(Channel_id, "title", NotificationManager.IMPORTANCE_HIGH);
                    mnotification.createNotificationChannel(channel);
                    builder.setChannelId(Channel_id);

                }

                mnotification.notify(100, builder.build());
            }else{
                System.out.println("else part");
            }*/



    }
}
