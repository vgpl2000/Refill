package com.example.projectrefill;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class firebasemessaging extends FirebaseMessagingService {

    private static  final String NOTIFICATION_CHANNEL_ID="MY_NOTIFICATION_CHANNEL_ID";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {


        super.onMessageReceived(message);

        String notificationtype=message.getData().get("notificationtype");
        if(notificationtype.equals("neworder")){
            String buyeruid=message.getData().get("buyeruid");
            String selleruid=message.getData().get("selleruid");
            String orderid=message.getData().get("orderid");
            String notificationtitle=message.getData().get("notificationtitile");
            String description=message.getData().get("notificationmessage");

            shownotification(buyeruid,selleruid,orderid,notificationtitle,description,notificationtype);
        }
        if(notificationtype.equals("orderstatuschanged")){
            String buyeruid=message.getData().get("buyeruid");
            String selleruid=message.getData().get("selleruid");
            String orderid=message.getData().get("orderid");
            String notificationtitle=message.getData().get("notificationtitile");
            String description=message.getData().get("notificationmessage");

            shownotification(buyeruid,selleruid,orderid,notificationtitle,description,notificationtype);
        }


    }

    private void shownotification(String buyeruid, String selleruid, String orderid, String notificationtitle, String description, String notificationtype) {
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        System.out.println("noti in firebasemessaging");
        int notificationID=new Random().nextInt(3000);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            setupnotificationchannel(notificationManager);
        }
        //handle notification click
        Intent intent=null;
        if(notificationtype.equals("neworder")){
            System.out.println("noti in neworder firebasemessaging");
            intent=new Intent(this,client_activity.class);
            intent.putExtra("orderid",orderid);
            intent.putExtra("orderby",buyeruid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        }else if(notificationtype.equals("orderstatuschanged")){
            System.out.println("noti in  orderstatuschanged firebasemessaging");
            intent=new Intent(this,retailer_activity.class);
            intent.putExtra("orderid",orderid);
            intent.putExtra("orderto",selleruid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        }
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeicon= BitmapFactory.decodeResource(getResources(),R.drawable.logo);

        Uri notificationsounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationbuilder.setSmallIcon(R.drawable.logo)
                .setLargeIcon(largeicon)
                .setContentTitle(notificationtitle)
                .setContentText(description)
                .setSound(notificationsounduri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        //show notification
        notificationManager.notify(notificationID,notificationbuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupnotificationchannel(NotificationManager notificationManager) {
        CharSequence channelname="Some sample text";
        String channeldescription="Channel description here";

        NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,channelname,NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(channeldescription);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        if(notificationManager!=null){
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}

